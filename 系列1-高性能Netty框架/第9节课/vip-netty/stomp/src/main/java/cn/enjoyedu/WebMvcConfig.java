package cn.enjoyedu;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
@Configuration
public class WebMvcConfig  implements WebMvcConfigurer {
	
	 @Override
	   public void addViewControllers(ViewControllerRegistry registry) {
         registry.addViewController("/chatroom")
				 .setViewName("/wechat_room");
	   }

}
