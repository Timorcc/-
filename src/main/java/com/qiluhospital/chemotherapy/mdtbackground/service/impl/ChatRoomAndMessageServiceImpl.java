package com.qiluhospital.chemotherapy.mdtbackground.service.impl;

import com.qiluhospital.chemotherapy.mdtbackground.dto.ChatRoomAndMessage;
import com.qiluhospital.chemotherapy.mdtbackground.mapper.ChatRoomAndMessageMapper;
import com.qiluhospital.chemotherapy.mdtbackground.service.ChatRoomAndMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChatRoomAndMessageServiceImpl implements ChatRoomAndMessageService {

    @Autowired
    ChatRoomAndMessageMapper chatRoomAndMessageMapper;

    @Override
    public List<ChatRoomAndMessage> findAll() {
        try {
            return chatRoomAndMessageMapper.findAll();
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }

    }
}
