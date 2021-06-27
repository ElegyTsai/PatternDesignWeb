# -*- coding: utf-8 -*-

import argparse
import os

import time
import cv2
import sklearn.cluster as cluster
import matplotlib.pyplot as plt
import numpy as np
import sys


# parser = argparse.ArgumentParser()
#
# parser.add_argument('--reference', type=str,
#                     help='File path to the reference image')
# parser.add_argument('--source', type=str,
#                     help='File path to the source image')
# parser.add_argument('--output', type=str, default='output',
#                     help='Directory to save the output image(s)')
# parser.add_argument('--k', type=int, default=8,
#                     help='number of centers for K-Means')
# parser.add_argument('--mode', type=str, default='match',
#                     help='analysis: color analysis, match: color analysis + match')
#
# args = parser.parse_args()



def centroid_histogram(clt):
    # grab the number of different clusters and create a histogram
    # based on the number of pixels assigned to each cluster
    numLabels = np.arange(0, len(np.unique(clt.labels_)) + 1)
    (hist, _) = np.histogram(clt.labels_, bins = numLabels)
    # normalize the histogram, such that it sums to one
    hist = hist.astype("float")
    hist /= hist.sum()
    # return the histogram
    return hist

def plot_colors(hist, centroids):
    # initialize the bar chart representing the relative frequency of each of the colors
    bar = np.zeros((50, 300, 3), dtype = "uint8")
    startX = 0
    # loop over the percentage of each cluster and the color of each cluster
    for (percent, color) in zip(hist, centroids):
        # plot the relative percentage of each cluster
        endX = startX + (percent * 300)
        cv2.rectangle(bar, (int(startX), 0), (int(endX), 50), color.astype("uint8").tolist(), -1)
        startX = endX

    # return the bar chart
    return bar

def color_analysis(ref, k):
    tic = time.time()

    ref = ref.reshape((-1, 3))
    clt = cluster.MiniBatchKMeans(n_clusters = k, init='k-means++')
    clt.fit(ref)

    hist = centroid_histogram(clt)
    # sort from high to low
    argsort = np.argsort(hist)
    hist_sorted = [hist[i] for i in argsort]
    hist_sorted.reverse()
    hist_sorted = np.array(hist_sorted)
    cluster_centers_sorted = [clt.cluster_centers_[i] for i in argsort]
    cluster_centers_sorted.reverse()
    cluster_centers_sorted = np.array(cluster_centers_sorted)

    bar = plot_colors(hist_sorted, cluster_centers_sorted)

    analysis_time = time.time() - tic

    return bar, clt, analysis_time

# compute L2 distance
def compute_dist(img, center):
    return np.sqrt(-2*np.dot(img, center.T) + np.sum(np.square(center),axis = 1) + np.transpose([np.sum(np.square(img), axis = 1)]))


def color_match(clt, sou):
    tic = time.time()

    predicts = np.argmin(compute_dist(sou.reshape((-1,3)).astype('float32'), clt.cluster_centers_), axis=-1)
    one_hot = np.eye(clt.cluster_centers_.shape[0])[predicts]

    resultimage = np.dot(one_hot, clt.cluster_centers_).reshape(sou.shape[0], sou.shape[1], 3).astype('uint8')
    
    match_time = time.time() - tic
    return resultimage, match_time




# convert the transparent background to white
def transparentToWhite(image, alpha):
    for i in range(alpha.shape[0]):
        for j in range(alpha.shape[1]):
            if alpha[i][j]==0:
                image[i][j]=[255,255,255]
    return image

reference = sys.argv[1]
source = sys.argv[2]
output = sys.argv[3]
k = int(sys.argv[4])
mode = sys.argv[5]

# read the reference image
ref = cv2.imread(reference, cv2.IMREAD_UNCHANGED)
#ref = cv2.imread(args.reference)


if ref.shape[-1]==4:
    refalpha = np.expand_dims(ref[:,:,-1], axis=-1)
else:
    refalpha = None


ref = cv2.cvtColor(ref, cv2.COLOR_BGR2RGB)


if refalpha is not None:
    ref = transparentToWhite(ref, refalpha)


# read the source image
sou = cv2.imread(source, cv2.IMREAD_UNCHANGED)

if sou.shape[-1]==4:
    soualpha = np.expand_dims(sou[:,:,-1], axis=-1)
else:
    soualpha = None

sou = cv2.cvtColor(sou, cv2.COLOR_BGR2RGB)

if soualpha is not None:
    sou = transparentToWhite(sou, soualpha)


bar, clt, analysis_time = color_analysis(ref, k)
match_result, match_time = color_match(clt, sou)

print ("color analysis time: %.4f"%(analysis_time))
bar = cv2.cvtColor(bar, cv2.COLOR_RGB2BGR)
cv2.imwrite(output+"/analysis_bar.jpg", bar)

match_result = cv2.cvtColor(match_result, cv2.COLOR_RGB2BGR)
if soualpha is not None:
    match_result = np.concatenate([match_result, soualpha], axis = -1)

print ("color match time: %.4f"%(match_time))
cv2.imwrite(output+"/match_result.jpg", match_result)