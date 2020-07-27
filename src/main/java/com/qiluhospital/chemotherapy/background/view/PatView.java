package com.qiluhospital.chemotherapy.background.view;

import lombok.Data;

@Data
public class PatView {

    private Long patId;

    private String patName;

    private String ideCard;

    private String telNumber;

    private String patCard;

    private String groupName;

    private String doctorName;

    private String certainVisitDate;//确定的下次就诊时间

    private String estimatedVisitDate;//预计下次就诊时间

    private String latestVisitDate;//最近就诊时间

    private String uploadDate;//医生设置上传体检报告时间

    private String painDate;//护士设置上传疼痛感时间

    private String state;//病人状态（治疗，死亡，痊愈）

    private String comState;//账号状态（已确认，未确认，注销）

}
