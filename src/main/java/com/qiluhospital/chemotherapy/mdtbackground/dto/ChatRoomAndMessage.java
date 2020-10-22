package com.qiluhospital.chemotherapy.mdtbackground.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ChatRoomAndMessage {
    private Long id;//聊天室主键
    private String name;//聊天室名称
    private Date create_date;//聊天室创建时间
    private Long create_admin_id;//创建聊天室的小秘书的id
    private String username;//创建聊天室的小秘书姓名
    private Date end_date; //聊天室终止时间
    private Boolean state;//聊天室室的状态 true：可以聊天.false:聊天已结束，不能再聊天
    private Long depart_id;//聊天的所属科室
    private String depart_name;//聊天室所属科室的名称
    private String conclusion;//聊天室诊断结论

}
