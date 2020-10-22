package com.qiluhospital.chemotherapy.mdt.controller;

import com.qiluhospital.chemotherapy.mdt.service.ChatRoomService;
import com.qiluhospital.chemotherapy.mdt.service.UserService;
import com.qiluhospital.chemotherapy.mdtbackground.dto.ChatRoomAndMessage;
import com.qiluhospital.chemotherapy.mdtbackground.service.ChatRoomAndMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    ChatRoomService chatRoomService;
    @Autowired
    ChatRoomAndMessageService chatRoomAndMessageService;

    @GetMapping("mdt/mdt_login")
    public String loginHtml() {
        return "mdt_chat_login";
    }

    @RequestMapping(value = "mdt/mdt_index")
    public String index(HttpServletRequest request, Model model) {
        Object username = request.getSession().getAttribute("username");
        System.out.println("username is====>");
        System.out.println(username);
        if (username == null) {
            return "redirect:mdt_login";
        }
        List<ChatRoomAndMessage> chatRoomView = chatRoomAndMessageService.findAll();
        //List<ChatRoom> chatRoomView = chatRoomService.findAll();
        model.addAttribute("chatRoomViews", chatRoomView);
        return "mdt_chat_index";
    }

    @PostMapping(value = "mdt/mdt_login/post")
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request,
                                      @RequestParam(name = "telNum") String telNum,
                                      @RequestParam(name = "wxNum")  String wxNum) {
        Map<String, Object> map = new HashMap<>();
        map = userService.login(telNum, wxNum, request);
        return map;
    }
}
