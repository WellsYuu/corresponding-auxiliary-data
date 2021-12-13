package com.gupaoedu.singleton;

import java.util.HashMap;
import java.util.Map;
//类似Spring里面的方法，将类名注册，下次从里面直接获取。  
public class Singleton6 {  
    private static Map<String,Singleton6> map = new HashMap<String,Singleton6>();  
    static {
        Singleton6 single = new Singleton6();
        map.put(single.getClass().getName(), single);
    }
    //保护的默认构造子  
    protected Singleton6(){}  
    //静态工厂方法,返还此类惟一的实例  
    public static Singleton6 getInstance(String name) {  
        if(name == null) {  
             name = Singleton6.class.getName();  
        }  
        if(map.get(name) == null) {  
	   try {  
	       map.put(name, (Singleton6) Class.forName(name).newInstance());  
	   } catch (InstantiationException e) {  
	       e.printStackTrace();  
	   } catch (IllegalAccessException e) {  
	       e.printStackTrace();  
	   } catch (ClassNotFoundException e) {  
	       e.printStackTrace();  
	   }  
}  
return map.get(name);  
}  
}
