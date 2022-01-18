package com.qiluhospital.chemotherapy.mdt.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSocketConfig {
    /**
     * 服务器节点
     * 编写一个WebSocketConfig配置类，注入对象ServerEndpointExporter
     * 这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
     * 如果使用独立的servlet容器，而不是直接使用springboot的内置容器，就不要注入
     * ServerEndpointExporter，因为它将由容器自己提供和管理
     */

//    上线注释
//
//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }

}
