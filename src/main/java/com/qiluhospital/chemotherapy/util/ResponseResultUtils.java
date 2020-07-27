package com.qiluhospital.chemotherapy.util;

public class ResponseResultUtils {

    /**
     * 请求成功，不携带数据和提示信息
     */
    public static ResponseResult success() {
        return new ResponseResult(true);
    }

    /**
     * 请求成功，不携带数据和提示信息
     */
    public static ResponseResult success(String msg) {
        return new ResponseResult(true,msg);
    }

    /**
     * 请求成功,携带数据
     */
    public static ResponseResult success(Object object) {
        return new ResponseResult(true, object);
    }

    /**
     * 请求成功，携带提示信息和数据
     */
    public static ResponseResult success(String msg, Object object) {
        return new ResponseResult(true, msg, object);
    }

    /**
     * 请求失败，不返回提示信息和异常信息
     */
    public static ResponseResult error() {
        return new ResponseResult(false);
    }

    /**
     * 请求失败，返回异常信息
     */
    public static ResponseResult error(Exception e) {
        return new ResponseResult(false, e.getMessage());
    }

    /**
     * 请求失败，返回提示信息
     */
    public static ResponseResult error(String msg) {
        return new ResponseResult(false, msg);
    }

    /**
     * 请求失败，返回提示信息和异常信息
     */
    public static ResponseResult error(String msg,Exception e) {
        return new ResponseResult(false, msg,e.getMessage());
    }


}
