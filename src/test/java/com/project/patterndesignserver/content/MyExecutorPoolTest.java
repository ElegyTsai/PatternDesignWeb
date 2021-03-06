package com.project.patterndesignserver.content;

import com.google.gson.JsonObject;
import com.project.patterndesignserver.module.pool.ExecutorPool;
import com.project.patterndesignserver.module.pool.MattingTask;
import com.project.patterndesignserver.service.colorMatch.MattingService;
import com.project.patterndesignserver.util.ImageUtil;
import com.project.patterndesignserver.util.JsonStringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MyExecutorPoolTest {

    volatile ExecutorPool executorPool = new ExecutorPool();

    @Autowired
    MattingService mattingService;

    @Test
    public void Test1()throws Exception, IOException {
        for(long i =0 ;i<20;i++){

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        for (int j = 0; j < 10; j++){
                            try {
                                Thread.sleep(10);
                                System.out.println(Thread.currentThread().getName()+" begin to add");
                                executorPool.addIntoPool(new MattingTask(System.currentTimeMillis()));
                                System.out.println(Thread.currentThread().getName()+" added");
                            } catch (Exception e) {
                                return;
                            }
                        }


                        return ;
                    }
                });
                thread.setName(i+"th thread");
                thread.start();
        }
        Thread.sleep(1000);
    }

    @Test
    public void test2(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("operation","addClicks");
        jsonObject.addProperty("x",1);
        jsonObject.addProperty("y",2);
        jsonObject.addProperty("mouse",true);
        System.out.println(jsonObject.toString());
    }
    @Test
    public void test3() throws Exception{
        MattingTask mattingTask = new MattingTask(1L);
        mattingTask.addClicks(1,2,true);
        mattingTask.addClicks(2,3,true);
        System.out.println(mattingTask.serialized());
        List<List<Integer>> clicks = new ArrayList<>();
        clicks = mattingTask.getClicks();
        MattingTask mattingTask2 = MattingTask.deserialized(mattingTask.serialized());
        System.out.println(mattingTask2.serialized());
//        System.out.println(JsonStringUtil.<List<Integer>>getJsonFromArray(clicks));
    }
    @Test
    public void test4() throws Exception{
        MattingTask mattingTask = new MattingTask(1L);
        mattingTask.taskInitialize();
        //???????????????
        mattingTask.addClicks(1,2,true);
        //??????add
//        mattingTask.undo();
        //??????
//        mattingTask.taskRedo();
        //redo????????? ????????????+???click????????????????????????add
        mattingTask.taskFinalize();
        System.out.println(mattingTask.serialized());
        List<List<Integer>> clicks = new ArrayList<>();
        clicks = mattingTask.getClicks();
        MattingTask mattingTask2 = MattingTask.deserialized(mattingTask.serialized());
        System.out.println(mattingTask2.serialized());
//        System.out.println(JsonStringUtil.<List<Integer>>getJsonFromArray(clicks));
    }
    @Test
    public void test5() throws  Exception{
        File file = new File("/Users/elegy/Downloads/IMG_1367.JPG");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile("pic", file.getName(), "png", fileInputStream);
            ImageUtil.saveWithRandomName(multipartFile,"/Users/elegy/Desktop/Data/cache/");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void test6(){

    }
}
