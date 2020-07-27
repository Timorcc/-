package com.qiluhospital.chemotherapy.miniapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "patient_pain")
@Data
public class PatientPain {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;//id

    private Long patientId;//所属病人id

    private Date upDate;//上传时间

    private String painArea;//疼痛部位

    private String painChar;//疼痛性质

    private String adr;//不良反应

    private Integer mostPainGrade;//24h最疼分值

    private Integer genPainGrade;//一般状态疼痛分值

    private String medCase;//服药情况

    private String stopCause;//停止服药的原因

}
