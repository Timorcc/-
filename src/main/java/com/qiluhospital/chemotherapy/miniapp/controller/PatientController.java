package com.qiluhospital.chemotherapy.miniapp.controller;

import com.qiluhospital.chemotherapy.miniapp.entity.Doctor;
import com.qiluhospital.chemotherapy.miniapp.entity.PatVisDateMsg;
import com.qiluhospital.chemotherapy.miniapp.entity.Patient;
import com.qiluhospital.chemotherapy.miniapp.enums.PatConfirmState;
import com.qiluhospital.chemotherapy.miniapp.enums.PatState;
import com.qiluhospital.chemotherapy.miniapp.servise.impl.DoctorServiceImpl;
import com.qiluhospital.chemotherapy.miniapp.servise.impl.PatientServiceImpl;
import com.qiluhospital.chemotherapy.miniapp.view.GroupAndDoctorView;
import com.qiluhospital.chemotherapy.miniapp.view.PatTodayPlanView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PatientController {

    @Autowired
    private PatientServiceImpl patientService;

    @Autowired
    private DoctorServiceImpl doctorService;

    /*微信登陆并注册用户*/
    @PostMapping(value = "patient/wxlogin")
    public Map wxLogin(String code){
        Map<String,String> msg = new HashMap<>();
        String openId = patientService.initPatUser(code);
        msg.put("openid",openId);
        return msg;
    }

    /*用户绑定或者重新绑定病人*/
    @RequestMapping(value = "patient/bind",method = RequestMethod.POST)
    public Map<String,Object> bindPatient(HttpServletRequest request){
        String patName = request.getParameter("name");
        String ideCard = request.getParameter("ideCard");
        String telNumber = request.getParameter("telNumber");
        String openid = request.getParameter("openid");
        String docId = request.getParameter("docId");
        String patCard = request.getParameter("patCard");
        String date = request.getParameter("date");
        String groupId = request.getParameter("groupId");
        Map<String,Object> res = patientService.bindPatient(openid,patName,ideCard,patCard,telNumber,docId,date,
                Long.valueOf(groupId));
        return res;
    }

    /*病人上传体检报告*/
    @RequestMapping(value = "patient/upload/report", method = RequestMethod.POST)
    public Map uploadFile(@RequestParam(name = "img") MultipartFile files,HttpServletRequest request) {
        String patId = request.getParameter("patId");
        String wbc = request.getParameter("wbc");
        String neu = request.getParameter("neu");
        String hgb = request.getParameter("hgb");
        String plt = request.getParameter("plt");
        String bind = request.getParameter("bind");
        Map<String,Object> msg = patientService.uploadReport(files, Long.valueOf(patId),Double.parseDouble(wbc),
                Double.parseDouble(neu),Double.parseDouble(hgb),Double.parseDouble(plt),bind);
        return msg;
    }

    /*病人上传疼痛感*/
    @RequestMapping(value = "patient/upload/pain", method = RequestMethod.POST)
    public Map uploadPain(HttpServletRequest request) {
        String patId = request.getParameter("patId");
        String painArea = request.getParameter("painPlace");
        String painChar = request.getParameter("painLevel");
        String ADR = request.getParameter("reaction");
        String mostPainGrade = request.getParameter("slider1");
        String genPainGrade = request.getParameter("slider2");
        String medCase = request.getParameter("medicine");
        String stopCause = request.getParameter("medicineStop");
        Map<String,Object> msg = patientService.uploadPain(Long.valueOf(patId),painArea,painChar,ADR,
                Integer.valueOf(mostPainGrade),Integer.valueOf(genPainGrade),medCase,stopCause);
        return msg;
    }

    /*获得全部已经认证的医生(不包括护士)*/
    @RequestMapping(value = "patient/group/doclist",method = RequestMethod.GET)
    public Map getDocList(){
        List<GroupAndDoctorView> groupAndDoctorViewList = doctorService.findAllRealDoc();
        Map<String,Object> msg = new HashMap<>();
        msg.put("groupAndDoctor",groupAndDoctorViewList);
        return msg;
    }

    /*获得用户全部绑定治疗态的病人*/
    @RequestMapping(value = "patient/patlist",method = RequestMethod.GET)
    public Map getPatList(HttpServletRequest request){
        String userOpenid = request.getParameter("openid");
        String patId = request.getParameter("patientId");
        List<Patient> patList = new ArrayList<>();
        if(patId!=null && patId.length()>0){
            Patient patient = patientService.getPatByPatId(Long.valueOf(patId));
            patList.add(patient);
        }else {
            PatState patState = PatState.CURE;
            patList = patientService.findPatsByUserOpenidAndState(userOpenid,patState.getState());
        }
        Map<String,Object> msg = new HashMap<>();
        msg.put("patList",patList);
        return msg;
    }

    /*首页返回获得账户绑定的病人的所有未处理消息和所有病人信息和今日计划*/
    @RequestMapping(value = "patient/patmsg/getall",method = RequestMethod.GET)
    public Map<String,Object> getPatMsgs(HttpServletRequest request){
        Map<String,Object> msg = new HashMap<>();
        String usrOpenId = request.getParameter("openid");
        List<PatVisDateMsg> patVisDateMsgs = patientService.getPatMsgs(usrOpenId);
        PatConfirmState patConfirmState = PatConfirmState.CANCELLED;
        List<Patient> patients = patientService.findPatsByUserOpenidAndConfirmStateNot(usrOpenId,patConfirmState.getState());
        List<PatTodayPlanView> patTodayPlanViews = patientService.getPatPlan(usrOpenId);
        msg.put("patTodayPlanViews", patTodayPlanViews);
        msg.put("visDateMsg", patVisDateMsgs);
        msg.put("patients",patients);
        return msg;
    }

    /*获取一条病人确认时间消息*/
    @RequestMapping(value = "patient/patmsg/getone",method = RequestMethod.GET)
    public Map<String,Object> getPatMsg(HttpServletRequest request){
        Map<String,Object> msg = new HashMap<>();
        String patMsgId = request.getParameter("messageId");
        PatVisDateMsg patVisDateMsg = patientService.getPatMsg(patMsgId);
        msg.put("patVisDateMsg", patVisDateMsg);
        return msg;
    }
    


    /*确认消息*/
    @RequestMapping(value = "patient/affirm/patMsg",method = RequestMethod.POST)
    public Map<String,Object> affirmPatMsg(HttpServletRequest request){
        Map<String,Object> msg = new HashMap<>();
        String patMsgId = request.getParameter("messageId");
        String state = request.getParameter("state");
        boolean isSucc = patientService.affirmPatMsg(patMsgId,state);
        msg.put("msg", isSucc);
        return msg;
    }

    /*根据病人id获取一个病人信息*/
    @RequestMapping(value = "patient/getone/byid",method = RequestMethod.GET)
    public Map<String,Object> getPatById(HttpServletRequest request){
        Map<String,Object> msg = new HashMap<>();
        String patientId = request.getParameter("patientId");
        Patient patient = patientService.getPatByPatId(Long.valueOf(patientId));
        Doctor doctor = doctorService.findDocById(patient.getDoctorId());
        msg.put("patient", patient);
        msg.put("doctor", doctor);
        return msg;
    }

    /*注销病人*/
    @RequestMapping(value = "patient/logoff",method = RequestMethod.POST)
    public Map<String,Object> logoff(HttpServletRequest request){
        Map<String,Object> msg = new HashMap<>();
        String patientId = request.getParameter("patientId");
        boolean isSucc = patientService.logoff(Long.valueOf(patientId));
        msg.put("msg", isSucc);
        return msg;
    }






}
