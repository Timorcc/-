package com.qiluhospital.chemotherapy.background.servise.impl;

import com.qiluhospital.chemotherapy.background.servise.BgdPatientServise;
import com.qiluhospital.chemotherapy.background.view.PatView;
import com.qiluhospital.chemotherapy.miniapp.entity.DocGroup;
import com.qiluhospital.chemotherapy.miniapp.entity.Doctor;
import com.qiluhospital.chemotherapy.miniapp.entity.Patient;
import com.qiluhospital.chemotherapy.miniapp.enums.PatState;
import com.qiluhospital.chemotherapy.miniapp.repository.DocGroupRepository;
import com.qiluhospital.chemotherapy.miniapp.repository.DoctorRepository;
import com.qiluhospital.chemotherapy.miniapp.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class BgdPatientServiseImpl implements BgdPatientServise {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DocGroupRepository docGroupRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public long countAllPat(String state) {
        try {
            return patientRepository.countAllByState(state);
        }catch (Exception e){
            log.error(e.toString());
            return 0;
        }
    }

    @Override
    public long countPatByPatName(String patName,String state) {
        try {
            return patientRepository.countAllByPatNameContainingAndState(patName,state);
        }catch (Exception e){
            log.error(e.toString());
            return 0;
        }
    }

    @Override
    public List<PatView> getAllPat(int pageIndex, int pageSize, String patName,String state) {
        List<PatView> patViewList = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Sort sort = Sort.by(Sort.Direction.ASC,"patName");
            Pageable pageable = PageRequest.of(pageIndex,pageSize,sort);
            Page<Patient> patients;
            if(patName==null){
                patients  = patientRepository.findAllByState(state,pageable);
            }else {
                patients = patientRepository.findAllByStateAndPatNameContaining(state,patName,pageable);
            }
            for (Patient patient:patients){
                PatView patView = new PatView();
                patView.setPatId(patient.getId());
                patView.setPatName(patient.getPatName());
                patView.setIdeCard(patient.getIdeCard());
                patView.setPatCard(patient.getPatCard());
                patView.setTelNumber(patient.getTelNumber());
                patView.setComState(patient.getConfirmState());
                DocGroup docGroup = docGroupRepository.findOneById(patient.getGroupId());
                patView.setGroupName(docGroup.getGroupName());
                Doctor doctor = doctorRepository.findOneById(patient.getDoctorId());
                patView.setDoctorName(doctor.getDocName());
                Date cerVisDate = patient.getCertainVisitDate();
                if(cerVisDate!=null){
                    patView.setCertainVisitDate(sdf.format(cerVisDate));
                }
                Date estVisDate = patient.getEstimatedVisitDate();
                if (estVisDate!=null){
                    patView.setEstimatedVisitDate(sdf.format(estVisDate));
                }
                Date latVisDate = patient.getLatestVisitDate();
                if (latVisDate!=null){
                    patView.setLatestVisitDate(sdf.format(latVisDate));
                }
                Date painDate = patient.getPainDate();
                if (painDate!=null){
                    patView.setPainDate(sdf.format(painDate));
                }
                Date upDate = patient.getUploadDate();
                if (upDate!=null){
                    patView.setUploadDate(sdf.format(upDate));
                }
                if (state.equals(PatState.CURE.getState())){
                    patView.setState("治疗中");
                }
                if (state.equals(PatState.RECOVERY.getState())){
                    patView.setState("痊愈");
                }
                if (state.equals(PatState.DEAD.getState())){
                    patView.setState("失效");
                }
                patViewList.add(patView);
            }
            return patViewList;
        }catch (Exception e){
            log.error(e.toString());
            return patViewList;
        }
    }

    @Override
    public boolean setPatState(Long patId,String state) {
        try {
            Patient patient = patientRepository.findOneById(patId);
            patient.setState(state);
            patientRepository.save(patient);
            return true;
        }catch (Exception e){
            log.error(e.toString());
            return false;
        }
    }



}
