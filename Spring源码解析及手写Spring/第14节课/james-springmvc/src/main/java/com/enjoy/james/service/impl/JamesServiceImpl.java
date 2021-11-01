package com.enjoy.james.service.impl;

import com.enjoy.james.annotation.EnjoyService;
import com.enjoy.james.service.JamesService;

@EnjoyService("JamesServiceImpl")
public class JamesServiceImpl implements JamesService {
    
    public String query(String name, String age) {
        
        return "{name="+name+",age="+age+"}";
    }
    
    public String insert(String param) {
        // TODO Auto-generated method stub
        return  "insert successful.....";
    }
    
    public String update(String param) {
        // TODO Auto-generated method stub
        return "update successful.....";
    }
    
}
