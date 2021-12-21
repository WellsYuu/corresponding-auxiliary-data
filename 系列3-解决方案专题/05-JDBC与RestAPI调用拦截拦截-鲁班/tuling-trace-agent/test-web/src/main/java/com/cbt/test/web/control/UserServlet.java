package com.cbt.test.web.control;

import com.alibaba.fastjson.JSONArray;
import com.cbt.test.web.bean.User;
import com.cbt.test.web.service.UserService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2018/5/30.
 */
@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private WebApplicationContext context;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        userService = context.getBean(UserService.class);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.getWriter().println("hello tuling trace .this is test page");
        String name = request.getParameter("name");
        String id = request.getParameter("id");
        User user = userService.getUser(id, name);
        String result = JSONArray.toJSONString(user);
        response.getWriter().println(result);
//        request.getRequestDispatcher("/index.html").forward(request, response);
        response.getWriter().println("end-----");
    }
}
