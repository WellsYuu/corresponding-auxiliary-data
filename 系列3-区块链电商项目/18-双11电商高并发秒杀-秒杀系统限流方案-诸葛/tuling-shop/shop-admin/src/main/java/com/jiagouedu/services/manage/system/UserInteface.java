package com.jiagouedu.services.manage.system;

import com.jiagouedu.core.Services;
import com.jiagouedu.core.system.bean.User;

public interface UserInteface extends Services<User> {
	/**
	 * @param e
	 * @return
	 */
	public User login(User e);
}
