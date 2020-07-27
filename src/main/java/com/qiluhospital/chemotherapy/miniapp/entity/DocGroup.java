package com.qiluhospital.chemotherapy.miniapp.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "doc_group")
@Data
public class DocGroup {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String groupName;

    private String description;
}
