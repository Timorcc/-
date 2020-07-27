package com.qiluhospital.chemotherapy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private ConfigBeanValue configBeanValue;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String imgPath = configBeanValue.imgPath.replaceAll("\\\\","/");
        registry.addResourceHandler(configBeanValue.pathPattern+"**").addResourceLocations("file:"+imgPath);}}