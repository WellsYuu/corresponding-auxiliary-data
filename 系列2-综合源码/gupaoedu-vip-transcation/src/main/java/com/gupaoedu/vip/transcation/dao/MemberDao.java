package com.gupaoedu.vip.transcation.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.config.TxNamespaceHandler;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;

import com.gupaoedu.vip.transcation.entity.Member;

@Repository
public class MemberDao {
	
	private JdbcTemplate template;
	
	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource){
		template = new JdbcTemplate(dataSource);
	}
	
	
	public List<Member> select(){
		
		return template.query("select * from t_member", new RowMapper(){

			@Override
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				Member m = new Member();
				m.setId(rs.getLong("id"));
				m.setName(rs.getString("name"));
				return m;
			}
			
		});
		
	}
	
	
	public int insert(String name) throws Exception{
		return template.update("insert into t_member(name) values(?)",name);
	}
	
	
	public int delete(long id) throws Exception{
		return template.update("delete from t_member where id = ?",id);
	}
	
	
	public int update(long id,String name) throws Exception{
		return template.update("update t_member set name = ? where id = ?",name,id);
	}
	
}
