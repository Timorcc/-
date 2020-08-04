package com.qiluhospital.chemotherapy.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j//打印日志
public class MessageUtils {

    // 产品名称:云通信短信API产品,开发者无需替换
    private static final String product = "Dysmsapi";
    // 产品域名,开发者无需替换
    private static final String domain = "dysmsapi.aliyuncs.com";

    private static final String accessKeyId = "LTAI4Fh29msZ2Zc3XzxwoBDH";

    private static final String accessKeySecret = "0ZoVYScULpjt3HVGEemvt1WQfcKmiF";


    public static SendSmsResponse sendName(String telephone, String name) throws ClientException {
        Map<String,Object> params = new HashMap<>();
        params.put("name",name);
        return sendSms(telephone,"SMS_181866047",name,params);
    }
    public static SendSmsResponse sendName1(String telephone, String name) throws ClientException {
        Map<String,Object> params = new HashMap<>();
        params.put("name",name);
        return sendSms(telephone,"SMS_198925864",name,params);
    }
    public static SendSmsResponse sendNameAndDate(String telephone, String name ,String date) throws ClientException {
        Map<String,Object> params = new HashMap<>();
        params.put("name",name);
        params.put("date",date);
        return sendSms(telephone,"SMS_182405479",name,params);
    }



//    public static QuerySendDetailsResponse querySendDetails(String tel, String sendTime) throws ClientException {
//
//        //可自助调整超时时间
//        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
//        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
//
//        //初始化acsClient,暂不支持region化
//        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
//        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
//        IAcsClient acsClient = new DefaultAcsClient(profile);
//
//        //组装请求对象
//        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
//        //必填-号码
//        request.setPhoneNumber(tel);
//        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        request.setSendDate(sdf.format(sendTime));
//        //必填-页大小
//        request.setPageSize(10L);
//        //必填-当前页码从1开始计数
//        request.setCurrentPage(1L);
//        //hint 此处可能会抛出异常，注意catch
//        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);
//        return querySendDetailsResponse;
//    }

    private static SendSmsResponse sendSms(String telephone,String templateCode, String name,Map<String,Object> templateParams)
            throws ClientException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日 HH时mm分ss秒");

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        // 必填:待发送手机号
        request.setPhoneNumbers(telephone);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName("山东大学齐鲁医院化疗科"); // TODO 改这里
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);  // TODO 改这里
        // 可选:模板中的变量替换JSON串
        Iterator<String> ite = templateParams.keySet().iterator();
        StringBuilder paramString = new StringBuilder("{");
        while (ite.hasNext()) {
            String key = ite.next();
            Object value = templateParams.get(key);
            paramString.append("\""+key + "\":\"" + value + "\",");
        }
        paramString.deleteCharAt(paramString.length()-1);
        paramString.append("}");
        request.setTemplateParam(paramString.toString());

        // 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
//        request.setSmsUpExtendCode("90997");

        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//        request.setOutId("11111");

        // hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if(sendSmsResponse.getCode()!= null && sendSmsResponse.getCode().equals("OK")){
            log.info("系统在"+sdf.format(new Date())+"给"+name+"发送短信成功");
            System.out.println("系统在"+sdf.format(new Date())+"给"+name+"发送短信成功");
        }else {
            log.info("系统在"+sdf.format(new Date())+"给"+name+"发送短信失败");
            System.out.println("系统在"+sdf.format(new Date())+"给"+name+"发送短信失败");
        }
        return sendSmsResponse;
    }

//    private static IAcsClient configuration()throws ClientException{
//        //可自助调整超时时间
//        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
//        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
//
//        //初始化acsClient,暂不支持region化
//        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
//        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
//        IAcsClient acsClient = new DefaultAcsClient(profile);
//        return  acsClient;
//    }



}