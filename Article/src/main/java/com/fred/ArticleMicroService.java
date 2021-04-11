package com.fred;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @auther fred
 * @create 2021-02-20 9:25
 */
@SpringBootApplication
public class ArticleMicroService {
    public static void main(String[] args) {
        ConfigurableApplicationContext ioc = SpringApplication.run(ArticleMicroService.class, args);
        String currentWorkingDirectory = System.getProperty("user.dir")+System.getProperty("file.separator");
        System.out.println(currentWorkingDirectory);
        System.out.println(System.getProperty("user.dir"));
        System.out.println(System.getProperty("file.separator"));

    }
}
