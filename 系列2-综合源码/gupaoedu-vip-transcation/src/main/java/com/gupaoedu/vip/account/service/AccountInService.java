package com.gupaoedu.vip.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gupaoedu.vip.account.dao.AccountDao;

@Service
public class AccountInService {
	
	@Autowired AccountDao accountDao;

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public int transferForIn(final String in,Double money) throws Exception{
		int inCount = accountDao.upateForIn(in, money);
		
//		if(inCount == 0){
//			throw new Exception("转入失败");
//		}
		return inCount;
	}
}
