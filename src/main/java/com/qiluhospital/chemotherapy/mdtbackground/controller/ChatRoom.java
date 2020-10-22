package com.qiluhospital.chemotherapy.mdtbackground.controller;


import com.qiluhospital.chemotherapy.mdt.entity.Message;
import com.qiluhospital.chemotherapy.mdt.service.MessageService;
import com.qiluhospital.chemotherapy.mdtbackground.dto.ChatRoomAndMessage;
import com.qiluhospital.chemotherapy.mdtbackground.service.ChatRoomAndMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("mdt/")
public class ChatRoom {
    @Autowired
    ChatRoomAndMessageService chatRoomAndMessageService;

    @Autowired
    MessageService messageService;

    //查询所有聊天室
    @GetMapping("background/chatRoom")
    public String chatRoomList(HttpServletRequest request, Model model) {
        List<ChatRoomAndMessage> chatRoomViews = chatRoomAndMessageService.findAll();
        //List<com.example.demo.mdt.entity.ChatRoom> chatRoomViews = chatRoomService.findAll();
        model.addAttribute("chatRoomViews", chatRoomViews);
        return "mdt_bg_chat_room_list";
    }
    @GetMapping("background/chatRoom/list/chatMessage")
    public String toChatRoomAndMessage(HttpServletRequest request, Model model) {
        String id = request.getParameter("chatRoomId");
        List<Message> messageList = messageService.findMessageByChatRoomId(Long.valueOf(id));
        model.addAttribute("messageList",messageList);
        return "mdt_bg_message_list";
    }
}
