package com.qiluhospital.chemotherapy.miniapp.controller;

import com.qiluhospital.chemotherapy.miniapp.entity.DocGroup;
import com.qiluhospital.chemotherapy.miniapp.entity.Doctor;
import com.qiluhospital.chemotherapy.miniapp.entity.PatVisDateMsg;
import com.qiluhospital.chemotherapy.miniapp.entity.Patient;
import com.qiluhospital.chemotherapy.miniapp.servise.impl.DoctorServiceImpl;
import com.qiluhospital.chemotherapy.miniapp.servise.impl.PatientServiceImpl;
import com.qiluhospital.chemotherapy.miniapp.view.PatPainView;
import com.qiluhospital.chemotherapy.miniapp.view.PatReportView;
import com.qiluhospital.chemotherapy.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DoctorController {

    @Autowired
    private PatientServiceImpl patientService;

    @Autowired
    private DoctorServiceImpl doctorService;

    private static int PAGESIZE = 6;


    /*微信登陆并注册医生*/
    @PostMapping(value = "doctor/wxlogin")
    public ResponseResult wxLogin(String code){
        ResponseResult result = doctorService.initDoctor(code);
        return result;
    }

    /*医生认证申请*/
    @RequestMapping(value = "doctor/auth",method = RequestMethod.POST)
    public Map docAuth(HttpServletRequest request){
        String docOpenid = request.getParameter("openid");
        String docName = request.getParameter("name");
        String telNum = request.getParameter("telNumber");
        String ideNum = request.getParameter("ideCard");
        String profNum = request.getParameter("profNum");
        String groupId = request.getParameter("docGroup");
        boolean isSucc = doctorService.authDoctor(docOpenid,docName,telNum,ideNum,profNum,Long.valueOf(groupId));
        Map<String,Object> map = new HashMap<>();
        map.put("msg",isSucc);
        return map;
    }

    /*获得全部分组*/
    @RequestMapping(value = "doctor/grouplist",method = RequestMethod.GET)
    public Map getGroupList(){
        List<DocGroup> groupList = doctorService.getGroups();
        Map<String,Object> map = new HashMap<>();
        map.put("groupList",groupList);
        return map;
    }

    /*获得同组全部未确定病人*/
    @RequestMapping(value = "doctor/patlist/unaffirm",method = RequestMethod.GET)
    public Map getPatList(HttpServletRequest request){
        String docOpenid = request.getParameter("openid");
        List<Patient> patList = patientService.findPatsByGroupId(docOpenid);
        Map<String,Object> map = new HashMap<>();
        map.put("patList",patList);
        return map;
    }

    /*获得同组全部已确定病人*/
    @RequestMapping(value = "doctor/patlist/hasAffirm",method = RequestMethod.GET)
    public Map getAffPatList(HttpServletRequest request){
        String docOpenid = request.getParameter("openid");
        List<Patient> patList = doctorService.findPatsByConfirmState(docOpenid);
        Map<String,Object> map = new HashMap<>();
        map.put("patList",patList);
        return map;
    }

    /*医生确定自己的病人*/
    @RequestMapping(value = "doctor/verify",method = RequestMethod.POST)
    public Map docVerify(HttpServletRequest request){
        String docOpenid = request.getParameter("openid");
        String patId = request.getParameter("patId");
        String  confirmState = request.getParameter("confirmState");
        boolean isSucc = doctorService.verifyPatient(docOpenid,Long.valueOf(patId),confirmState);
        Map<String,Object> map = new HashMap<>();
        map.put("msg",isSucc);
        return map;
    }

    /*护士获取所有具有疼痛感的病人*/
    @RequestMapping(value = "doctor/getPats/pain",method = RequestMethod.GET)
    public Map getUpObjList(HttpServletRequest request){
        String docOpenid = request.getParameter("openid");
        String pageIndex = request.getParameter("pageIndex");
        Map<String,Object> map = new HashMap<>();
        Page<Patient> patients = doctorService.findPatsByPainDateIsNull(docOpenid,Integer.parseInt(pageIndex),PAGESIZE);
        map.put("patients",patients);
        return map;
    }

    /*护士获取病人具体的疼痛感信息*/
    @RequestMapping(value = "doctor/patpain/get",method = RequestMethod.GET)
    public Map getPatPain(HttpServletRequest request){
        String patId = request.getParameter("patId");
        List<PatPainView> patientPains = doctorService.findPatPainsByPat(Long.valueOf(patId));
        Map<String,Object> map = new HashMap<>();
        map.put("patientPains",patientPains);
        return map;
    }

    /*医生获取同组中报告单有问题的所有病人*/
    @RequestMapping(value = "doctor/getPat/abnormalRet",method = RequestMethod.GET)
    public Map getAbnorPats(HttpServletRequest request){
        String openId = request.getParameter("openid");
        String pageIndex = request.getParameter("pageIndex");
        Page<Patient> patients = doctorService.findPatsByIsNormal(openId,Integer.parseInt(pageIndex),PAGESIZE);
        Map<String,Object> map = new HashMap<>();
        map.put("patients",patients);
        return map;
    }

    /*医生获取病人具体的报告单信息*/
    @RequestMapping(value = "doctor/reports/get",method = RequestMethod.GET)
    public Map getPatReports(HttpServletRequest request){
        String patId = request.getParameter("patId");
        List<PatReportView> patReportViews = doctorService.findPatReportByPatId(Long.valueOf(patId));
        Map<String,Object> map = new HashMap<>();
        map.put("patReportViews", patReportViews);
        return map;
    }

    /*判断医生是否注册过*/
    @RequestMapping(value = "doctor/register/judge",method = RequestMethod.GET)
    public Map judgeRegister(HttpServletRequest request){
        String openid = request.getParameter("openid");
        boolean hasRegister = doctorService.hasRegister(openid);
        Map<String,Object> map = new HashMap<>();
        map.put("msg",hasRegister);
        return map;
    }

    /*根据openid查找一个医生的信息*/
    @RequestMapping(value = "doctor/find/one",method = RequestMethod.GET)
    public Map findDoctor(HttpServletRequest request){
        String openid = request.getParameter("openid");
        Map<String,Object> map = doctorService.findOneByOpenid(openid);
        return map;
    }

    /*按病人最近就诊时间或者预计返回医生所属的组的所有的病人*/
    @RequestMapping(value = "doctor/patlist/lastedday",method = RequestMethod.GET)
    public Map findPatByDocId(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String openid = request.getParameter("openid");
        String page = request.getParameter("pageIndex");
        String patName = request.getParameter("patName");
        String currentTab = request.getParameter("currentTab");
        if (patName!=null){
            List<Patient> patients = doctorService.findPatsByPatName(openid,patName);
            map.put("patients",patients);
        }else {
            Page<Patient> patients = doctorService.findPatsByGroupOrderByLastedDay(openid,Integer.parseInt(page),PAGESIZE,currentTab);
            map.put("patients",patients);
        }
        return map;
    }

    /*医生或护士获取某一个病人的信息*/
    @RequestMapping(value = "doctor/getone/patient",method = RequestMethod.GET)
    public Map findPatByDoc(HttpServletRequest request){
        String docOpenid = request.getParameter("openid");
        String patId = request.getParameter("patientId");
        return doctorService.findPatByDocOpenid(docOpenid,patId);
    }

    /*判断医生或者护士*/
    @RequestMapping(value = "doctor/judge/type",method = RequestMethod.POST)
    public Map judgeDocType(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String openid = request.getParameter("openid");
        Doctor doctor = doctorService.findDocByOpenId(openid);
        map.put("doctor",doctor);
        return map;
    }


    /*医生或者护士修改病人预计就诊时间和一系列时间*/
    @RequestMapping(value = "doctor/modify/patDate",method = RequestMethod.POST)
    public Map modifyDate(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String openid = request.getParameter("openid");
        String patId = request.getParameter("patientId");
        String date = request.getParameter("date");
        String type = request.getParameter("type");
        boolean isSucc = doctorService.modifyPatDate(openid,patId,date,type);
        map.put("msg",isSucc);
        return map;
    }

    /*医生修改病人确切就诊时间*/
    @RequestMapping(value = "doctor/modify/patCertainDate",method = RequestMethod.POST)
    public Map modifyCertainDate(HttpServletRequest request) {
        String openid = request.getParameter("openid");
        String patId = request.getParameter("patientId");
        String certainVisitDate = request.getParameter("visitDate");
        Map<String,Object> msg = doctorService.modifyPatCertainDate(openid,patId,certainVisitDate);
        return msg;
    }

    /*根据选择的确切看病时间显示病人*/
    @RequestMapping(value = "doctor/patients/byCertainDate",method = RequestMethod.GET)
    public Map getPatsByCertainDate(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String openid = request.getParameter("openid");
        String certainVisitDate = request.getParameter("visitDate");
        List<PatVisDateMsg> patVisDateMsgList = doctorService.getPatsByCertainVistDate(openid,certainVisitDate);
        map.put("patVisDateMsgList",patVisDateMsgList);
        return map;
    }

    /*医生冻结病人*/
    @RequestMapping(value = "doctor/freeze/patient",method = RequestMethod.POST)
    public Map freezePat(HttpServletRequest request) {
        String patId = request.getParameter("patientId");
        boolean res = doctorService.freezePat(Long.valueOf(patId));
        Map<String,Object> map = new HashMap<>();
        map.put("msg",res);
        return map;
    }








}
