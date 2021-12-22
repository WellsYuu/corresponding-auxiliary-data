package com.jiagouedu.web.util;

import com.jiagouedu.core.FrontContainer;
import com.jiagouedu.core.ManageContainer;
import com.jiagouedu.core.system.bean.User;
import com.jiagouedu.services.front.account.bean.Account;

import javax.servlet.http.HttpSession;

/**
 * Created by dylan on 15-2-11.
 */
public class LoginUserHolder {
    public static User getLoginUser(){
        HttpSession session = RequestHolder.getSession();
        return session == null ? null : (User)session.getAttribute(ManageContainer.manage_session_user_info);
    }
    public static Account getLoginAccount(){
        HttpSession session = RequestHolder.getSession();
        return session == null ? null : (Account)session.getAttribute(FrontContainer.USER_INFO);
    }
}
