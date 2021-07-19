package com.project.patterndesignserver.module.pool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitApplicationRunner implements ApplicationRunner {
    @Value("${python.Matting}")
    String pythonFile;
    @Value("${python.runner}")
    String pythonRunner;
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String[] args = new String[]{pythonRunner, pythonFile};
                try {
                    Process process = Runtime.getRuntime().exec(args);
                    process.waitFor();
                }
                catch (Exception e){
                    System.out.println("python服务器运行失败");
                }
            }
        });
        System.out.println("初始化完成：InitApplicationRunner");
    }
}
