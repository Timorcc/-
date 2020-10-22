package com.qiluhospital.chemotherapy.mdt.service;

import com.qiluhospital.chemotherapy.mdt.entity.Message;

import java.util.Date;
import java.util.List;


public interface MessageService {
    List<Message> findMessageByChatRoomId(Long chatRoomId);
    Boolean insertMessage(Long chatRoomId, Long userId, Date date, String content, String username, String type);
}
