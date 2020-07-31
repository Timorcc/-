package com.qiluhospital.chemotherapy.miniapp.servise;

import com.qiluhospital.chemotherapy.miniapp.entity.PatVisDateMsg;
import com.qiluhospital.chemotherapy.miniapp.entity.Patient;
import com.qiluhospital.chemotherapy.miniapp.view.PatTodayPlanView;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface PatientService {


    String initPatUser(String code);

    Map<String,Object> bindPatient(String openid, String patName, String ideCard, String patCard, String telNumber, String docId,
                        String preDate,Long groupId) throws ParseException;

    Map<String,Object> uploadReport(MultipartFile fileList, Long patId, double wbc, double neu, double hgb, double plt,String bind,int readStatus);

    List<Patient> findPatsByGroupId(String docOpenId);

    List<Patient> findPatsByUserOpenidAndConfirmStateNot(String userOpenId,String comState);

    List<Patient> findPatsByUserOpenidAndState(String userOpenId,String state);

    Map<String,Object> uploadPain(Long patId, String painArea, String painChar, String ADR, Integer mostPainGrade,
                                  Integer genPainGrade, String medCase, String stopCause);

    List<PatVisDateMsg> getPatMsgs(String usrOpenid);

    PatVisDateMsg getPatMsg(String msgId);

    boolean affirmPatMsg(String msgId,String state);

    boolean autoUpdatePatVisitDate(String date);

    Patient getPatByPatId(Long patId);

    boolean logoff(Long patId);

    List<PatTodayPlanView> getPatPlan(String usrOpenId);



}
