package com.qiluhospital.chemotherapy.mdtbackground.controller;

import com.alibaba.fastjson.JSON;
import com.qiluhospital.chemotherapy.mdtbackground.dto.DoctorAndDepart;
import com.qiluhospital.chemotherapy.mdtbackground.entity.Department;
import com.qiluhospital.chemotherapy.mdtbackground.service.DepartmentService;
import com.qiluhospital.chemotherapy.mdtbackground.service.MdtDoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@Slf4j
@RequestMapping("mdt/")
public class MdtDoctorController {
    @Autowired
    MdtDoctorService mdtDoctorService;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("background/doctorList")
    public String doctorList(Model model) {
        List<DoctorAndDepart> doctorAndDepartViews = mdtDoctorService.findAllDoctorAndDepart();
        model.addAttribute("doctorAndDepartViews", doctorAndDepartViews);
        return "mdt_bg_doctor_list";
    }

    //跳转到添加医生窗口
    @GetMapping("background/doctor/list/add")
    public String toDoctorListAdd() {
        return "mdt_bg_doctor_add";
    }

    //跳转到修改医生窗口
    @GetMapping("background/doctor/list/edit")
    public String toDoctorListEdit(HttpServletRequest request, Model model) {
        List<Department> departmentsList = departmentService.findAll();
        String id = request.getParameter("id");
        DoctorAndDepart doctorAndDepart = mdtDoctorService.findDoctorAndDepartById(Long.valueOf(id));
        model.addAttribute("doctorAndDepart", doctorAndDepart);
        model.addAttribute("departmentsList", departmentsList);
        return "mdt_bg_doctor_edit";
    }

    /*实现添加医生信息*/
    @RequestMapping(value = "background/doctor/list/post/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> toDoctorAdd(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String doctorName = request.getParameter("doctorName").trim();
        String doctorTelNum = request.getParameter("doctorTelNum").trim();
        String doctorWxNum = request.getParameter("doctorWxNum").trim();
        boolean state = mdtDoctorService.AddDoctor(doctorName, doctorTelNum, doctorWxNum);
        map.put("state", state);
        return map;
    }

    /*实现修改医生信息*/
    @RequestMapping(value = "background/doctor/list/post/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> toBigAdd(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //医生的id
        String id = request.getParameter("id");
        String doctorName = request.getParameter("doctorName").trim();
        String doctorTelNum = request.getParameter("doctorTelNum").trim();
        String doctorWxNum = request.getParameter("doctorWxNum").trim();
        List<String> docDepartList = JSON.parseArray(request.getParameter("docDepartList").trim(), String.class);
        Boolean result1 = mdtDoctorService.updateDoctorById(Long.valueOf(id), doctorName, doctorTelNum, doctorWxNum);
        Boolean result2 = mdtDoctorService.updateRDDByDoctorId(Long.valueOf(id), docDepartList);
        boolean state = false;
        if ((result1 && result2)){
            state=true;
        }
            map.put("state", state);
        return map;
    }

    //模糊查询
//    @GetMapping(value = "background/fuzzyQueryWithDoc")
//    public String fuzzyQueryWithDoc(HttpServletRequest request, Model model) {
//        String name = request.getParameter("docName");
//        List<BigSecretary> bigSecretaryView = bigSecretaryService.fuzzyFind(name);
//        model.addAttribute("bigSecretaryViews", bigSecretaryView);
//
//        return "big_secretary_list";
//    }

}
