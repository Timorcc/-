package com.qiluhospital.chemotherapy.background.controller;

import com.qiluhospital.chemotherapy.background.entity.BgdAdmin;
import com.qiluhospital.chemotherapy.background.servise.impl.BgdAdminServiseImpl;
import com.qiluhospital.chemotherapy.miniapp.servise.impl.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BgdAdminController {

    @Autowired
    private BgdAdminServiseImpl bgdAdminServise;

    @Autowired
    private DoctorServiceImpl doctorService;

//    @Autowired
//    private DefaultKaptcha defaultKaptcha;


//    @RequestMapping("/vryCodeGet")
//    public void defaultKpatcha(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        System.out.println("vryCodeGet");
//        byte[] captchaChallengeAsJpeg = null;
//        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
//        try {
//            //生产验证码字符串并保存到session中
//            String createText = defaultKaptcha.createText();
//            System.out.println(createText);
//            request.getSession().setAttribute("vrifyCode", createText);
//            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
//            BufferedImage challenge = defaultKaptcha.createImage(createText);
//            ImageIO.write(challenge, "jpg", jpegOutputStream);
//        }catch (IllegalArgumentException e){
//            response.sendError(HttpServletResponse.SC_NOT_FOUND);
//            return;
//        }
//        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
//        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
//        response.setHeader("Cache-Control", "no-store");
//        response.setHeader("Pragma", "no-cache");
//        response.setDateHeader("Expires", 0);
//        response.setContentType("image/jpeg");
//        ServletOutputStream responseOutputStream = response.getOutputStream();
//        responseOutputStream.write(captchaChallengeAsJpeg);
//        responseOutputStream.flush();
//        responseOutputStream.close();
//    }

    @RequestMapping(value = "background/login")
    public String loginHtml() {
        return "login";
    }

    @RequestMapping(value = "background/logout")
    public String loginout(HttpServletRequest request) {
        //false代表：不创建session对象，只是从request中获取。
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("username");
        }
        return "redirect:login";
    }

    @RequestMapping(value = "background/index")
    public String index(HttpServletRequest request) {
        Object username = request.getSession().getAttribute("username");
        if (username == null) {
            return "redirect:login";
        }
        return "index";
    }

    @RequestMapping(value = "background/login/post", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request,
                                     @RequestParam(name = "username") String username,
                                     @RequestParam(name = "password") String password) {
//        String captchaId = (String) request.getSession().getAttribute("vrifyCode");
        Map<String, Object> map = new HashMap<>();
//        if(!code.equalsIgnoreCase(captchaId)){
//            map.put("status",2);
//            map.put("msg","验证码错误");
//            return map;
//        }
        map = bgdAdminServise.login(username, password,request);
        return map;
    }

    @RequestMapping(value = "background/main")
    public String mainHtml(HttpServletRequest request, Model model) {
//        Subject subject = SecurityUtils.getSubject();
//        String roleName = (String) subject.getSession().getAttribute("empRole");
//        Long count = activityService.findTaskCountByRoleId(roleName);
//        String principal = (String) SecurityUtils.getSubject().getPrincipal();
//        Employee emp = employeeService.findEmployeeByNum(principal);
//        long newMessageCount = copyMessageService.getNewMessageAmountByReciverId(emp.getEmployeeId());
//        model.addAttribute("newMessageCount",newMessageCount);
//        model.addAttribute("count",count);
        return "main";
    }


    /*修改密码界面显示*/
    @RequestMapping(value = "background/admin/passwd/show",method = RequestMethod.GET)
    public String adminPasswdHtml(){
        return "password";
    }

    /*修改密码*/
    @RequestMapping(value = "background/admin/passwd/update",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> adminPasswdUpdate(HttpServletRequest request){
        Long id = (Long) request.getSession().getAttribute("id");
        String oldPasswd = request.getParameter("oldPass");
        String newPass = request.getParameter("newPass");
        return bgdAdminServise.updatePasswd(id,oldPasswd,newPass);
    }

    /*管理员界面显示*/
    @RequestMapping(value = "background/admin/list/show",method = RequestMethod.GET)
    public String adminListHtml(Model model){
        List<BgdAdmin> bgdAdmins = bgdAdminServise.getNotRootAdmins();
        model.addAttribute("bgdAdmins", bgdAdmins);
        return "admin_list";
    }

    /*管理员添加界面显示*/
    @RequestMapping(value = "background/admin/list/addAdmin/html",method = RequestMethod.GET)
    public String addAdminHtml(){
        return "admin_add";
    }

    /*添加管理员*/
    @RequestMapping(value = "background/admin/list/addAdmin/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addAdmin(HttpServletRequest request){
        Map<String,Object> res = new HashMap<>();
        String adminName = request.getParameter("adminName");
        String description = request.getParameter("description");
        boolean isSucc = bgdAdminServise.addAdmin(adminName,description);
        res.put("state",isSucc);
        return res;
    }

    /*删除管理员*/
    @RequestMapping(value = "background/admin/list/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteAdmin(HttpServletRequest request){
        Map<String,Object> res = new HashMap<>();
        String id = request.getParameter("id");
        boolean isSucc = bgdAdminServise.deleteAdmin(Long.valueOf(id));
        res.put("state",isSucc);
        return res;
    }

    /*管理员密码重置*/
    @RequestMapping(value = "background/admin/list/reset",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> resetAdmin(HttpServletRequest request){
        Map<String,Object> res = new HashMap<>();
        String id = request.getParameter("id");
        boolean isSucc = bgdAdminServise.resetAdmin(id);
        res.put("state",isSucc);
        return res;
    }







}
