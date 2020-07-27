//package com.qiluhospital.chemotherapy.miniapp.servise.impl;
//
//import com.alicom.mns.tools.MessageListener;
//import com.aliyun.mns.model.Message;
//import com.google.gson.Gson;
//
//import java.text.SimpleDateFormat;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//public class MyMessageListener implements MessageListener {
//
//    private Gson gson = new Gson();
//
//    @Override
//    public boolean dealMessage(Message message) {
//
//        System.out.println("开始接受");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        //消息的几个关键值
////        System.out.println("message handle: " + message.getReceiptHandle());
////        System.out.println("message body: " + message.getMessageBodyAsString());
////        System.out.println("message id: " + message.getMessageId());
////        System.out.println("message dequeue count:" + message.getDequeueCount());
////        System.out.println("Thread:" + Thread.currentThread().getName());
//        try{
//            Map<String,Object> contentMap = gson.fromJson(message.getMessageBodyAsString(), HashMap.class);
//            System.out.println(message.toString());
//            System.out.println(message.getMessageBody());
//            //TODO 根据文档中具体的消息格式进行消息体的解析
//            String arg = (String) contentMap.get("arg");
//            System.out.println(arg);
//            //TODO 这里开始编写您的业务代码
//            Iterator<String> ite = contentMap.keySet().iterator();
//            while (ite.hasNext()) {
//                String key = ite.next();
//                Object value = contentMap.get(key);
//                System.out.println("key:"+key);
//                System.out.println("value:"+value);
//            }
////            QuerySendDetailsResponse querySendDetailsResponse =
////                    MessageUtils.querySendDetails((String)contentMap.get("phone_number"),sdf.format(contentMap.get("send_time")));
////            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
////            {
////                System.out.println("开始了");
////                System.out.println("Content=" + smsSendDetailDTO.getContent());
////                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
////                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
////                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
////                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
////                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
////                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
////                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
////            }
//            String phoneNumber=(String)contentMap.get("phone_number");
//            System.out.print("=======短信回复消息==手机号==========="+phoneNumber+"\n");
//
//            String content=(String)contentMap.get("content");
//            System.out.print("=========短信内容==========="+content+"\n");
//
//            String sign_name=(String)contentMap.get("sign_name");
//            System.out.print("=========短信签名==========="+sign_name+"\n");
//
//            String dest_code=(String)contentMap.get("dest_code");
//            System.out.print("=========扩展码==========="+dest_code+"\n");
//
//            Double sequence_id= (Double) contentMap.get("sequence_id");
//            System.out.print("=========消息序列ID==========="+sequence_id+"\n");
//
//            if (content!=null&&phoneNumber!=null){
//                //根据手机号把短信回复内容插入到表里
//                System.out.print("短信放入数据库中");
//            }
//
//        }catch(com.google.gson.JsonSyntaxException e){
////            logger.error("短信回复内容插入失败（RegisteradminApplication之MyMessageListener）：error_json_format:"+message.getMessageBodyAsString(),e);
//            e.printStackTrace();
//            //理论上不会出现格式错误的情况，所以遇见格式错误的消息，只能先delete,否则重新推送也会一直报错
//            return true;
//        } catch (Throwable e) {
//            //您自己的代码部分导致的异常，应该return false,这样消息不会被delete掉，而会根据策略进行重推
//            return false;
//        }
//
//        //消息处理成功，返回true, SDK将调用MNS的delete方法将消息从队列中删除掉
//        return true;
//    }
//
//
//}
