package com.qiluhospital.chemotherapy.mdtbackground.controller;


import com.qiluhospital.chemotherapy.mdtbackground.entity.SmallSecretary;
import com.qiluhospital.chemotherapy.mdtbackground.service.SmallSecretaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("mdt/")
public class SmallSecretaryController {
    @Autowired
    private SmallSecretaryService smallSecretaryService;

    //查询所有小秘书列表
    @GetMapping("background/smallsecretarylist")
    public String smallSecretaryList(HttpServletRequest request, Model model) {
        List<SmallSecretary> smallSecretaryView = smallSecretaryService.getSmallSecretaryList();
        model.addAttribute("smallSecretaryViews", smallSecretaryView);
        return "mdt_bg_small_secretary_list";
    }

    //模糊查询
    @GetMapping(value = "background/fuzzyQueryWithSmall")
    public String fuzzyQueryWithSmall(HttpServletRequest request, Model model) {
        String name = request.getParameter("smallSecretaryName");
        List<SmallSecretary> smallSecretaryView = smallSecretaryService.fuzzyFind(name);
        model.addAttribute("smallSecretaryViews", smallSecretaryView);
        return "mdt_bg_small_secretary_list";
    }


    /*跳转到添加或者修改小秘书的表单*/
    @GetMapping(value = "background/smallSecretary/list/{type}")
    public String toSmallListHtml(HttpServletRequest request, Model model, @PathVariable("type") String type) {
        if (type.equals("edit")) {
            String id = request.getParameter("id");
            SmallSecretary smallSecretary = smallSecretaryService.findById(Long.valueOf(id));
            model.addAttribute("smallSecretary", smallSecretary);
        }
        model.addAttribute("type", type);
        return "mdt_bg_small_secretary_add_edit";
    }

    /*实现添加或者修改小秘书信息*/
    @RequestMapping(value = "background/smallSecretary/list/post/{type}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> toSmallAdd(HttpServletRequest request, @PathVariable("type") String type) {
        Map<String, Object> map = new HashMap<>();

        String smallSecretaryName = request.getParameter("smallSecretaryName").trim();
        String smallSecretaryTelNum = request.getParameter("smallSecretaryTelNum").trim();
        String smallSecretaryWxNum = request.getParameter("smallSecretaryWxNum").trim();
        boolean state = false;
        if (type.equals("edit")) {
            String id = request.getParameter("id");
            state = smallSecretaryService.updateById(Long.valueOf(id), smallSecretaryName, smallSecretaryTelNum, smallSecretaryWxNum);
        }
        if (type.equals("add")) {
            state = smallSecretaryService.AddSmallSecretary(smallSecretaryName, smallSecretaryTelNum, smallSecretaryWxNum);
        }
        map.put("type", type);
        map.put("state", state);
        return map;
    }


}
