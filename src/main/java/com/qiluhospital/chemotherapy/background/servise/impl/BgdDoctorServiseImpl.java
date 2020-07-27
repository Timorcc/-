package com.qiluhospital.chemotherapy.background.servise.impl;

import com.qiluhospital.chemotherapy.background.servise.BgdDoctorServise;
import com.qiluhospital.chemotherapy.background.view.DocView;
import com.qiluhospital.chemotherapy.miniapp.entity.DocGroup;
import com.qiluhospital.chemotherapy.miniapp.entity.Doctor;
import com.qiluhospital.chemotherapy.miniapp.repository.DocGroupRepository;
import com.qiluhospital.chemotherapy.miniapp.repository.DoctorRepository;
import com.qiluhospital.chemotherapy.util.ResponseResult;
import com.qiluhospital.chemotherapy.util.ResponseResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BgdDoctorServiseImpl implements BgdDoctorServise {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DocGroupRepository docGroupRepository;

    @Override
    public List<DocView> getDocList(int pageIndex, int pageSize, String docName) {
        List<DocView> docViewList = new ArrayList<>();
        try {
            Sort sort = Sort.by(Sort.Direction.ASC,"docName");
            Pageable pageable = PageRequest.of(pageIndex,pageSize,sort);
            Page<Doctor> doctors;
            if(docName==null){
                doctors  = doctorRepository.findAll(pageable);
            }else {
                doctors = doctorRepository.findAllByDocNameContaining(docName,pageable);
            }
            for (Doctor doctor:doctors){
                DocView docView = new DocView();
                docView.setDocId(doctor.getId());
                docView.setDocName(doctor.getDocName());
                docView.setIdeNum(doctor.getIdeNum());
                docView.setProfNum(doctor.getProfNum());
                docView.setTelNum(doctor.getTelNum());
                String auth = "未确认";
                if (doctor.getAuthenticity()){
                    auth = "已确认";
                }
                docView.setAuthenticity(auth);
                String type = "护士";
                if (doctor.getType().equals("doctor")){
                    type = "医生";
                }
                docView.setType(type);
                DocGroup docGroup = docGroupRepository.findOneById(doctor.getGroupId());
                docView.setGroupName(docGroup.getGroupName());
                docViewList.add(docView);
            }
            return docViewList;
        }catch (Exception e){
            log.error(e.toString());
            return docViewList;
        }
    }

    @Override
    public long countAllDoc() {
        try {
            return doctorRepository.count();
        }catch (Exception e){
            log.error(e.toString());
            return 0;
        }

    }

    @Override
    public ResponseResult authDoc(Long docId, String state) {
        try {
            Doctor doctor = doctorRepository.findOneById(docId);
            if(state.equals("yes")){
                doctor.setAuthenticity(true);
            }
            if(state.equals("cancel")){
                doctor.setAuthenticity(false);
            }
            doctorRepository.save(doctor);
            return ResponseResultUtils.success();
        }catch (Exception e){
            log.error(e.toString());
            return ResponseResultUtils.error(e);
        }
    }

    @Override
    public List<DocGroup> getAllDocGroup() {
        try {
            return docGroupRepository.findAll();
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public Doctor getDocById(Long docId) {
        try {
            return doctorRepository.findOneById(docId);
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public boolean modDocGroupAndType(Long docId, Long groupId, String type) {
        try {
            Doctor doctor = doctorRepository.findOneById(docId);
            doctor.setGroupId(groupId);
            doctor.setType(type);
            doctorRepository.save(doctor);
            return true;
        }catch (Exception e){
            log.error(e.toString());
            return false;
        }
    }

    @Override
    public boolean delDocGroup(Long docGroupId) {
        try {
            DocGroup docGroup = docGroupRepository.findOneById(docGroupId);
            docGroupRepository.delete(docGroup);
            return true;
        }catch (Exception e){
            log.error(e.toString());
            return false;
        }
    }

    @Override
    public DocGroup getDocGroupById(Long docGroupId) {
        try {
            return docGroupRepository.findOneById(docGroupId);
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public boolean addDocGroup(String groupName, String description) {
        try {
            DocGroup docGroup = new DocGroup();
            docGroup.setGroupName(groupName);
            docGroup.setDescription(description);
            docGroupRepository.save(docGroup);
            return true;
        }catch (Exception e){
            log.error(e.toString());
            return false;
        }
    }

    @Override
    public boolean editDocGroup(String groupName, String description, Long groupId) {
        try {
            DocGroup docGroup = docGroupRepository.findOneById(groupId);
            docGroup.setGroupName(groupName);
            docGroup.setDescription(description);
            docGroupRepository.save(docGroup);
            return true;
        }catch (Exception e){
            log.error(e.toString());
            return false;
        }
    }

    @Override
    public long countByDocName(String docName) {
        try {
            return doctorRepository.countByDocNameContaining(docName);
        }catch (Exception e){
            log.error(e.toString());
            return 0;
        }

    }

}
