package com.qiluhospital.chemotherapy.miniapp.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "patient_user")
@Data
public class PatUser {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String openId;

    private String userName;

    private String ideCard;

    private String telNumber;

    private String sex;

    private Date birthday;
}
