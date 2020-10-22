package com.qiluhospital.chemotherapy.background.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data

public class BgdAdmin {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String description;
    
}
