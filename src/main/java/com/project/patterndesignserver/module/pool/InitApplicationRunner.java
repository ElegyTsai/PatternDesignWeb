package com.project.patterndesignserver.module.pool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

//@Component
public class InitApplicationRunner implements ApplicationRunner {
    @Value("${python.matting.file}")
    String pythonFile;
    @Value("${python.matting.runner}")
    String pythonRunner;
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String[] args = new String[]{pythonRunner, pythonFile};
                try {
                    System.out.println("Python服务器开始运行");
                    Process process = Runtime.getRuntime().exec(args);
                    process.waitFor();
                }
                catch (Exception e){
                    System.out.println("python服务器运行失败");
                }
            }
        });
//        thread.start();
        System.out.println("python服务器初始化完成：InitApplicationRunner");
    }
}
