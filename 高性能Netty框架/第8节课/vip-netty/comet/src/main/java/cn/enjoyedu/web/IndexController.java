package cn.enjoyedu.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String hello(){
        return "index";
    }

}
