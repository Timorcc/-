package com.qiluhospital.chemotherapy.mdtbackground.service;

import com.qiluhospital.chemotherapy.mdtbackground.entity.BigSecretary;

import java.util.List;

public interface BigSecretaryService {
    List<BigSecretary> getBigSecretaryList();
    Boolean AddBigSecretary(String username, String telNum, String wxNum);
    BigSecretary findById(Long id);
    Boolean updateById(Long id, String username, String telNum, String wxNum);
    List<BigSecretary> fuzzyFind(String name);
}
