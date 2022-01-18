package com.qiluhospital.chemotherapy.mdt.server;

import com.qiluhospital.chemotherapy.mdt.config.MyApplicationContextAware;
import com.qiluhospital.chemotherapy.mdt.entity.Message;
import com.qiluhospital.chemotherapy.mdt.service.MessageService;
import com.qiluhospital.chemotherapy.mdtbackground.entity.Doctor;
import com.qiluhospital.chemotherapy.mdtbackground.entity.SmallSecretary;
import com.qiluhospital.chemotherapy.mdtbackground.service.MdtDoctorService;
import com.qiluhospital.chemotherapy.mdtbackground.service.SmallSecretaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@ServerEndpoint("/webSocket/chat/{roomName}/{userId}")
@Controller
public class WebSocketServer {

    private static final Map<String, Set<Session>> rooms = new ConcurrentHashMap();
    private static final Map<String, String> userNameList = new ConcurrentHashMap();
    private MessageService messageServiceImpl = (MessageService) MyApplicationContextAware.getApplicationContext().getBean("messageServiceImpl");
    private MdtDoctorService mdtDoctorServiceImpl = (MdtDoctorService) MyApplicationContextAware.getApplicationContext().getBean("mdtDoctorServiceImpl");
    private SmallSecretaryService smallSecretaryServiceImpl = (SmallSecretaryService) MyApplicationContextAware.getApplicationContext().getBean("smallSecretaryServiceImpl");

    //open时先查寻所有的message
    @OnOpen
    public void connect(@PathParam("roomName") String roomName, @PathParam("userId") String userId, Session session) throws Exception {
        System.out.println("连接成功");
        // 将session按照房间名来存储，将各个房间的用户隔离
        if (!rooms.containsKey(roomName)) {
            // 创建房间不存在时，创建房间
            Set<Session> room = new HashSet<Session>();
            // 添加用户
            room.add(session);
            rooms.put(roomName, room);
        } else {
            // 房间已存在，直接添加用户到相应的房间
            rooms.get(roomName).add(session);
        }
        System.err.println("userId:" + userId);
        System.out.println("a client has connected!");
        System.out.println("-----------------------");
        List<Message> messageList = messageServiceImpl.findMessageByChatRoomId(Long.valueOf(roomName));
        for (Message message : messageList
        ) {
            //String history = message.getType()+message.getSender() + "-" + message.getUsername() + ":" + message.getContent();
            //broadcast(roomName, history);r,
            broadcast(roomName, message);
        }
    }

    @OnClose
    public void disConnect(@PathParam("roomName") String roomName, @PathParam("userId") String userId, Session session) {
        rooms.get(roomName).remove(session);
        System.out.println("a client has disconnected!");
    }

    @OnMessage
    public void receiveMsg(@PathParam("roomName") String roomName, @PathParam("userId") String userId,
                           String msg, Session session) throws Exception {
        Date data = new Date();
        String username;
        //判断是秘书还是医生
        if (Long.valueOf(userId) < 9000) {
            SmallSecretary s = smallSecretaryServiceImpl.findById(Long.valueOf(userId));
            username = s.getUsername();
        } else {
            Doctor d = mdtDoctorServiceImpl.findById(Long.valueOf(userId));
            username = d.getUsername();
        }
        System.out.println("username is" + username);
        //往数据库里存消息，文字消息type为0
        boolean re = messageServiceImpl.insertMessage(Long.valueOf(roomName), Long.valueOf(userId), data, msg, username, "0");
        //msg = userId + "-" + username + ":" + msg;
        //接收到信息后进行广播
        Message message = new Message();
        message.setId(0L);
        message.setChatroom_id(Long.valueOf(roomName));
        message.setSender(Long.valueOf(userId));
        message.setSend_date(data);
        message.setContent(msg);
        message.setUsername(username);
        message.setType("0");
        broadcast(roomName, message);
    }


    // 按照房间名进行广播
    public static void broadcast(String roomName, Message message) throws Exception {
        String msg = message.getType()+"-"+ message.getSender() + "-" + message.getUsername() + ":" + message.getContent();
        for (Session session : rooms.get(roomName)) {
            session.getBasicRemote().sendText(msg);
        }
    }
}