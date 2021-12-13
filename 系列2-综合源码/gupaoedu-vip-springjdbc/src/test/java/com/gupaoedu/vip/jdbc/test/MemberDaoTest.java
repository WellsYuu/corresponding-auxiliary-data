package com.gupaoedu.vip.jdbc.test;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.gupaoedu.vip.jdbc.demo.dao.MemberDao;
import com.gupaoedu.vip.jdbc.demo.entity.Member;

@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MemberDaoTest {
	@Autowired MemberDao memberDao;
	
	//2009开发第一版本ibatis
	//平稳运行了8年，没有出现大的事故
	//你们的目标：超越Tom老师（只是你们起步的榜样，不是最终的榜样）
	//如果一旦在业务中出现大的事故，一定要反馈给Tom老师
	
	
	//Tom老师写框架的目的不是为了要出名，也不是为了到得到多少利用
	//兴趣，解决实际问题（适合自己的才是最好的）
	//不约而同（感觉到一点，所谓框架不管怎么变，万变不离其宗）
	
	
	//前端UI框架 BootStrap API非常乱
	//开发EUI（使用像EasyUI一样简单，功能像Ext一样强大）
	
	
	//SpringBoot 很简单，零配置
	//基于SpringMVC进行了二次开发
	
	
	
	//框架做了两件事
	//1、自动生成SQL语句
	//2、自动ORM映射
	
	
	//Hibernate优点
	//1、API丰富，可以实现无SQL操作（HQL），为了兼容所有数据库（都会先解释为HQL）
	//再由HQL翻译成SQL(当然，有支持直接执行SQL的API，为了考虑用户需求复杂性)
	//对所有数据库方言都支持得非常不错
	//2、ORM全自动化
	
	
	//MyBatis优点
	//1、轻量级，性能好
	//2、SQL和Java代码分离（SqlMap,把每一条SQL语句起一名字，作为Map的key保存）
	//get("selectByName")
	
	
	//自己的框架（择其善者而从之，开发出一个适合自己的框架）
	//爱美之心，人皆有之
	//找对象，一定是找个适合自己的（有时候需要去接受对方的缺点）
	
	//第一，性能要好，是啥就是啥，不经过二次处理（不对SQL语句进行二次包装）
	//第二，单表操作实现NoSQL(只要用JDBC，SQL是不能省，只不过这拼接SQL的过程不要用户自己写了
	//由程序自动生成,最终生成的是一个字符串)
	//QueryRule(顾名思义，查询规则)，可以根据QueryRule来自动生成SQL语句
	//第三，ORM零配置实现自动化（利用反射机制，把字段和属性对应上，然后，自动实例化返回结果）
	
	//原则：约定优于配置（保证代码健壮性）
	//DAO原则：一个Dao只操作一张表
	//约定：做修改和删除的是根据主键来操作的
	//约定：尽量使用单表操作，如果实在要多表操作，可以先把数据查出来放到内存，然后在内存中进行计算
	//约定：支持读写分离
	//约定：支持分库分表
	//约定：ORM支持的类型原则上只认Java八大基本数据类型  + String（为了降低复杂度）
	//对象状态：临时态、持久态、删除态、游离态
	
	//Mapping 配置  OneToOne  OneToMany  ManyToMany
	//简化，JdbcTemplate自己写去
	
	@Test
//	@Ignore
	public void testSelectByName(){
		try {
			List<Member> r = memberDao.selectByName("tom");
			System.out.println(JSON.toJSON(r));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Ignore
	public void testSelectAll(){
		try {
			System.out.println("-------" + JSON.toJSONString(memberDao.selectAll()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Ignore
	public void testInsertOne(){
		try {
			Member data = new Member();
			data.setName("小星星");
			boolean r = memberDao.insterOne(data);
			if(r){
				System.out.println(data.getId());
			}else{
				System.out.println("失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Ignore
	public void testUpdate(){
		try {
			Member data = new Member();
			data.setId(6L);
			data.setName("于菲");
			boolean r = memberDao.updataOne(data);
			System.out.println(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	@Ignore
	public void testDelete(){
		try {
			Member data = new Member();
			data.setId(6L);
			boolean r = memberDao.deleteOne(data);
			System.out.println(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
