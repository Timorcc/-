package com.qiluhospital.chemotherapy.miniapp.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @param
 * @author liuguoan
 * @date 2022-01-18
 * @Description 医生
 * @return
 */

@Entity
@Table(name = "doctor")
@Data
public class Doctor implements Serializable {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * openId
     */
    private String openId;
    /**
     * 医生姓名
     */
    private String docName;
    /**
     * 职业编号
     */
    private String profNum;
    /**
     * 身份证号
     */
    private String ideNum;
    /**
     * 电话号码
     */
    private String telNum;
    /**
     * 是否认证
     */
    private Boolean authenticity;
    /**
     * 分组编号
     */
    private Long groupId;
    /**
     * 是否注册
     */
    private Boolean hasRegister;
    /**
     * 医生类型
     */
    private String type;


}
