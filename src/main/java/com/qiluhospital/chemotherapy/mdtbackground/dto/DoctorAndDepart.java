package com.qiluhospital.chemotherapy.mdtbackground.dto;

import lombok.Data;

@Data
public class DoctorAndDepart {
    private Long id;
    private String tel_num;
    private String wx_num;
    private String username;
    private String departId;
    private String departsName;
}