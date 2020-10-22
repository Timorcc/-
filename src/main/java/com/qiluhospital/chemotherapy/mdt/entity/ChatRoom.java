package com.qiluhospital.chemotherapy.mdt.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ChatRoom {
    private Long id;
    private String name;
    private Date create_date;
    private String create_admin_id;
    private Boolean state;
    private Date end_date;
    private Long depart_id;
    private String conclusion;
}
