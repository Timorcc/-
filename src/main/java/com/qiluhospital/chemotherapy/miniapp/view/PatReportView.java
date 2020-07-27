package com.qiluhospital.chemotherapy.miniapp.view;

import lombok.Data;

import java.util.List;

@Data
public class PatReportView {

    private List<String> imgPaths;

    private String patName;

    private String patCard;

    private String upDate;

    private String tel;

    private Double wbc;//白细胞(3.5-9.5)

    private Double neu;//中性粒细胞计数（1.8-6.3）

    private Double hgb;//血红蛋白（130-175）

    private Double plt;//血小板计数（125-350）

}
