package cn.enjoyedu.web;

import cn.enjoyedu.domain.ChatRoomRequest;
import cn.enjoyedu.domain.ChatRoomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
@Controller
public class StompController {

    @Autowired
    private SimpMessagingTemplate template;/*Spring实现的一个发送模板类*/

    /*消息群发，接受发送至自massRequest的请求*/
    @MessageMapping("/massRequest")
    @SendTo("/mass/getResponse")
    //SendTo 发送至 Broker 下的指定订阅路径mass ,
    // Broker再根据getResponse发送消息到订阅了/mass/getResponse的用户处
    public ChatRoomResponse mass(ChatRoomRequest chatRoomRequest){
        System.out.println("name = " + chatRoomRequest.getName()
                +" chatValue = " + chatRoomRequest.getChatValue());
        ChatRoomResponse response=new ChatRoomResponse();
        response.setName(chatRoomRequest.getName());
        response.setChatValue(chatRoomRequest.getChatValue());
        //this.template.convertAndSend();
        return response;
    }

    /*单独聊天，接受发送至自aloneRequest的请求*/
    @MessageMapping("/aloneRequest")
    //@SendToUser
    public ChatRoomResponse alone(ChatRoomRequest chatRoomRequest){
        System.out.println("SendToUser = " + chatRoomRequest.getUserId()
                +" FromName = " + chatRoomRequest.getName()
                +" ChatValue = " + chatRoomRequest.getChatValue());
        ChatRoomResponse response=new ChatRoomResponse();
        response.setName(chatRoomRequest.getName());
        response.setChatValue(chatRoomRequest.getChatValue());
        //会发送到订阅了 /user/{用户的id}/alone 的用户处
        //  {userid}/alone ==> /queue/{userid}/alone
        this.template.convertAndSendToUser(chatRoomRequest.getUserId()
                +"","/alone",response);
        return response;
    }
}