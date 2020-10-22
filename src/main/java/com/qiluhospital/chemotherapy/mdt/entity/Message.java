package com.qiluhospital.chemotherapy.mdt.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private Long id;
    private Long chatroom_id;
    private Long sender;
    private String username;
    private Date send_date;
    private String content;
    private String type;//消息类型。0：文字消息/1：图片消息
}
