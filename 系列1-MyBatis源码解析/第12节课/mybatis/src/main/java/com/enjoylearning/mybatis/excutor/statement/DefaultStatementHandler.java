package com.enjoylearning.mybatis.excutor.statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.enjoylearning.mybatis.config.MappedStatement;


public class DefaultStatementHandler implements StatementHandler {
	
	private MappedStatement mappedStatment;
	
	
	
	

	public DefaultStatementHandler(MappedStatement mappedStatment) {
		super();
		this.mappedStatment = mappedStatment;
	}

	@Override
	public PreparedStatement prepare(Connection connection) throws SQLException {
		return connection.prepareStatement(mappedStatment.getSql());
	}

	@Override
	public ResultSet query(PreparedStatement statement) {
		try {
			return statement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
