package com.qiluhospital.chemotherapy.mdt.service.impl;

import com.qiluhospital.chemotherapy.mdt.entity.ChatRoom;
import com.qiluhospital.chemotherapy.mdt.mapper.ChatRoomMapper;
import com.qiluhospital.chemotherapy.mdt.service.ChatRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ChatRoomServiceImpl implements ChatRoomService {

    @Autowired
    ChatRoomMapper chatRoomMapper;

    @Override
    public List<ChatRoom> findAll() {
        try {
            return chatRoomMapper.findAll();
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public Boolean addChatRoom(String name, Date createTime, Long createAdminId, Long departId, boolean state) {
        try {
            return chatRoomMapper.addChatRoom(name, createTime, createAdminId, departId, state);
        } catch (Exception e) {
            log.error(e.toString());
            return false;
        }
    }


    @Override
    public Boolean updateChatRoomConclusion(Long id, Date endDate, String conclusion) {
        try {
            return chatRoomMapper.updateChatRoomConclusion(id, endDate, conclusion);
        } catch (Exception e) {
            log.error(e.toString());
            return false;
        }
    }
}
