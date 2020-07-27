package com.qiluhospital.chemotherapy.miniapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "patient")
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String patName;

    private String ideCard;

    private String telNumber;

    private String patCard;

    private String sex;

    private Date birthday;

    private Long groupId;

    private Long doctorId;

    private Long userId;

    private String confirmState;//病人账号确认状态(已确认，未确认，已注销)

    private Date certainVisitDate;//确定的下次就诊时间

    private Date preCertainVisitDate;//预设确定的下次就诊时间

    private Date estimatedVisitDate;//预计下次就诊时间

    private Date latestVisitDate;//最近就诊时间

    private Date uploadDate;//医生设置上传体检报告时间

    private Date painDate;//护士设置上传疼痛感时间

    private Date newAbnormalRetDate;//病人实际上传体检报告时间

    private Boolean retIsNormal;//病人体检报告是否正常

    private Date actuPainDate;//病人实际上传疼痛感时间

    private String state;//病人状态（cure，dead，recovery）


}
