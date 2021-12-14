package com.tuling.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
// handle
public class SimpleControl implements Controller {
	public ModelAndView getName(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=new ModelAndView("/WEB-INF/page/userView.jsp");
		mv.addObject("name", "hanmeimei");
		if(request.getParameter("error")!=null){
			throw new RuntimeException(request.getParameter("error"));
		}
		return mv;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return getName(request, response);
	}
}
