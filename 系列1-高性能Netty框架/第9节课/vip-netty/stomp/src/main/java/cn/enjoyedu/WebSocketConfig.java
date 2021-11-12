package cn.enjoyedu;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
@Configuration
/*开启使用Stomp协议来传输基于消息broker的消息
这时控制器支持使用@MessageMapping,就像使用@RequestMapping一样*/
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /*注册STOMP协议的节点(endpoint),并映射指定的url,
        * 添加一个访问端点“/endpointMark”,客户端打开双通道时需要的url,
        * 允许所有的域名跨域访问，指定使用SockJS协议。*/
        registry.addEndpoint("/endpointMark")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /*配置一个消息代理
        * mass 负责群聊
        * queue 单聊*/
        registry.enableSimpleBroker(
                "/mass","/queue");

        //一对一的用户，请求发到/queue
        registry.setUserDestinationPrefix("/queue");
    }

}
