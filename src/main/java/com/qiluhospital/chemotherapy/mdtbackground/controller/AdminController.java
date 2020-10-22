package com.qiluhospital.chemotherapy.mdtbackground.controller;

import com.qiluhospital.chemotherapy.mdtbackground.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AdminController {
    @Autowired
    AdminService adminService;

    //请求登录主页面
    @RequestMapping(value = "mdt/background/login")
    public String loginHtml() {
        return "mdt_bg_login";
    }

    @RequestMapping(value = "mdt/background/index")
    public String index(HttpServletRequest request) {
        Object username = request.getSession().getAttribute("username");
        if (username == null) {
            return "redirect:login";
        }
        return "mdt_bg_index";
    }

    @RequestMapping(value = "mdt/background/main")
    public String mainHtml(HttpServletRequest request, Model model) {
        return "mdt_bg_main";
    }

    //输入账号秘密请求登录,登录校验
    @PostMapping(value = "mdt/background/login/post")
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request,
                                     @RequestParam(name = "username") String username,
                                     @RequestParam(name = "password") String password) {
        Map<String, Object> map = new HashMap<>();
        map = adminService.login(username, password, request);
        return map;
    }

    //注销
    @RequestMapping(value = "mdt/background/logout")
    public String loginout(HttpServletRequest request) {
        //false代表：不创建session对象，只是从request中获取。
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("username");
        }
        return "redirect:login";
    }

}
