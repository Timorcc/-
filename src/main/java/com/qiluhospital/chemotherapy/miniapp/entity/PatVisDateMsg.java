package com.qiluhospital.chemotherapy.miniapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pat_vis_date_msg")
@Data
public class PatVisDateMsg {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long patId;

    private String patName;

    private String tel;

    private Date cerVisitDate;//确认就诊时间日期

    private String content;//消息内容

    private Boolean hasHandle;//病人是否处理

    private String cerVisitDateState;//下次就诊时间确认状态（已拒绝，已同意，未处理）

    private Long docId;//发送该消息医生id

    private String docName;//发送该消息医生姓名

    private Boolean isValid;//是否有效

}
