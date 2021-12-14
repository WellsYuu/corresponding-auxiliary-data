package com.tuling.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Tommy on 2017/10/17.
 */
@Controller
public class FreemarkControl {
    @RequestMapping("/fm.do")
    public ModelAndView getName(String name){
        ModelAndView mv=new ModelAndView("/userView");
        mv.addObject("name","hello :"+name);
        return mv;
    }
}
