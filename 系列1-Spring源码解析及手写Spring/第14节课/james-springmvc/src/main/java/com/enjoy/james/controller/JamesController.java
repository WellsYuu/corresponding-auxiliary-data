package com.enjoy.james.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.enjoy.james.annotation.EnjoyController;
import com.enjoy.james.annotation.EnjoyQualifier;
import com.enjoy.james.annotation.EnjoyRequestHeader;
import com.enjoy.james.annotation.EnjoyRequestMapping;
import com.enjoy.james.annotation.EnjoyRequestParam;
import com.enjoy.james.service.JamesService;

@EnjoyController
@EnjoyRequestMapping("/james")
public class JamesController {
    
    @EnjoyQualifier("JamesServiceImpl")
    private JamesService jamesService;
    
    @EnjoyRequestMapping("/query")
    public void query(HttpServletRequest request, HttpServletResponse response,
            @EnjoyRequestParam("name") String name,
            @EnjoyRequestParam("age") String age) {
        
        try {
            PrintWriter pw = response.getWriter();
            String result = jamesService.query(name,age);
            pw.write(result);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @EnjoyRequestMapping("/insert")
    public void insert(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            PrintWriter pw = response.getWriter();
            String result = jamesService.insert("0000");
            
            pw.write(result);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @EnjoyRequestMapping("/update")
    public void update(HttpServletRequest request,
            HttpServletResponse response, String param) {
        try {
            PrintWriter pw = response.getWriter();
            String result = jamesService.update(param);
            
            pw.write(result);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
