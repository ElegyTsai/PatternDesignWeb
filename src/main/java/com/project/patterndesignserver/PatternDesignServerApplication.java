package com.project.patterndesignserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAsync
@ServletComponentScan
@SpringBootApplication
public class PatternDesignServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatternDesignServerApplication.class, args);
    }

}
