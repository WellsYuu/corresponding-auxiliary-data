package com.alibaba.rocketmq.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.rocketmq.LoginFilter;
import com.alibaba.rocketmq.common.Table;
import com.alibaba.rocketmq.config.ConfigureInitializer;
import com.alibaba.rocketmq.service.ClusterService;


/**
 * 
 * @author yankai913@gmail.com
 * @date 2014-2-8
 */
@Controller
public class ClusterAction extends AbstractAction {
	Logger log = LoggerFactory.getLogger(ClusterAction.class);
    @Autowired
    ClusterService clusterService;
    @Autowired
    ConfigureInitializer configureInitializer;

    @Override
    protected String getFlag() {
        return "cluster_flag";
    }


    @Override
    protected String getName() {
        return "Cluster";
    }
    
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String index(ModelMap map) {
    	LoginFilter.loginTime = DateTime.now().minusDays(1);
    	LoginFilter.user = null;
    	return "index";
    }
    @RequestMapping(value = "/doLogin", method = {RequestMethod.POST})
    public String index(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
    	String name = request.getParameter("name");
    	String checkCode = request.getParameter("checkCode");
    	if(StringUtils.isBlank(name)){
    		map.put("errorMsg", "用户名不能为空");
    		return "index";
    	}
    	if(StringUtils.isBlank(checkCode)){
    		map.put("errorMsg", "校验码错误");
    		return "index";
    	}
    	name = name.trim();
    	String password = configureInitializer.userMap.get(name);
    	if(StringUtils.isBlank(password)){
    		map.put("errorMsg", "用户名错误");
    		return "index";
    	}
    	if(!password.equals(checkCode)) {
    		map.put("errorMsg", "用户名或者校验码错误");
    		return "index";
    	}
    	LoginFilter.loginTime = DateTime.now();
    	LoginFilter.user = name;
    	
    	Cookie cookie = new Cookie("user", name);
    	cookie.setMaxAge(60 * 30);
    	cookie.setPath("/");
    	response.addCookie(cookie);
    	log.info(name + "登录成功");
    	return list(map);
    }

    @RequestMapping(value = "/cluster/list", method = RequestMethod.GET)
    public String list(ModelMap map) {
        putPublicAttribute(map, "list");
        try {
            Table table = clusterService.list();
            putTable(map, table);
        }
        catch (Throwable t) {
            putAlertMsg(t, map);
        }
        return TEMPLATE;
    }


    @RequestMapping(value = "/cluster/demo", method = RequestMethod.GET)
    public String demo() {
        return "cluster/demo";
    }

}
