package com.qiluhospital.chemotherapy.miniapp.servise.impl;

import com.alibaba.fastjson.JSONObject;
import com.qiluhospital.chemotherapy.config.ConfigBeanValue;
import com.qiluhospital.chemotherapy.miniapp.entity.*;
import com.qiluhospital.chemotherapy.miniapp.enums.DocType;
import com.qiluhospital.chemotherapy.miniapp.enums.PatConfirmState;
import com.qiluhospital.chemotherapy.miniapp.enums.PatState;
import com.qiluhospital.chemotherapy.miniapp.repository.*;
import com.qiluhospital.chemotherapy.miniapp.servise.DoctorService;
import com.qiluhospital.chemotherapy.miniapp.view.GroupAndDoctorView;
import com.qiluhospital.chemotherapy.miniapp.view.PatPainView;
import com.qiluhospital.chemotherapy.miniapp.view.PatReportView;
import com.qiluhospital.chemotherapy.util.MessageUtils;
import com.qiluhospital.chemotherapy.util.MiniappUtils;
import com.qiluhospital.chemotherapy.util.ResponseResult;
import com.qiluhospital.chemotherapy.util.ResponseResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientPainRepository patientPainRepository;

    @Autowired
    private PatUplImgRepository patUplImgRepository;

    @Autowired
    private DocGroupRepository docGroupRepository;

    @Autowired
    private PatVisDateMsgRepository patVisDateMsgRepository;

    @Autowired
    private PatReportRepository patReportRepository;

    @Autowired
    private ConfigBeanValue configBeanValue;

    @Autowired
    private CommServiceImpl commService;

    @Autowired
    private UploadDateRepository uploadDateRepository;

    @Override
    public List<GroupAndDoctorView> findAllRealDoc() {
        try {
            List<GroupAndDoctorView> groupAndDoctorViewList = new ArrayList<>();
            List<DocGroup> docGroups = docGroupRepository.findAll();
            for (DocGroup docGroup:docGroups){
                GroupAndDoctorView groupAndDoctorView = new GroupAndDoctorView();
                Long groupId = docGroup.getId();
                String groupName = docGroup.getGroupName();
                List<Doctor> doctorList = doctorRepository.findAllByAuthenticityAndGroupIdAndType(true,
                        groupId,DocType.DOCTOR.getType());
                groupAndDoctorView.setGroupName(groupName);
                groupAndDoctorView.setGroupId(groupId);
                groupAndDoctorView.setDoctors(doctorList);
                groupAndDoctorViewList.add(groupAndDoctorView);
            }
            return groupAndDoctorViewList;
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public ResponseResult initDoctor(String code) {
        JSONObject jsonObject = MiniappUtils.getWxUserInfo(configBeanValue.docAppid,configBeanValue.docSecret,code,
                configBeanValue.grant_type);
        String openId = jsonObject.getString("openid");
        String session_key = jsonObject.getString("session_key");
        String unionid = jsonObject.getString("unionid");
        if (doctorRepository.findByOpenId(openId)==null){
            Doctor doctor = new Doctor();
            doctor.setOpenId(openId);
            doctor.setHasRegister(false);
            doctorRepository.save(doctor);
        }
        return ResponseResultUtils.success((Object) openId);
    }

    @Override
    public boolean authDoctor(String openId,String docName,String telNum,String ideNum,String profNum,Long groupId) {
        Doctor doctor = doctorRepository.findByOpenId(openId);
        if(doctor!=null){
            doctor.setDocName(docName);
            doctor.setTelNum(telNum);
            doctor.setIdeNum(ideNum);
            doctor.setProfNum(profNum);
            doctor.setHasRegister(true);
            doctor.setAuthenticity(false);
            doctor.setGroupId(groupId);
            DocType docType = DocType.DOCTOR;
            doctor.setType(docType.getType());
            try {
                doctorRepository.save(doctor);
                return true;
            }catch (Exception e){
                log.error(e.toString());
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean verifyPatient(String docOpenId, Long patId,String confirmState) {
        Patient patient = patientRepository.findOneById(patId);
        try{
            patient.setConfirmState(confirmState);
            PatState patState = PatState.CURE;
            patient.setState(patState.getState());
            patientRepository.save(patient);
            return true;
        }catch (Exception e){
            log.error(e.toString());
            return false;
        }
    }

    @Override
    public List<PatReportView> findPatReportByPatId(Long patId) {
        try{
            List<PatReportView> patReportViewList = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Sort sort = Sort.by(Sort.Direction.DESC,"uploadDate");
            List<PatReport> patReports = patReportRepository.findAllByPatIdAndIsNormal(patId,false,sort);
            for (PatReport patReport:patReports){
                PatReportView patReportView = new PatReportView();
                patReportView.setHgb(patReport.getHgb());
                patReportView.setPlt(patReport.getPlt());
                patReportView.setNeu(patReport.getNeu());
                patReportView.setWbc(patReport.getWbc());

                patReportView.setReadStatus(patReport.getReadStatus());
                patReportView.setBind(patReport.getBind());
                patReportView.setUpDate(sdf.format(patReport.getUploadDate()));
                System.out.println("=====");
                System.out.println("patReport"+patReport);
                System.out.println("patReport.getId()"+patReport.getId());
                System.out.println("=====");
                List<PatientUploadImg> patientUploadImgs = patUplImgRepository.findAllByReportId(patReport.getId());
                System.out.println("patientUploadImgs=---=>"+patientUploadImgs);
                if (patientUploadImgs !=null && patientUploadImgs.size()!=0){
                    PatientUploadImg patientUploadImg = patientUploadImgs.get(0);
                    Patient patient = patientRepository.findOneById(patientUploadImg.getPatientId());
                    patReportView.setPatName(patient.getPatName());
                    patReportView.setTel(patient.getTelNumber());
                    patReportView.setPatCard(patient.getPatCard());
                    List<String> imgPaths = new ArrayList<>();
                    for (PatientUploadImg uploadImg: patientUploadImgs){
                        String imgName = uploadImg.getImgName();
                        imgPaths.add(configBeanValue.pathPattern+imgName);
                    }
                    patReportView.setImgPaths(imgPaths);
                }
                patReportViewList.add(patReportView);
            }
            return patReportViewList;
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public List<Patient> findPatsByConfirmState(String docOpenId) {
        Doctor doctor = doctorRepository.findByOpenId(docOpenId);
        if(doctor!=null){
            if(doctor.getType().equals(DocType.NURSE.getType())){
                return new ArrayList<>();
            }
            Long groupId = doctor.getGroupId();
            if(groupId!=null){
                return patientRepository.findAllByGroupIdAndConfirmStateAndState(groupId,
                        PatConfirmState.COMFIRMED.getState(),PatState.CURE.getState());
            }
        }
        return null;
    }

    @Override
    public List<DocGroup> getGroups() {
        try {
            return docGroupRepository.findAll();
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public boolean hasRegister(String openid) {
        Doctor doctor = doctorRepository.findByOpenId(openid);
        if (doctor!=null){
            return doctor.getHasRegister();
        }
        return false;
    }

    @Override
    public Map<String,Object> findOneByOpenid(String openid) {
        Map<String,Object> res = new HashMap<>();
        try {
            Doctor doctor = doctorRepository.findByOpenId(openid);
            if (doctor!=null){
                res.put("doctor",doctor);
                Long groupId = doctor.getGroupId();
                DocGroup docGroup = docGroupRepository.findOneById(groupId);
                res.put("group",docGroup);
            }
            return res;
        }catch (Exception e){
            log.error(e.toString());
            return res;
        }
    }

    @Override
    public Page<Patient> findPatsByGroupOrderByLastedDay(String docOpenid,int page,int size,String currentTab) {
        try {
            Doctor doctor = doctorRepository.findByOpenId(docOpenid);
            Long groupId = doctor.getGroupId();
            Sort sort;
            if (currentTab.equals("0")){
                sort = Sort.by(Sort.Direction.ASC,"latestVisitDate");
            }else {
                sort = Sort.by(Sort.Direction.ASC,"estimatedVisitDate");
            }
            Pageable pageable = PageRequest.of(page,size,sort);
            PatConfirmState patConfirmState = PatConfirmState.COMFIRMED;
            PatState patState = PatState.CURE;
            Page<Patient> patients;
            if(doctor.getType().equals(DocType.DOCTOR.getType())){
                patients = patientRepository.findAllByGroupIdAndConfirmStateAndState(groupId,
                        patConfirmState.getState(),patState.getState(),pageable);
            }else {
                patients = patientRepository.findAllByConfirmStateAndState(
                        patConfirmState.getState(),patState.getState(),pageable);
            }
            return patients;
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public List<Patient> findPatsByPatName(String docOpenid, String patName) {
        try {
            Doctor doctor = doctorRepository.findByOpenId(docOpenid);
            Long groupId = doctor.getGroupId();
            PatConfirmState patConfirmState = PatConfirmState.COMFIRMED;
            PatState patState = PatState.CURE;
            List<Patient> patients;
            if(doctor.getType().equals(DocType.DOCTOR.getType())){
                patients = patientRepository.findAllByGroupIdAndPatNameContainingAndConfirmStateAndState(
                        groupId,patName,patConfirmState.getState(),patState.getState());
            }else {
                patients = patientRepository.findAllByPatNameContainingAndConfirmStateAndState(
                        patName,patConfirmState.getState(),patState.getState());
            }
            System.out.println("patients -----=====>>>");
            System.out.println(patients);
            return patients;
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public Map<String, Object> findPatByDocOpenid(String docOpenid, String patId) {
        try{
            Doctor doctor = doctorRepository.findByOpenId(docOpenid);
            Patient patient = patientRepository.findOneById(Long.valueOf(patId));
            Map<String,Object> res = new HashMap<>();
            res.put("doctor",doctor);
            res.put("patient",patient);
            return res;
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public boolean modifyPatDate(String openid, String patId, String date,String type) {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Patient patient = patientRepository.findOneById(Long.valueOf(patId));
            List<UploadDate> polyDate = uploadDateRepository.findByUserId(Long.valueOf(patId));
            if(patient!=null){
                if (type.equals("visit")){
                    patient.setEstimatedVisitDate(sdf.parse(date));
                }
                if (type.equals("pain")){
                    patient.setPainDate(sdf.parse(date));
                }
                if (type.equals("report")){

                    patient.setUploadDate(sdf.parse(date));
                }
                patientRepository.save(patient);
                return true;
            }
            return false;
        }catch (Exception e){
            log.error(e.toString());
            return false;
        }
    }

    @Override
    public Map<String,Object> modifyPatCertainDate(String openid, String patId,String certainVisitDate){
        Map<String,Object> res = new HashMap<>();
        try{
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
            Patient patient = patientRepository.findOneById(Long.valueOf(patId));
            Doctor doctor = doctorRepository.findByOpenId(openid);
            String today = sdf1.format(new Date()) + " 00:00:00";
            //如果今天之后存在未处理的消息，不允许设置
            List<PatVisDateMsg> patVisDateMsgs =  patVisDateMsgRepository.findAllByPatIdAndHasHandleAndCerVisitDateAfter(
                    patient.getId(), false,sdf1.parse(today));
            if(patVisDateMsgs!=null && patVisDateMsgs.size()!=0){
                res.put("allow",false);
                return res;
            }
            //如果存在设置同一天的情况，不允许设置
            patVisDateMsgs = patVisDateMsgRepository.findAllByPatIdAndCerVisitDate(patient.getId(),sdf1.parse(certainVisitDate+" 00:00:00"));
            if(patVisDateMsgs!=null && patVisDateMsgs.size()!=0){
                res.put("allow",false);
                return res;
            }

            patVisDateMsgs = patVisDateMsgRepository.findAllByPatIdAndHasHandleAndCerVisitDateBefore(
                    patient.getId(), false,sdf1.parse(today));
            if(patVisDateMsgs!=null && patVisDateMsgs.size()!=0){
                for (PatVisDateMsg patVisDateMsg:patVisDateMsgs){
                    patVisDateMsgRepository.delete(patVisDateMsg);
                }
            }

            PatVisDateMsg patVisDateMsg = new PatVisDateMsg();
            patVisDateMsg.setPatId(patient.getId());
            patVisDateMsg.setHasHandle(false);
            patVisDateMsg.setTel(patient.getTelNumber());
            patVisDateMsg.setCerVisitDate(sdf1.parse(certainVisitDate+" 00:00:01"));
            patVisDateMsg.setPatName(patient.getPatName());
            patVisDateMsg.setCerVisitDateState("未确认");
            patVisDateMsg.setIsValid(true);
            patVisDateMsg.setDocId(doctor.getId());
            patVisDateMsg.setDocName(doctor.getDocName());
            patVisDateMsgRepository.save(patVisDateMsg);
            patient.setPreCertainVisitDate(sdf1.parse(certainVisitDate));
            patientRepository.save(patient);
            res.put("allow",true);

            //给病人发送短信提醒
            MessageUtils.sendNameAndDate(patient.getTelNumber(),patient.getPatName(),sdf2.format(sdf1.parse(certainVisitDate)));
//            MessageUtils.sendName(patient.getTelNumber(),patient.getPatName());
            return res;
        }catch (Exception e){
            res.put("allow",null);
            log.error(e.toString());
            return res;
        }
    }

    @Override
    public List<PatVisDateMsg> getPatsByCertainVistDate(String docOpenid, String certainVisitDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Doctor doctor = doctorRepository.findByOpenId(docOpenid);
            Long groupId = doctor.getGroupId();
            PatConfirmState patConfirmState = PatConfirmState.COMFIRMED;
            PatState patState = PatState.CURE;
            List<Patient> patients = patientRepository.findAllByGroupIdAndConfirmStateAndPreCertainVisitDateAndState(groupId,
                    patConfirmState.getState(),sdf.parse(certainVisitDate),patState.getState());
            List<PatVisDateMsg> patVisDateMsgList = new ArrayList<>();
            for (Patient patient:patients){
                patVisDateMsgList.addAll(patVisDateMsgRepository.findAllByPatIdAndCerVisitDate(patient.getId(),sdf.parse(certainVisitDate)));
            }
            return patVisDateMsgList;
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public Page<Patient> findPatsByIsNormal(String docOpenid,int page,int size) {
        try {
            Doctor doctor = doctorRepository.findByOpenId(docOpenid);
                Sort sort = Sort.by(Sort.Direction.DESC,"newAbnormalRetDate");
                Pageable pageable = PageRequest.of(page,size,sort);
                PatConfirmState patConfirmState = PatConfirmState.COMFIRMED;
                PatState patState = PatState.CURE;
                Page<Patient> patients = patientRepository.findAllByRetIsNormalAndGroupIdAndConfirmStateAndState(
                        false, doctor.getGroupId(),patConfirmState.getState(),patState.getState(),pageable);
                return patients;
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }


    }

    @Override
    public Page<Patient> findPatsByPainDateIsNull(String docOpenId,int page,int size) {
        try {
            Doctor doctor = doctorRepository.findByOpenId(docOpenId);
//            if(doctor.getType().equals("nurse")){
                Sort sort = Sort.by(Sort.Direction.DESC,"actuPainDate");
                Pageable pageable = PageRequest.of(page,size,sort);
            PatConfirmState patConfirmState = PatConfirmState.COMFIRMED;
            PatState patState = PatState.CURE;
                Page<Patient> patients = patientRepository.findAllByPainDateIsNotNullAndConfirmStateAndActuPainDateIsNotNullAndState(
                                patConfirmState.getState(),patState.getState(),pageable);
                return patients;
//            }
//            return null;
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public List<PatPainView> findPatPainsByPat(Long patId) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            List<PatPainView> patPainViews = new ArrayList<>();
            Sort sort = Sort.by(Sort.Direction.DESC,"upDate");
            List<PatientPain> patientPains = patientPainRepository.findAllByPatientId(patId,sort);
            Patient patient = patientRepository.findOneById(patId);
            for (PatientPain patientPain:patientPains){
                PatPainView patPainView = new PatPainView();
                patPainView.setPatCard(patient.getPatCard());
                patPainView.setPatName(patient.getPatName());
                patPainView.setTel(patient.getTelNumber());
                patPainView.setUpDate(sdf.format(patientPain.getUpDate()));
                patPainView.setAdr(patientPain.getAdr());
                patPainView.setGenPainGrade(patientPain.getGenPainGrade());
                patPainView.setMostPainGrade(patientPain.getMostPainGrade());
                patPainView.setMedCase(patientPain.getMedCase());
                patPainView.setPainArea(patientPain.getPainArea());
                patPainView.setPainChar(patientPain.getPainChar());
                patPainView.setStopCause(patientPain.getStopCause());
                patPainView.setPatientId(patId);
                patPainViews.add(patPainView);
            }
            return patPainViews;
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public Doctor findDocByOpenId(String openid) {
        try {
            Doctor doctor = doctorRepository.findByOpenId(openid);
            return doctor;
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public Doctor findDocById(Long docId) {
        try {
            Doctor doctor = doctorRepository.findOneById(docId);
            return doctor;
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public boolean freezePat(Long patId) {
        try {
            Patient patient = patientRepository.findOneById(patId);
            PatState patState = PatState.RECOVERY;
            patient.setState(patState.getState());
            patientRepository.save(patient);
            return true;
        }catch (Exception e){
            log.error(e.toString());
            return false;
        }
    }


    @Override
    public boolean setReadStatus(String bind) {
        PatReport patReport = patReportRepository.findPatReportByBind(bind);
        System.out.println("根据bind查询到的病人是：" +patReport);
        if(patReport != null){
//           patReportRepository.
            patReport.setReadStatus(1);
            patReportRepository.save(patReport);
            System.out.println("修改后"+patReport);
            return true;
        }else{
            return false;
        }

    }
}
