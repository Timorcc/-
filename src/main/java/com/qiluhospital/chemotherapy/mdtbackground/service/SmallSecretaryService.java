package com.qiluhospital.chemotherapy.mdtbackground.service;

import com.qiluhospital.chemotherapy.mdtbackground.entity.SmallSecretary;

import java.util.List;

public interface SmallSecretaryService {
    List<SmallSecretary> getSmallSecretaryList();
    Boolean AddSmallSecretary(String username, String telNum, String wxNum);
    SmallSecretary findById(Long id);
    Boolean updateById(Long id, String username, String telNum, String wxNum);
    List<SmallSecretary> fuzzyFind(String name);

}
