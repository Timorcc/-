package com.qiluhospital.chemotherapy.miniapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pat_report")
@Data
public class PatReport {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long patId;

    private Double wbc;//白细胞(3.5-9.5)

    private Double neu;//中性粒细胞计数（1.8-6.3）

    private Double hgb;//血红蛋白（130-175）

    private Double plt;//血小板计数（125-350）

    private Date uploadDate;//上传时间

    private Boolean isNormal;//指数是否正常

    private String bind;//绑定图片标识

    private Integer readStatus;//病人上传化验单之后，医生是否阅读。0未读，1已读。

}
