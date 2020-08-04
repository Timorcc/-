package com.qiluhospital.chemotherapy.miniapp.servise.impl;

import com.alibaba.fastjson.JSONObject;
import com.qiluhospital.chemotherapy.config.ConfigBeanValue;
import com.qiluhospital.chemotherapy.miniapp.entity.*;
import com.qiluhospital.chemotherapy.miniapp.enums.PatConfirmState;
import com.qiluhospital.chemotherapy.miniapp.enums.PatState;
import com.qiluhospital.chemotherapy.miniapp.repository.*;
import com.qiluhospital.chemotherapy.miniapp.servise.PatientService;
import com.qiluhospital.chemotherapy.miniapp.view.PatTodayPlanView;
import com.qiluhospital.chemotherapy.util.MessageUtils;
import com.qiluhospital.chemotherapy.util.MiniappUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatUserRepository patUserRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatUplImgRepository patUplImgRepository;

    @Autowired
    private PatientPainRepository patientPainRepository;

    @Autowired
    private PatVisDateMsgRepository patVisDateMsgRepository;

    @Autowired
    private PatReportRepository patReportRepository;

    @Autowired
    private ConfigBeanValue configBeanValue;

    @Override
    public String initPatUser(String code) {
        JSONObject jsonObject = MiniappUtils.getWxUserInfo(configBeanValue.patAppid,configBeanValue.patSecret,code,
                configBeanValue.grant_type);
        String openId = jsonObject.getString("openid");
        String session_key = jsonObject.getString("session_key");
        if (patUserRepository.findByOpenId(openId)==null){
            PatUser patUser = new PatUser();
            patUser.setOpenId(openId);
            patUserRepository.save(patUser);
        }
        return openId;
    }

    @Override
    public Map<String,Object> bindPatient(String openid, String patName, String ideCard, String patCard, String telNumber,
                               String docId,String preDate,Long groupId) {
        Map<String,Object> res = new HashMap<>();
        try {
            PatUser patUser = patUserRepository.findByOpenId(openid);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Patient patient = patientRepository.findPatientByIdeCard(ideCard);
            //已经绑定并且已经审核通过的需要先解绑
            if (patient!=null && patient.getConfirmState().equals(PatConfirmState.COMFIRMED.getState())){
                //提示未解绑账户
                res.put("msg",2);
                return res;
            }else if (patient!=null && patient.getConfirmState().equals(PatConfirmState.UNCOMFIRMED.getState())){
                //提示该身份证正在审核中
                res.put("msg",3);
                return res;
            }else {//其余情况需要医生重新审核
                patient = new Patient();
                patient.setDoctorId(Long.valueOf(docId));
                patient.setPatName(patName);
                patient.setUserId(patUser.getId());
                patient.setIdeCard(ideCard);
                patient.setTelNumber(telNumber);
                patient.setPatCard(patCard);
                patient.setGroupId(groupId);
                patient.setLatestVisitDate(sdf.parse(preDate));
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(sdf.parse(preDate));
                calendar.add(calendar.DATE,21);
                patient.setEstimatedVisitDate(calendar.getTime());
                PatConfirmState patConfirmState = PatConfirmState.UNCOMFIRMED;
                patient.setConfirmState(patConfirmState.getState());
                patient.setRetIsNormal(true);
                PatState patState = PatState.CURE;
                patient.setState(patState.getState());
                patientRepository.save(patient);
                res.put("msg",1);
                return res;
            }
        }catch (Exception e){
            res.put("msg",0);
            log.error(e.toString());
            return res;
        }
    }

    @Override
    public Map<String,Object> uploadReport(MultipartFile fileList, Long patId, double wbc, double neu, double hgb, double plt,String bind,int readStatus) {
        try {
            Map<String,Object> res = new HashMap<>();
            Patient patient = patientRepository.findOneById(patId);
            if (patient.getUploadDate()!=null){
                //报告单存入数据库
                PatReport patReport = patReportRepository.findPatReportByBind(bind);
                if(patReport==null){
                    patReport = new PatReport();
                    patReport.setHgb(hgb);
                    patReport.setNeu(neu);
                    patReport.setWbc(wbc);
                    patReport.setPlt(plt);
                    patReport.setPatId(patId);
                    patReport.setBind(bind);
                    patReport.setReadStatus(readStatus);
                    Date upDate = new Date();
                    patReport.setUploadDate(upDate);
                    boolean isNormal = false;
                    if(wbc>3.5 && wbc <9.5 && neu>1.8 && neu<6.3 && hgb>130 && hgb<175 && plt>125 && plt<350){
                        isNormal = true;
                    }else {
                        //更新病人报告单情况

                        patient.setRetIsNormal(false);
                        patient.setNewAbnormalRetDate(upDate);
                        patientRepository.save(patient);
                    }
                    patReport.setIsNormal(isNormal);
                    patReport = patReportRepository.save(patReport);
                }
                if (patReport!=null){
                    //上传文件到本地磁盘
                    String pathname = configBeanValue.imgPath;
                    String filename = fileList.getOriginalFilename();
                    File dir = new File(pathname);
                    if(!dir.exists()){
                        dir.mkdirs();
                    }
                    String timeMillis = Long.toString(System.currentTimeMillis());//时间戳
                    String filepath = pathname+timeMillis+"_"+filename;
                    File serverFile = new File(filepath);
                    fileList.transferTo(serverFile);
                    //照片存入数据库
                    PatientUploadImg patientUploadImg = new PatientUploadImg();
                    patientUploadImg.setImgName(timeMillis+"_"+filename);
                    patientUploadImg.setImgPath(filepath);
                    patientUploadImg.setPatientId(patId);
                    patientUploadImg.setUploadDate(new Date());
                    patientUploadImg.setImgSize(fileList.getSize());
                    patientUploadImg.setReportId(patReport.getId());
                    patUplImgRepository.save(patientUploadImg);
                }
                Doctor doctor=doctorRepository.findOneById(patient.getDoctorId());
                System.out.println("----化验单上传----");
                System.out.println(doctor);
                System.out.println("---------");
                //MessageUtils.sendName(doctor.getTelNum(),doctor.getDocName());
                MessageUtils.sendName1(doctor.getTelNum(),doctor.getDocName());

//              MessageUtils.sendNameAndDate(patient.getTelNumber(),patient.getPatName(),sdf2.format(sdf1.parse(certainVisitDate)));
                res.put("msg",true);
            }else {
                //医生未设置上传报告单时间，不能上传
                res.put("msg",false);
            }
            return res;
        }catch (Exception e){
            log.error(e.toString());
            System.out.println("失败");
            return null;
        }
    }

    @Override
    public List<Patient> findPatsByGroupId(String docOpenId) {
        Doctor doctor = doctorRepository.findByOpenId(docOpenId);
        if (doctor!=null){
            if(doctor.getType().equals("nurse")){
                return new ArrayList<>();
            }
            Long groupId = doctor.getGroupId();
            if(groupId!=null){
                PatConfirmState patConfirmState = PatConfirmState.UNCOMFIRMED;
                return patientRepository.findAllByGroupIdAndConfirmState(groupId,patConfirmState.getState());
            }
        }
        return null;
    }

    @Override
    public List<Patient> findPatsByUserOpenidAndConfirmStateNot(String userOpenId,String comState) {
        try{
            PatUser patUser = patUserRepository.findByOpenId(userOpenId);
            return patientRepository.findAllByUserIdAndConfirmStateNot(patUser.getId(),comState);
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public List<Patient> findPatsByUserOpenidAndState(String userOpenId,String state) {
        try {
            PatUser patUser = patUserRepository.findByOpenId(userOpenId);
            PatConfirmState patConfirmState = PatConfirmState.COMFIRMED;
            return patientRepository.findAllByUserIdAndStateAndConfirmState(patUser.getId(),state,patConfirmState.getState());
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public Map<String,Object> uploadPain(Long patId,String painArea,String painChar,String ADR,Integer mostPainGrade,
                              Integer genPainGrade,String medCase,String stopCause) {
        try {
            Map<String,Object> res = new HashMap<>();
            Patient patient = patientRepository.findOneById(patId);
            Doctor doctor=doctorRepository.findOneById(patient.getDoctorId());
            Date painDate = patient.getPainDate();
            if(painDate!=null){
                //疼痛感存入数据库
                PatientPain patientPain = new PatientPain();
                patientPain.setPatientId(patId);
                Date date = new Date();
                patientPain.setUpDate(date);
                patientPain.setPainArea(painArea);
                patientPain.setPainChar(painChar);
                patientPain.setAdr(ADR);
                patientPain.setMostPainGrade(mostPainGrade);
                patientPain.setGenPainGrade(genPainGrade);
                patientPain.setMedCase(medCase);
                patientPain.setStopCause(stopCause);
                patientPainRepository.save(patientPain);
                //修改病人实际上传疼痛感时间
                patient.setActuPainDate(date);
                patientRepository.save(patient);
                res.put("msg",true);
                System.out.println("----痛感上传-----");
                System.out.println(doctor);
                System.out.println("---------");
                MessageUtils.sendName1(doctor.getTelNum(),doctor.getDocName());

            }else {
                res.put("msg",false);
            }
            return res;
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public List<PatVisDateMsg> getPatMsgs(String usrOpenid) {
        try {
            PatUser patUser = patUserRepository.findByOpenId(usrOpenid);
            List<Patient> patients = patientRepository.findAllByUserIdAndConfirmState(patUser.getId(),
                    PatConfirmState.COMFIRMED.getState());
            List<PatVisDateMsg> patVisDateMsgList = new ArrayList<>();
            for (Patient patient:patients){
                Long patId = patient.getId();
                List<PatVisDateMsg> patVisDateMsgs = patVisDateMsgRepository.findAllByPatIdAndHasHandle(patId,false);
                patVisDateMsgList.addAll(patVisDateMsgs);
            }
            return patVisDateMsgList;
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public PatVisDateMsg getPatMsg(String msgId) {
        try {
            PatVisDateMsg patVisDateMsg = patVisDateMsgRepository.findPatVisDateMsgById(Long.valueOf(msgId));
            return patVisDateMsg;
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public boolean affirmPatMsg(String msgId,String state) {
        try {
            PatVisDateMsg patVisDateMsg = patVisDateMsgRepository.findPatVisDateMsgById(Long.valueOf(msgId));
            Patient patient = patientRepository.findOneById(patVisDateMsg.getPatId());
            patVisDateMsg.setHasHandle(true);
            patVisDateMsg.setCerVisitDateState(state);
            patVisDateMsgRepository.save(patVisDateMsg);
            if(state.equals("已同意")){
                patient.setCertainVisitDate(patVisDateMsg.getCerVisitDate());
                patientRepository.save(patient);
            }
            return true;
        }catch (Exception e){
            log.error(e.toString());
            return false;
        }
    }

    @Override
    public boolean autoUpdatePatVisitDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String beginDate = date + "00:00:00";
            String endDate = date + " 23:59:59";
            PatConfirmState patConfirmState = PatConfirmState.COMFIRMED;
            List<Patient> patients = patientRepository.findAllByConfirmStateAndCertainVisitDateBetween(
                    patConfirmState.getState(), sdf.parse(beginDate),sdf.parse(endDate));
            for (Patient patient:patients){
                Date cerVisitDate = patient.getCertainVisitDate();
                patient.setLatestVisitDate(cerVisitDate);
                patient.setEstimatedVisitDate(null);
                patient.setCertainVisitDate(null);
                patientRepository.save(patient);
            }
            return true;
        }catch (Exception e){
            log.error(e.toString());
            return false;
        }
    }

    @Override
    public Patient getPatByPatId(Long patId) {
        try {
            return patientRepository.findOneById(patId);
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public boolean logoff(Long patId) {
        try {
            Patient patient = patientRepository.findOneById(patId);
//            PatConfirmState patConfirmState = PatConfirmState.UNCOMFIRMED;
//            if (patient.getConfirmState().equals(patConfirmState.getState())){
//                patientRepository.delete(patient);
//                return true;
//            }
            patient.setConfirmState(PatConfirmState.CANCELLED.getState());
            patientRepository.save(patient);
            return true;
        }catch (Exception e){
            log.error(e.toString());
            return false;
        }
    }

    @Override
    public List<PatTodayPlanView> getPatPlan(String usrOpenId) {
        try {
            List<PatTodayPlanView> patTodayPlanViewList = new ArrayList<>();
            PatUser patUser = patUserRepository.findByOpenId(usrOpenId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String today = sdf.format(new Date());
            List<Patient> patients = patientRepository.findAllByUserIdAndPainDateAndConfirmState(patUser.getId(),
                    sdf.parse(today), PatConfirmState.COMFIRMED.getState());
            for (Patient patient:patients){
                //所有今天上传痛疼感的人
                PatTodayPlanView patTodayPlanView = new PatTodayPlanView();
                patTodayPlanView.setPatId(patient.getId());
                patTodayPlanView.setPatName(patient.getPatName());
                patTodayPlanView.setType(0);
                patTodayPlanViewList.add(patTodayPlanView);
            }
            patients = patientRepository.findAllByUserIdAndUploadDateAndConfirmState(patUser.getId(),sdf.parse(today),
                    PatConfirmState.COMFIRMED.getState());
            for (Patient patient:patients){
                //所有今天上传化验单的人
                PatTodayPlanView patTodayPlanView = new PatTodayPlanView();
                patTodayPlanView.setPatId(patient.getId());
                patTodayPlanView.setPatName(patient.getPatName());
                patTodayPlanView.setType(1);
                patTodayPlanViewList.add(patTodayPlanView);
            }
            return patTodayPlanViewList;
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

}
