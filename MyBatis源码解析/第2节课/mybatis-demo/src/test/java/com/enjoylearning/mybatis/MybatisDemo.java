package com.enjoylearning.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.enjoylearning.mybatis.entity.EmailSexBean;
import com.enjoylearning.mybatis.entity.TJobHistory;
import com.enjoylearning.mybatis.entity.TUser;
import com.enjoylearning.mybatis.mapper.TJobHistoryAnnoMapper;
import com.enjoylearning.mybatis.mapper.TUserMapper;

public class MybatisDemo {

	private SqlSessionFactory sqlSessionFactory;

	@Before
	public void init() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// 1.读取mybatis配置文件创SqlSessionFactory
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		inputStream.close();
	}

	@Test
	// 测试自动映射
	public void testAutoMapping() throws IOException {
		// 2.获取sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.获取对应mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		// 4.执行查询语句并返回结果
		TUser user = mapper.selectByPrimaryKey(1);
		System.out.println(user);
	}

	
	// 多参数查询
	@Test
	public void testManyParamQuery() {
		// 2.获取sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.获取对应mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);

		String email = "qq.com";
		Byte sex = 1;

		// 第一种方式使用map
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", email);
		params.put("sex", sex);
		List<TUser> list1 = mapper.selectByEmailAndSex1(params);
		System.out.println(list1.size());

		// 第二种方式直接使用参数
		List<TUser> list2 = mapper.selectByEmailAndSex2(email, sex);
		System.out.println(list2.size());

		// 第三种方式用对象
		EmailSexBean esb = new EmailSexBean();
		esb.setEmail(email);
		esb.setSex(sex);
		List<TUser> list3 = mapper.selectByEmailAndSex3(esb);
		System.out.println(list3.size());
	}



	@Test
	// 测试插入数据自动生成id
	public void testInsertGenerateId1() throws IOException {
		// 2.获取sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.获取对应mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		// 4.执行查询语句并返回结果
		TUser user1 = new TUser();
		user1.setUserName("test1");
		user1.setRealName("realname1");
		user1.setEmail("myemail1");
		mapper.insert1(user1);
		sqlSession.commit();
		System.out.println(user1.getId());
	}

	@Test
	// 测试插入数据自动生成id
	public void testInsertGenerateId2() throws IOException {
		// 2.获取sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.获取对应mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		// 4.执行查询语句并返回结果
		TUser user2 = new TUser();
		user2.setUserName("test2");
		user2.setRealName("realname2");
		user2.setEmail("myemai2l");
		mapper.insert2(user2);
		sqlSession.commit();
		System.out.println(user2.getId());
	}




	@Test
	// 参数#和参数$区别测试(动态sql 入门)
	public void testSymbol() {
		// 2.获取sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.获取对应mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		
		String inCol = "id, user_name, real_name, sex, mobile, email, note";
		String tableName = "t_user";
		Byte sex = 1;
		String orderStr = "sex,user_name";
		
		List<TUser> list = mapper.selectBySymbol(tableName, inCol, orderStr, sex);
		System.out.println(list.size());
		
	}

	@Test
	// 注解测试
	public void testAnno() {
		// 2.获取sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.获取对应mapper
		TJobHistoryAnnoMapper mapper = sqlSession.getMapper(TJobHistoryAnnoMapper.class);
		
		List<TJobHistory> list = mapper.selectByUserId(1);
		System.out.println(list.size());
		
		List<TJobHistory> listAll = mapper.selectAll();
		System.out.println(listAll.size());
		
		TJobHistory job = new TJobHistory();
		job.setTitle("产品经理");
		job.setUserId(1);
		job.setCompName("美团");
		job.setYears(3);
		
		mapper.insert(job);
		System.out.println(job.getId());
	}
	
	
	//--------------------------------动态sql---------------------------------------
	
	@Test
	// if用于select，并与where配合
	public void testSelectIfOper() {
		// 2.获取sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.获取对应mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		
		String email = "qq.com";
		Byte sex = null;
		List<TUser> list = mapper.selectIfOper(email, sex);
//		List<TUser> list = mapper.selectIfandWhereOper(email, sex);
		System.out.println(list.size());
		
	}

	@Test
	// if用于update，并与set配合
	public void testUpdateIfOper() {
		// 2.获取sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.获取对应mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		
		TUser user = new TUser();
		user.setId(3);
		user.setUserName("cindy");
		user.setRealName("王美丽");
		user.setEmail("xxoo@163.com");
		user.setMobile("18695988747");
		user.setNote("cindy's note");
		user.setSex((byte) 2);
		user.setPositionId(1);
		System.out.println(mapper.updateIfOper(user));
//		System.out.println(mapper.updateIfAndSetOper(user));
		
	}
	
	
	@Test
	// if用于insert，并与trim配合
	public void testInsertIfOper() {
		// 2.获取sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.获取对应mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		
		TUser user = new TUser();
		user.setUserName("mark");
		user.setRealName("毛毛");
		user.setEmail("xxoo@163.com");
		user.setMobile("18695988747");
		user.setNote("mark's note");
		user.setSex((byte) 1);
		user.setPositionId(1);
		System.out.println(mapper.insertIfOper(user));
//		System.out.println(mapper.insertSelective(user));
	}
	
	
	
	@Test
	// Foreach用于in查询
	public void testForeach4In() {
		// 2.获取sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.获取对应mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		
		String[] names = new String[]{"lison","james"};
		List<TUser> users = mapper.selectForeach4In(names);
		System.out.println(users.size());
	}
	
	
	@Test
	// Foreach用于批量插入
	public void testForeach4Insert() {
		// 2.获取sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.获取对应mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		
		TUser user1 = new TUser();
		user1.setUserName("king");
		user1.setRealName("李小京");
		user1.setEmail("li@qq.com");
		user1.setMobile("18754548787");
		user1.setNote("king's note");
		user1.setSex((byte)1);
		TUser user2 = new TUser();
		user2.setUserName("deer");
		user2.setRealName("陈大林");
		user2.setEmail("chen@qq.com");
		user2.setMobile("18723138787");
		user2.setNote("deer's note");
		user2.setSex((byte)1);
		
		
		int i = mapper.insertForeach4Batch(Arrays.asList(user1,user2));
		System.out.println(i);
	}
	
	
	
	
	
	
	
	
	
	
	
	//------------------------------动态sql结束----------------------------------------
	
	
	
	@Test
	// 批量更新
	public void testBatchExcutor() {
		// 2.获取sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
//		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		// 3.获取对应mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		
		TUser user = new TUser();
		user.setUserName("mark");
		user.setRealName("毛毛");
		user.setEmail("xxoo@163.com");
		user.setMobile("18695988747");
		user.setNote("mark's note");
		user.setSex((byte) 1);
		user.setPositionId(1);
		System.out.println(mapper.insertSelective(user));
		
		TUser user1 = new TUser();
		user1.setId(3);
		user1.setUserName("cindy");
		user1.setRealName("王美丽");
		user1.setEmail("xxoo@163.com");
		user1.setMobile("18695988747");
		user1.setNote("cindy's note");
		user1.setSex((byte) 2);
		user1.setPositionId(1);
		System.out.println(mapper.updateIfOper(user1));
		
		sqlSession.commit();
		System.out.println("----------------");

	}
	
	
	
	@Test
	// 测试两种关联方式
	public void testAssociation() {
		// 2.获取sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 3.获取对应mapper
		TUserMapper mapper = sqlSession.getMapper(TUserMapper.class);
		// 4.执行查询语句并返回结果
		// ----------------------
		List<TUser> selectUserJobs1 = mapper.selectUserJobs1();
		List<TUser> selectUserJobs2 = mapper.selectUserJobs2();
		for (TUser tUser : selectUserJobs1) {
			System.out.println(tUser);
		}
		for (TUser tUser : selectUserJobs2) {
			System.out.println(tUser.getJobs().size());
		}
	}
	

}
