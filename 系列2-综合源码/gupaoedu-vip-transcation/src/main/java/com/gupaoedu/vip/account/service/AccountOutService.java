package com.gupaoedu.vip.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gupaoedu.vip.account.dao.AccountDao;

@Service
public class AccountOutService {

	@Autowired AccountDao accountDao;
	
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public int transferForOut(final String out,Double money) throws Exception{
		int outCount = accountDao.upateForOut(out, money);
//		if(outCount == 0){
//			throw new Exception("转出失败");
//		}
		return outCount;
	}
	
}
