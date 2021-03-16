package com.fred;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @auther fred
 * @create 2021-02-20 9:25
 */
@SpringBootApplication
public class ArticleMicroService {
    public static void main(String[] args) {
        ConfigurableApplicationContext ioc = SpringApplication.run(ArticleMicroService.class, args);
        //打印自动注入信息
//        String[] beanDefinitionNames = ioc.getBeanDefinitionNames();
//        for (String name : beanDefinitionNames) {
//            System.out.println(name);
//        }
    }
}
