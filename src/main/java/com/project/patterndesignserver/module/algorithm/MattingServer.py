
import argparse
from time import sleep

import cv2
import base64
import numpy as np
import queue
import json

import torch

from isegm.inference import utils
from isegm.inference import clicker
from isegm.inference.predictors import get_predictor
from isegm.utils.vis import draw_with_blend_and_clicks
import pika
import json
import threading

threadPool = {}
def main():
    connection =pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
    channel = connection.channel()

    def callback(ch, method, properties, body):
        data = json.loads(body)
        sessionId = int(data["sessionId"])
        print(data["fileName"])
        aQueue = threadPool.get(sessionId)
        if(aQueue is None):
            que = queue.Queue()
            threadPool[sessionId] = que
            que.put(body)
            t = threading.Thread(target=process,args=((que,connection,)))
            t.start()
        else:
            aQueue.put(body)
        # print(body)
        print(data["operation"])

    channel.basic_consume(queue='matting.handler.1', on_message_callback=callback, auto_ack=True)

    print(' [*] Waiting for messages. To exit press CTRL+C')

    channel.start_consuming()


def process(q,connection):
    args = parse_args()

    torch.backends.cudnn.deterministic = True
    checkpoint_path = utils.find_checkpoint("./", args.checkpoint)
    model = utils.load_is_model(checkpoint_path, args.device, cpu_dist_maps=True)

    controller = InteractiveController(model, args.device,
                                       predictor_params={'brs_mode': 'NoBRS'})

    # q1 = {"operationCount": 0, "fileName": "", "createTime": 1626681500364, "clicks": [], "cacheImage": [],
    #       "sessionId": 1, "operation": "{\"operation\":\"initialize\"}", "lastUpdateTime": 1626681500364,
    #       "status": "active"}
    # json1 = json.dumps(q1)
    # q.put(json1)
    while True:
        if not q.empty():
            body = q.get()
            data = json.loads(body)
            operation = json.loads(data['operation'])
            if operation['operation'] == 'initialize':
                image = load_image(data['fileName'])
                controller.set_image(image)
                data['status'] = 'waiting'
            elif operation['operation'] == 'addClicks':
                controller.add_click(operation['x'], operation['y'], operation['mouse'])
                vis = controller.get_visualization(0.50)
                data['cacheImage'] = image_to_base64(vis)
                print(data['cacheImage'])
                data['status'] = 'waiting'
            elif operation['operation'] == 'undo':
                controller.undo_click()
                vis = controller.get_visualization(0.50)
                data['cacheImage'] = image_to_base64(vis)
                print(data['cacheImage'])
                data['status'] = 'waiting'
            elif operation['operation'] == 'redo':
                image = load_image(data['fileName'])
                controller.set_image(image)
                data['status'] = 'waiting'
                clicks = data['clicks']
                for i in len(clicks):
                    controller.add_click(clicks[0], clicks[1], clicks[2] == 1)
                vis = controller.get_visualization(0.50)
                data['cacheImage'] = image_to_base64(vis)
                print(data['cacheImage'])
                data['status'] = 'waiting'
            elif operation['operation'] == 'save':
                mask = controller.result_mask
                if mask.max() < 256:
                    mask = mask.astype(np.uint8)
                    mask *= 255 // mask.max()
                data['cacheImage'] = save_mask(mask)
                data['status'] = 'waiting'
            elif operation['operation'] == 'finalize':
                return
            connection2 = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
            channel2 = connection2.channel()
            newBody = json.dumps(data)
            print(newBody)
            channel2.basic_publish(exchange='',routing_key="matting.receiver",body=newBody)

        else:
            sleep(0.01)
            # print('Queue is empty.')

    # # 保存mask
    # mask = controller.result_mask
    # if mask.max() < 256:
    #     mask = mask.astype(np.uint8)
    #     mask *= 255 // mask.max()
    # cv2.imshow('mask', mask)
    # cv2.waitKey(0)
def save_mask(mask):
    image = cv2.imencode('.png', mask)[1]
    base64_data = str(base64.b64encode(image))[2:-1]
    return base64_data

def image_to_base64(vis):
    img_np = cv2.cvtColor(vis, cv2.COLOR_RGB2BGR)
    image = cv2.imencode('.jpg', img_np)[1]
    base64_data = str(base64.b64encode(image))[2:-1]
    return base64_data


def load_image(filename):
    image = cv2.cvtColor(cv2.imread(filename), cv2.COLOR_BGR2RGB)
    return image


class InteractiveController:
    def __init__(self, net, device, predictor_params, prob_thresh=0.5):
        self.net = net
        self.prob_thresh = prob_thresh
        self.clicker = clicker.Clicker()
        self.states = []
        self.probs_history = []
        self.object_count = 0
        self._result_mask = None
        self._init_mask = None

        self.image = None
        self.predictor = None
        self.device = device
        self.predictor_params = predictor_params
        self.reset_predictor()

    def set_image(self, image):
        self.image = image
        self._result_mask = np.zeros(image.shape[:2], dtype=np.uint16)
        self.object_count = 0
        self.reset_last_object()

    def add_click(self, x, y, is_positive):
        # is_positive：左键单击为True，右键单击为False
        self.states.append({
            'clicker': self.clicker.get_state(),
            'predictor': self.predictor.get_states()
        })
        click = clicker.Click(is_positive=is_positive, coords=(y, x))
        self.clicker.add_click(click)
        pred = self.predictor.get_prediction(self.clicker, prev_mask=self._init_mask)
        if self._init_mask is not None and len(self.clicker) == 1:
            pred = self.predictor.get_prediction(self.clicker, prev_mask=self._init_mask)

        torch.cuda.empty_cache()

        if self.probs_history:
            self.probs_history.append((self.probs_history[-1][0], pred))
        else:
            self.probs_history.append((np.zeros_like(pred), pred))

    def undo_click(self):
        if not self.states:
            return

        prev_state = self.states.pop()
        self.clicker.set_state(prev_state['clicker'])
        self.predictor.set_states(prev_state['predictor'])
        self.probs_history.pop()
        if not self.probs_history:
            self.reset_init_mask()

    def reset_last_object(self):
        self.states = []
        self.probs_history = []
        self.clicker.reset_clicks()
        self.reset_predictor()
        self.reset_init_mask()

    def reset_predictor(self, predictor_params=None):
        if predictor_params is not None:
            self.predictor_params = predictor_params
        self.predictor = get_predictor(self.net, device=self.device,
                                       **self.predictor_params)
        if self.image is not None:
            self.predictor.set_input_image(self.image)

    def reset_init_mask(self):
        self._init_mask = None
        self.clicker.click_indx_offset = 0

    @property
    def current_object_prob(self):
        if self.probs_history:
            current_prob_total, current_prob_additive = self.probs_history[-1]
            return np.maximum(current_prob_total, current_prob_additive)
        else:
            return None

    @property
    def is_incomplete_mask(self):
        return len(self.probs_history) > 0

    @property
    def result_mask(self):
        result_mask = self._result_mask.copy()
        if self.probs_history:
            result_mask[self.current_object_prob > self.prob_thresh] = self.object_count + 1
        return result_mask

    def get_visualization(self, alpha_blend):
        if self.image is None:
            return None

        results_mask_for_vis = self.result_mask
        vis = draw_with_blend_and_clicks(self.image, mask=results_mask_for_vis, alpha=alpha_blend)
        if self.probs_history:
            total_mask = self.probs_history[-1][0] > self.prob_thresh
            results_mask_for_vis[np.logical_not(total_mask)] = 0
            vis = draw_with_blend_and_clicks(vis, mask=results_mask_for_vis, alpha=alpha_blend)

        return vis


# commands: python matting.py
def parse_args():
    parser = argparse.ArgumentParser()

    parser.add_argument('--checkpoint', type=str, default='coco_lvis_h18_itermask',
                        help='The path to the checkpoint. '
                             'This can be a relative path (relative to cfg.INTERACTIVE_MODELS_PATH) '
                             'or an absolute path. The file extension can be omitted.')

    parser.add_argument('--gpu', type=int, default=0,
                        help='Id of GPU to use.')

    parser.add_argument('--cpu', action='store_true', default=True,
                        help='Use only CPU for inference.')

    args = parser.parse_args()
    if args.cpu:
        args.device = torch.device('cpu')
    else:
        args.device = torch.device(f'cuda:{args.gpu}')
    return args


if __name__ == '__main__':
    main()
