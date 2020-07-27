package com.qiluhospital.chemotherapy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//@Configuration
//@EnableAsync
@SpringBootApplication
public class MiniappApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MiniappApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        //处理消息回复
//        DefaultAlicomMessagePuller puller = new DefaultAlicomMessagePuller();
//        //设置异步线程池大小及任务队列的大小，还有无数据线程休眠时间
//        puller.setConsumeMinThreadSize(6);
//        puller.setConsumeMaxThreadSize(16);
//        puller.setThreadQueueSize(200);
//        puller.setPullMsgThreadSize(1);
//        //和服务端联调问题时开启,平时无需开启，消耗性能
//        puller.openDebugLog(false);
//        //此处需要替换成开发者自己的AK信息
//        String accessKeyId="LTAI4Fh29msZ2Zc3XzxwoBDH";
//        String accessKeySecret="0ZoVYScULpjt3HVGEemvt1WQfcKmiF";
//        /*
//         * 将messageType和queueName替换成您需要的消息类型名称和对应的队列名称
//         *云通信产品下所有的回执消息类型:
//         *1:短信回执：SmsReport，
//         *2:短息上行：SmsUp
//         *3:语音呼叫：VoiceReport
//         *4:流量直冲：FlowReport
//         */
//        String messageType="SmsUp";//此处应该替换成相应产品的消息类型messageType
//        String queueName="Alicom-Queue-1857277521244836-SmsUp";//在云通信页面开通相应业务消息后，就能在页面上获得对应的queueName
//        try {
//            puller.startReceiveMsg(accessKeyId,accessKeySecret, messageType, queueName,
//                    new MyMessageListener());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return builder.sources(MiniappApplication.class);
    }


}

