package com.lawer.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
配置Tomcat对项目外部加载资源的放行
访问方式为localhost:8080/upload/   映射路径为本地文件夹所在地
 */
@Configuration
public class WebMvcConfig  implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:D:/upload/");
    }

}
