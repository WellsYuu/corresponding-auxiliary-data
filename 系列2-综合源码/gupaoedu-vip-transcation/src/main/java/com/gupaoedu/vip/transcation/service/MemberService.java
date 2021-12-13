package com.gupaoedu.vip.transcation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gupaoedu.vip.transcation.dao.MemberDao;
import com.gupaoedu.vip.transcation.entity.Member;

@Service
public class MemberService {

	@Autowired MemberDao memberDao;
	
	public List<Member> query() throws Exception{
		return memberDao.select();
	}
	
	
	public boolean remove(long id) throws Exception{
		boolean r = memberDao.delete(id) > 0;
		throw new Exception("自定义异常");
//		return r;
	}
	
	
	public boolean modify(long id,String name) throws Exception{
		return memberDao.update(id, name) > 0;
	}
	
	
	public boolean add(String name) throws Exception{
		boolean r = memberDao.insert(name) > 0;
//		throw new Exception("测试回滚");
		return r;
	}
	
//	第一层   login本身是一个事务
	@Transactional
	public boolean login(long id,String name) throws Exception{
		//事务里面又调用了事务方法
		//很显然，事务嵌套了两层
		boolean modify = this.modify(id, name);
//		throw new Exception("测试无事务");
		return modify;
	}
	
}
