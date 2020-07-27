package com.qiluhospital.chemotherapy.background.servise;

import com.qiluhospital.chemotherapy.background.view.DocView;
import com.qiluhospital.chemotherapy.miniapp.entity.DocGroup;
import com.qiluhospital.chemotherapy.miniapp.entity.Doctor;
import com.qiluhospital.chemotherapy.util.ResponseResult;

import java.util.List;

public interface BgdDoctorServise {


    List<DocView> getDocList(int pageIndex, int pageSize, String docName);

    long countAllDoc();

    ResponseResult authDoc(Long docId, String state);

    List<DocGroup> getAllDocGroup();

    Doctor getDocById(Long docId);

    boolean modDocGroupAndType(Long docId,Long groupId,String type);

    boolean delDocGroup(Long docGroupId);

    DocGroup getDocGroupById(Long docGroupId);

    boolean addDocGroup(String groupName,String description);

    boolean editDocGroup(String groupName,String description,Long groupId);

    long countByDocName(String docName);

}
