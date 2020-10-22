package com.qiluhospital.chemotherapy.mdtbackground.controller;

import com.qiluhospital.chemotherapy.mdtbackground.entity.Department;
import com.qiluhospital.chemotherapy.mdtbackground.service.DepartmentService;
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
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    //跳转到科室列表
    @GetMapping("background/departmentList")
    public String departmentList(Model model) {
        List<Department> departmentList = departmentService.findAll();
        model.addAttribute("departmentViews", departmentList);
        return "mdt_bg_department_list";
    }

    //跳转到添加||修改科室窗口
    @GetMapping("background/department/list/{type}")
    public String toDepartmentList(HttpServletRequest request, Model model, @PathVariable("type") String type) {
        if (type.equals("edit")) {
            String id = request.getParameter("id");
            Department department = departmentService.findById(Long.valueOf(id));
            model.addAttribute("department", department);
        }
        model.addAttribute("type", type);
        return "mdt_bg_department_add_edit";
    }

    //携带添加或者修改科室的信息，完成逻辑
    @PostMapping("background/department/list/post/{type}")
    @ResponseBody
    public Map<String, Object> toDepartmentAdd(HttpServletRequest request, @PathVariable("type") String type) {
        Map<String, Object> map = new HashMap<>();
        String departmentName = request.getParameter("departmentName").trim();
        boolean state = false;
        if (type.equals("edit")) {
            String id = request.getParameter("id");
            state = departmentService.updateById(Long.valueOf(id), departmentName);
        }
        if (type.equals("add")) {
            state = departmentService.AddDepartment(departmentName);
        }
        map.put("type", type);
        map.put("state", state);
        return map;
    }

}
