package com.fred.config;

import com.fred.config.interceptor.AdminLoginHandlerInterceptor;
import com.fred.config.interceptor.BackOfficeLoginHandlerInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

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

    @Bean
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new BackOfficeLoginHandlerInterceptor()).addPathPatterns("/backOffice/**")
                        .excludePathPatterns("/index.html","/","/backOffice/login");
                registry.addInterceptor(new AdminLoginHandlerInterceptor()).addPathPatterns("/admin/**")
                        .excludePathPatterns("/admin/login","/admin");

            }
        };
        return adapter;
    }
}
