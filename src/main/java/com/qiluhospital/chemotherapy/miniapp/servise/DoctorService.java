package com.qiluhospital.chemotherapy.miniapp.servise;

import com.qiluhospital.chemotherapy.miniapp.entity.DocGroup;
import com.qiluhospital.chemotherapy.miniapp.entity.Doctor;
import com.qiluhospital.chemotherapy.miniapp.entity.PatVisDateMsg;
import com.qiluhospital.chemotherapy.miniapp.entity.Patient;
import com.qiluhospital.chemotherapy.miniapp.view.GroupAndDoctorView;
import com.qiluhospital.chemotherapy.miniapp.view.PatPainView;
import com.qiluhospital.chemotherapy.miniapp.view.PatReportView;
import com.qiluhospital.chemotherapy.util.ResponseResult;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface DoctorService {

    List<GroupAndDoctorView> findAllRealDoc();

    ResponseResult initDoctor(String code);

    boolean authDoctor(String openId,String docName,String telNum,String ideNum,String profNum,Long groupId);

    boolean verifyPatient(String docOpenId,Long patId,String confirmState);

    List<PatReportView> findPatReportByPatId(Long patId);

    List<Patient> findPatsByConfirmState(String docOpenId);

    List<DocGroup> getGroups();

    boolean hasRegister(String openid);

    Map<String,Object> findOneByOpenid(String openid);

    Page<Patient> findPatsByGroupOrderByLastedDay(String docOpenid, int page, int size,String currentTab);

    List<Patient> findPatsByPatName(String docOpenid,String patName);

    Map<String,Object> findPatByDocOpenid(String docOpenid,String patId);

    boolean modifyPatDate(String openid,String patId,String date,String type);

    Map<String,Object> modifyPatCertainDate(String openid,String patId,String certainVisitDate);

    List<PatVisDateMsg> getPatsByCertainVistDate(String docOpenid,String certainVisitDate);

    Page<Patient> findPatsByIsNormal(String docOpenid,int page,int size);

    Page<Patient> findPatsByPainDateIsNull(String docOpenId,int page,int size);

    List<PatPainView> findPatPainsByPat(Long patId);

    Doctor findDocByOpenId(String openid);

    Doctor findDocById(Long docId);

    boolean freezePat(Long patId);

    boolean setReadStatus(String bind);
}
