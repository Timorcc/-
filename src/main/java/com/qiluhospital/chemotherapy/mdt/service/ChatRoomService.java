package com.qiluhospital.chemotherapy.mdt.service;

import com.qiluhospital.chemotherapy.mdt.entity.ChatRoom;

import java.util.Date;
import java.util.List;

public interface ChatRoomService {
    List<ChatRoom> findAll();
    Boolean addChatRoom(String name, Date createTime, Long createAdminId, Long departId, boolean state);
    Boolean updateChatRoomConclusion(Long id, Date endDate, String conclusion);
}
