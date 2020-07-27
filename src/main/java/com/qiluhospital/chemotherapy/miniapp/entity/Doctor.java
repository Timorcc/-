package com.qiluhospital.chemotherapy.miniapp.entity;

import com.qiluhospital.chemotherapy.miniapp.enums.DocType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "doctor")
@Data
public class Doctor {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String openId;

    private String docName;

    private String profNum;

    private String ideNum;

    private String telNum;

    private Boolean authenticity;

    private Long groupId;

    private Boolean hasRegister;

    private String type;


}
