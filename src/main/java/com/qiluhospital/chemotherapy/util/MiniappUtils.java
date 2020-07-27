package com.qiluhospital.chemotherapy.util;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MiniappUtils {
    public static JSONObject getWxUserInfo(String appid,String secret,String code,String grant_type) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appid",appid);
        params.put("secret",secret);
        params.put("js_code",code);
        params.put("grant_type",grant_type);
        String result = HttpUtils.sendGetRequest(url,params);
        return JSONObject.parseObject(result);
    }
}
