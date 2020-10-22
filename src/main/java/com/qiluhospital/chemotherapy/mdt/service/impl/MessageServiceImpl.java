package com.qiluhospital.chemotherapy.mdt.service.impl;

import com.qiluhospital.chemotherapy.mdt.entity.Message;
import com.qiluhospital.chemotherapy.mdt.mapper.MessageMapper;
import com.qiluhospital.chemotherapy.mdt.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;

    @Override
    public List<Message> findMessageByChatRoomId(Long chatRoomId) {
        try {
            return messageMapper.findMessageByChatRoomId(chatRoomId);
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public Boolean insertMessage(Long chatRoomId, Long userId, Date date, String content, String username, String type) {
        try {
            return messageMapper.insertMessage(chatRoomId, userId, date, content, username,type);
        } catch (Exception e) {
            log.error(e.toString());
            return false;
        }

    }
}
