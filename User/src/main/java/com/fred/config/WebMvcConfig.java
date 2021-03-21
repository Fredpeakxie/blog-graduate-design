package com.fred.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @auther fred
 * @create 2021-03-21 17:59
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${accessFile.portrait.resourceHandler}")
    private String portraitResourceHandler; //匹配url 中的资源映射

    @Value("${accessFile.portrait.location}")
    private String portraitLocation; //上传文件保存的本地目录

    /**
     * 配置静态资源映射
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //匹配到resourceHandler,将URL映射至location,也就是本地文件夹
        registry.addResourceHandler(portraitResourceHandler).addResourceLocations("file:///" + portraitLocation);
    }
}
