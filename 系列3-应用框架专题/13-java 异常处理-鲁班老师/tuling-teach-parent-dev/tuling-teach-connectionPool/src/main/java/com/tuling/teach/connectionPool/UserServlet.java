package com.tuling.teach.connectionPool;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tommy on 2017/10/25.
 */
@WebServlet(name = "/user")
public class UserServlet extends HttpServlet {
    private WebApplicationContext context;
    private UserService service;

    @Override
    public void init() throws ServletException {
        super.init();
        context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
         service = context.getBean(UserService.class);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserInfo> list = service.getUserById(Integer.parseInt(request.getParameter("id")));
        response.setCharacterEncoding("UTF-8");
        Arrays.toString(list.toArray());
        for (UserInfo userInfo : list) {
            response.getWriter().println(userInfo);
        }
        response.getWriter().flush();
        response.setContentType("text/html; charset=utf-8");
    }
}
