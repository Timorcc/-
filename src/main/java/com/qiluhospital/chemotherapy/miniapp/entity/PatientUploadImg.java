package com.qiluhospital.chemotherapy.miniapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "patient_upload_img")
@Data
public class PatientUploadImg {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;//id

    private String imgName;//上传图片名字

    private Long imgSize;//上传文件大小

    private String imgPath;//上传文件对象路径

    private Date uploadDate;//上传时间

    private Long patientId;//所属病人id

    private Long reportId;//所属报告单id

}
