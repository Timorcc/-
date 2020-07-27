package com.qiluhospital.chemotherapy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigBeanValue {

    @Value("${patient.appid}")
    public String patAppid;

    @Value("${patient.secret}")
    public String patSecret;

    @Value("${doctor.appid}")
    public String docAppid;

    @Value("${doctor.secret}")
    public String docSecret;

    @Value("${grant_type}")
    public String grant_type;

    @Value("${patient.imgPath}")
    public String imgPath;

    @Value("${patient.pathPattern}")
    public String pathPattern;

    @Value("${aliyun.accessKeyId}")
    public String accessKeyId;

    @Value("${aliyun.accessKeySecret}")
    public String accessKeySecret;


}
