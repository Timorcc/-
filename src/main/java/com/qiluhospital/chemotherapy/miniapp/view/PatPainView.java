package com.qiluhospital.chemotherapy.miniapp.view;

import lombok.Data;

import java.util.Date;

@Data
public class PatPainView {

    private String patName;//病人名称

    private String patCard;//就诊卡号

    private String tel;//电话号码

    private Long patientId;//所属病人id

    private String upDate;//上传时间

    private String painArea;//疼痛部位

    private String painChar;//疼痛性质

    private String adr;//不良反应

    private Integer mostPainGrade;//24h最疼分值

    private Integer genPainGrade;//一般状态疼痛分值

    private String medCase;//服药情况

    private String stopCause;//停止服药的原因

}
