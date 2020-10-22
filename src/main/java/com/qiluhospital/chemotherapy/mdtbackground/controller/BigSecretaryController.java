package com.qiluhospital.chemotherapy.mdtbackground.controller;

import com.qiluhospital.chemotherapy.mdtbackground.entity.BigSecretary;
import com.qiluhospital.chemotherapy.mdtbackground.service.BigSecretaryService;
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
public class BigSecretaryController {
    @Autowired
    private BigSecretaryService bigSecretaryService;

    //查询所有大秘书列表
    @GetMapping("background/bigsecretarylist")
    public String bigSecretaryList(HttpServletRequest request, Model model) {
        List<BigSecretary> bigSecretaryView = bigSecretaryService.getBigSecretaryList();
        model.addAttribute("bigSecretaryViews", bigSecretaryView);
        return "mdt_bg_big_secretary_list";
    }

    //模糊查询
    @GetMapping(value = "background/fuzzyQueryWithBig")
    public String fuzzyQueryWithBig(HttpServletRequest request, Model model) {
        String name = request.getParameter("bigSecretaryName");
        List<BigSecretary> bigSecretaryView = bigSecretaryService.fuzzyFind(name);
        model.addAttribute("bigSecretaryViews", bigSecretaryView);
        return "mdt_bg_big_secretary_list";
    }


    /*跳转到添加或者修改大秘书的表单*/
    @GetMapping(value = "background/bigSecretary/list/{type}")
    public String toBigListHtml(HttpServletRequest request, Model model, @PathVariable("type") String type) {
        if (type.equals("edit")) {
            String id = request.getParameter("id");
            BigSecretary bigSecretary = bigSecretaryService.findById(Long.valueOf(id));
            model.addAttribute("bigSecretary", bigSecretary);
        }
        model.addAttribute("type", type);
        return "mdt_bg_bigSecretary_add_edit";
    }

    /*实现添加或者修改大秘书信息*/
    @RequestMapping(value = "background/bigSecretary/list/post/{type}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> toBigAdd(HttpServletRequest request, @PathVariable("type") String type) {
        Map<String, Object> map = new HashMap<>();
        String bigSecretaryName = request.getParameter("bigSecretaryName").trim();
        String bigSecretaryTelNum = request.getParameter("bigSecretaryTelNum").trim();
        String bigSecretaryWxNum = request.getParameter("bigSecretaryWxNum").trim();
        boolean state = false;
        if (type.equals("edit")) {
            String id = request.getParameter("id");
            state = bigSecretaryService.updateById(Long.valueOf(id), bigSecretaryName, bigSecretaryTelNum, bigSecretaryWxNum);
        }
        if (type.equals("add")) {
            state = bigSecretaryService.AddBigSecretary(bigSecretaryName, bigSecretaryTelNum, bigSecretaryWxNum);
        }
        map.put("type", type);
        map.put("state", state);
        return map;
    }
}
