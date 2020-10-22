package com.qiluhospital.chemotherapy.mdtbackground.service;

import com.qiluhospital.chemotherapy.mdtbackground.dto.ChatRoomAndMessage;

import java.util.List;

public interface ChatRoomAndMessageService {
    List<ChatRoomAndMessage> findAll();
}
