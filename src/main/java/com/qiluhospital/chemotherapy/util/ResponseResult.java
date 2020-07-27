package com.qiluhospital.chemotherapy.util;

import lombok.Data;

@Data
public class ResponseResult {

    /**
     * 请求状态
     */
    private boolean success;
    /**
     * 返回提示信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private Object data;

    ResponseResult(boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    ResponseResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    ResponseResult(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    ResponseResult(boolean success) {
        this.success = success;
    }
}
