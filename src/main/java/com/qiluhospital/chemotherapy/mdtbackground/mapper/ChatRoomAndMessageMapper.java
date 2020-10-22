package com.qiluhospital.chemotherapy.mdtbackground.mapper;

import com.qiluhospital.chemotherapy.mdtbackground.dto.ChatRoomAndMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChatRoomAndMessageMapper {
    @Select("SELECT c.id,c.name,c.create_date,c.create_admin_id,s.username,c.state,c.end_date,c.depart_id,d.depart_name,c.conclusion FROM mdt_chat_room c LEFT JOIN mdt_small_secretary s on c.create_admin_id = s.id LEFT JOIN mdt_department d on d.id= c.depart_id")
    List<ChatRoomAndMessage> findAll();
}
