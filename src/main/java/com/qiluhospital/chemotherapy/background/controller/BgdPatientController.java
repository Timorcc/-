package com.qiluhospital.chemotherapy.background.controller;

import com.qiluhospital.chemotherapy.background.servise.impl.BgdPatientServiseImpl;
import com.qiluhospital.chemotherapy.background.view.PatView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BgdPatientController {

    @Autowired
    private BgdPatientServiseImpl bgdPatientServise;

    private static int PAGESIZE = 6;

    /*获取所有的病人或者根据病人名字模糊查询*/
    @RequestMapping(value = "background/patlist/{state}",method = RequestMethod.GET)
    public String pacList(HttpServletRequest request, Model model,@PathVariable("state") String state){
        String page = request.getParameter("page");
        String patName = request.getParameter("patName");
        int pg = 1;
        long count;
        if (patName == null) {//无条件显示第一页
            count = bgdPatientServise.countAllPat(state);
        } else {
            count = bgdPatientServise.countPatByPatName(patName,state);
        }
        if (page!=null){
            pg = Integer.parseInt(page);
        }
        List<PatView> patViews = bgdPatientServise.getAllPat(pg-1,PAGESIZE,patName,state);
        model.addAttribute("patName",patName);
        model.addAttribute("patViews", patViews);
        model.addAttribute("currentPage", pg);
        model.addAttribute("count", count);
        model.addAttribute("pagesize", PAGESIZE);
        return "patient_list_" + state;
    }

    /*设置病人状态*/
    @RequestMapping(value = "background/patlist/pat/{state}" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> modRecovery(HttpServletRequest request, @PathVariable("state") String state){
        Map<String,Object> map = new HashMap<>();
        String patId = request.getParameter("id");
        boolean res = bgdPatientServise.setPatState(Long.valueOf(patId),state);
        map.put("state",res);
        return map;
    }





}
