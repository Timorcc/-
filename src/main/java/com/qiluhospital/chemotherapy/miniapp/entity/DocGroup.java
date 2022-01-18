package com.qiluhospital.chemotherapy.miniapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @param
 * @author liuguoan
 * @date 2022-01-18
 * @Description 分组
 * @return
 */

@Entity
@Table(name = "doc_group")
@Data
public class DocGroup implements Serializable {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 分组描述
     */
    private String description;
}
