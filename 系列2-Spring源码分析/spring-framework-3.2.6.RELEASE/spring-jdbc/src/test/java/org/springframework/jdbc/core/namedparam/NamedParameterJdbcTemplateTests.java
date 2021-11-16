/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.jdbc.core.namedparam;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.Customer;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

/**
 * @author Rick Evans
 * @author Juergen Hoeller
 * @author Chris Beams
 */
public class NamedParameterJdbcTemplateTests {

	private static final String SELECT_NAMED_PARAMETERS =
		"select id, forename from custmr where id = :id and country = :country";
	private static final String SELECT_NAMED_PARAMETERS_PARSED =
		"select id, forename from custmr where id = ? and country = ?";
	private static final String SELECT_NO_PARAMETERS =
			"select id, forename from custmr";

	private static final String UPDATE_NAMED_PARAMETERS =
		"update seat_status set booking_id = null where performance_id = :perfId and price_band_id = :priceId";
	private static final String UPDATE_NAMED_PARAMETERS_PARSED =
		"update seat_status set booking_id = null where performance_id = ? and price_band_id = ?";

	private static final String[] COLUMN_NAMES = new String[] {"id", "forename"};

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private Connection connection;
	private DataSource dataSource;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private DatabaseMetaData databaseMetaData;
	private Map<String, Object> params = new HashMap<String, Object>();
	private NamedParameterJdbcTemplate namedParameterTemplate;

	@Before
	public void setUp() throws Exception {
		connection = mock(Connection.class);
		dataSource = mock(DataSource.class);
		preparedStatement =	mock(PreparedStatement.class);
		resultSet = mock(ResultSet.class);
		namedParameterTemplate = new NamedParameterJdbcTemplate(dataSource);
		databaseMetaData = mock(DatabaseMetaData.class);
		given(dataSource.getConnection()).willReturn(connection);
		given(connection.prepareStatement(anyString())).willReturn(preparedStatement);
		given(preparedStatement.getConnection()).willReturn(connection);
		given(preparedStatement.executeQuery()).willReturn(resultSet);
		given(databaseMetaData.getDatabaseProductName()).willReturn("MySQL");
		given(databaseMetaData.supportsBatchUpdates()).willReturn(true);
	}

	@Test
	public void testNullDataSourceProvidedToCtor() throws Exception {
		thrown.expect(IllegalArgumentException.class);
		new NamedParameterJdbcTemplate((DataSource) null);
	}

	@Test
	public void testNullJdbcTemplateProvidedToCtor() throws Exception {
		thrown.expect(IllegalArgumentException.class);
		new NamedParameterJdbcTemplate((JdbcOperations) null);
	}

	@Test
	public void testExecute() throws SQLException {
		given(preparedStatement.executeUpdate()).willReturn(1);

		params.put("perfId", 1);
		params.put("priceId", 1);
		Object result = namedParameterTemplate.execute(UPDATE_NAMED_PARAMETERS, params,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException {
						assertEquals(preparedStatement, ps);
						ps.executeUpdate();
						return "result";
					}
				});

		assertEquals("result", result);
		verify(connection).prepareStatement(UPDATE_NAMED_PARAMETERS_PARSED);
		verify(preparedStatement).setObject(1, 1);
		verify(preparedStatement).setObject(2, 1);
		verify(preparedStatement).close();
		verify(connection).close();
	}

	@Test
	public void testExecuteWithTypedParameters() throws SQLException {
		given(preparedStatement.executeUpdate()).willReturn(1);

		params.put("perfId", new SqlParameterValue(Types.DECIMAL, 1));
		params.put("priceId", new SqlParameterValue(Types.INTEGER, 1));
		Object result = namedParameterTemplate.execute(UPDATE_NAMED_PARAMETERS, params,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException {
						assertEquals(preparedStatement, ps);
						ps.executeUpdate();
						return "result";
					}
				});

		assertEquals("result", result);
		verify(connection).prepareStatement(UPDATE_NAMED_PARAMETERS_PARSED);
		verify(preparedStatement).setObject(1, 1, Types.DECIMAL);
		verify(preparedStatement).setObject(2, 1, Types.INTEGER);
		verify(preparedStatement).close();
		verify(connection).close();
	}

	@Test
	public void testExecuteNoParameters() throws SQLException {
		given(preparedStatement.executeUpdate()).willReturn(1);

		Object result = namedParameterTemplate.execute(SELECT_NO_PARAMETERS,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException {
						assertEquals(preparedStatement, ps);
						ps.executeQuery();
						return "result";
					}
				});

		assertEquals("result", result);
		verify(connection).prepareStatement(SELECT_NO_PARAMETERS);
		verify(preparedStatement).close();
		verify(connection).close();
	}

	@Test
	public void testQueryWithResultSetExtractor() throws SQLException {
		given(resultSet.next()).willReturn(true);
		given(resultSet.getInt("id")).willReturn(1);
		given(resultSet.getString("forename")).willReturn("rod");

		params.put("id", new SqlParameterValue(Types.DECIMAL, 1));
		params.put("country", "UK");
		Customer cust = namedParameterTemplate.query(SELECT_NAMED_PARAMETERS, params,
				new ResultSetExtractor<Customer>() {
					@Override
					public Customer extractData(ResultSet rs) throws SQLException,
							DataAccessException {
						rs.next();
						Customer cust = new Customer();
						cust.setId(rs.getInt(COLUMN_NAMES[0]));
						cust.setForename(rs.getString(COLUMN_NAMES[1]));
						return cust;
					}
				});

		assertTrue("Customer id was assigned correctly", cust.getId() == 1);
		assertTrue("Customer forename was assigned correctly", cust.getForename().equals("rod"));
		verify(connection).prepareStatement(SELECT_NAMED_PARAMETERS_PARSED);
		verify(preparedStatement).setObject(1, 1, Types.DECIMAL);
		verify(preparedStatement).setString(2, "UK");
		verify(preparedStatement).close();
		verify(connection).close();
	}

	@Test
	public void testQueryWithResultSetExtractorNoParameters() throws SQLException {
		given(resultSet.next()).willReturn(true);
		given(resultSet.getInt("id")).willReturn(1);
		given(resultSet.getString("forename")).willReturn("rod");

		Customer cust = namedParameterTemplate.query(SELECT_NO_PARAMETERS,
				new ResultSetExtractor<Customer>() {
					@Override
					public Customer extractData(ResultSet rs) throws SQLException,
							DataAccessException {
						rs.next();
						Customer cust = new Customer();
						cust.setId(rs.getInt(COLUMN_NAMES[0]));
						cust.setForename(rs.getString(COLUMN_NAMES[1]));
						return cust;
					}
				});

		assertTrue("Customer id was assigned correctly", cust.getId() == 1);
		assertTrue("Customer forename was assigned correctly", cust.getForename().equals("rod"));
		verify(connection).prepareStatement(SELECT_NO_PARAMETERS);
		verify(preparedStatement).close();
		verify(connection).close();
	}

	@Test
	public void testQueryWithRowCallbackHandler() throws SQLException {
		given(resultSet.next()).willReturn(true, false);
		given(resultSet.getInt("id")).willReturn(1);
		given(resultSet.getString("forename")).willReturn("rod");

		params.put("id", new SqlParameterValue(Types.DECIMAL, 1));
		params.put("country", "UK");
		final List<Customer> customers = new LinkedList<Customer>();
		namedParameterTemplate.query(SELECT_NAMED_PARAMETERS, params, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				Customer cust = new Customer();
				cust.setId(rs.getInt(COLUMN_NAMES[0]));
				cust.setForename(rs.getString(COLUMN_NAMES[1]));
				customers.add(cust);
			}
		});

		assertEquals(1, customers.size());
		assertTrue("Customer id was assigned correctly", customers.get(0).getId() == 1);
		assertTrue("Customer forename was assigned correctly", customers.get(0).getForename().equals("rod"));
		verify(connection).prepareStatement(SELECT_NAMED_PARAMETERS_PARSED);
		verify(preparedStatement).setObject(1, 1, Types.DECIMAL);
		verify(preparedStatement).setString(2, "UK");
		verify(preparedStatement).close();
		verify(connection).close();
	}

	@Test
	public void testQueryWithRowCallbackHandlerNoParameters() throws SQLException {
		given(resultSet.next()).willReturn(true, false);
		given(resultSet.getInt("id")).willReturn(1);
		given(resultSet.getString("forename")).willReturn("rod");

		final List<Customer> customers = new LinkedList<Customer>();
		namedParameterTemplate.query(SELECT_NO_PARAMETERS, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				Customer cust = new Customer();
				cust.setId(rs.getInt(COLUMN_NAMES[0]));
				cust.setForename(rs.getString(COLUMN_NAMES[1]));
				customers.add(cust);
			}
		});

		assertEquals(1, customers.size());
		assertTrue("Customer id was assigned correctly", customers.get(0).getId() == 1);
		assertTrue("Customer forename was assigned correctly", customers.get(0).getForename().equals("rod"));
		verify(connection).prepareStatement(SELECT_NO_PARAMETERS);
		verify(preparedStatement).close();
		verify(connection).close();
	}

	@Test
	public void testQueryWithRowMapper() throws SQLException {
		given(resultSet.next()).willReturn(true, false);
		given(resultSet.getInt("id")).willReturn(1);
		given(resultSet.getString("forename")).willReturn("rod");

		params.put("id", new SqlParameterValue(Types.DECIMAL, 1));
		params.put("country", "UK");
		List<Customer> customers = namedParameterTemplate.query(SELECT_NAMED_PARAMETERS, params,
				new RowMapper<Customer>() {
					@Override
					public Customer mapRow(ResultSet rs, int rownum) throws SQLException {
						Customer cust = new Customer();
						cust.setId(rs.getInt(COLUMN_NAMES[0]));
						cust.setForename(rs.getString(COLUMN_NAMES[1]));
						return cust;
					}
				});
		assertEquals(1, customers.size());
		assertTrue("Customer id was assigned correctly", customers.get(0).getId() == 1);
		assertTrue("Customer forename was assigned correctly", customers.get(0).getForename().equals("rod"));
		verify(connection).prepareStatement(SELECT_NAMED_PARAMETERS_PARSED);
		verify(preparedStatement).setObject(1, 1, Types.DECIMAL);
		verify(preparedStatement).setString(2, "UK");
		verify(preparedStatement).close();
		verify(connection).close();
	}

	@Test
	public void testQueryWithRowMapperNoParameters() throws SQLException {
		given(resultSet.next()).willReturn(true, false);
		given(resultSet.getInt("id")).willReturn(1);
		given(resultSet.getString("forename")).willReturn("rod");

		List<Customer> customers = namedParameterTemplate.query(SELECT_NO_PARAMETERS,
				new RowMapper<Customer>() {
					@Override
					public Customer mapRow(ResultSet rs, int rownum) throws SQLException {
						Customer cust = new Customer();
						cust.setId(rs.getInt(COLUMN_NAMES[0]));
						cust.setForename(rs.getString(COLUMN_NAMES[1]));
						return cust;
					}
				});
		assertEquals(1, customers.size());
		assertTrue("Customer id was assigned correctly", customers.get(0).getId() == 1);
		assertTrue("Customer forename was assigned correctly", customers.get(0).getForename().equals("rod"));
		verify(connection).prepareStatement(SELECT_NO_PARAMETERS);
		verify(preparedStatement).close();
		verify(connection).close();
	}

	@Test
	public void testQueryForObjectWithRowMapper() throws SQLException {
		given(resultSet.next()).willReturn(true, false);
		given(resultSet.getInt("id")).willReturn(1);
		given(resultSet.getString("forename")).willReturn("rod");

		params.put("id", new SqlParameterValue(Types.DECIMAL, 1));
		params.put("country", "UK");
		Customer cust = namedParameterTemplate.queryForObject(SELECT_NAMED_PARAMETERS, params,
				new RowMapper<Customer>() {
					@Override
					public Customer mapRow(ResultSet rs, int rownum) throws SQLException {
						Customer cust = new Customer();
						cust.setId(rs.getInt(COLUMN_NAMES[0]));
						cust.setForename(rs.getString(COLUMN_NAMES[1]));
						return cust;
					}
				});
		assertTrue("Customer id was assigned correctly", cust.getId() == 1);
		assertTrue("Customer forename was assigned correctly", cust.getForename().equals("rod"));
		verify(connection).prepareStatement(SELECT_NAMED_PARAMETERS_PARSED);
		verify(preparedStatement).setObject(1, 1, Types.DECIMAL);
		verify(preparedStatement).setString(2, "UK");
		verify(preparedStatement).close();
		verify(connection).close();
	}

	@Test
	public void testUpdate() throws SQLException {
		given(preparedStatement.executeUpdate()).willReturn(1);

		params.put("perfId", 1);
		params.put("priceId", 1);
		int rowsAffected = namedParameterTemplate.update(UPDATE_NAMED_PARAMETERS, params);

		assertEquals(1, rowsAffected);
		verify(connection).prepareStatement(UPDATE_NAMED_PARAMETERS_PARSED);
		verify(preparedStatement).setObject(1, 1);
		verify(preparedStatement).setObject(2, 1);
		verify(preparedStatement).close();
		verify(connection).close();
	}

	@Test
	public void testUpdateWithTypedParameters() throws SQLException {
		given(preparedStatement.executeUpdate()).willReturn(1);

		params.put("perfId", new SqlParameterValue(Types.DECIMAL, 1));
		params.put("priceId", new SqlParameterValue(Types.INTEGER, 1));
		int rowsAffected = namedParameterTemplate.update(UPDATE_NAMED_PARAMETERS, params);

		assertEquals(1, rowsAffected);
		verify(connection).prepareStatement(UPDATE_NAMED_PARAMETERS_PARSED);
		verify(preparedStatement).setObject(1, 1, Types.DECIMAL);
		verify(preparedStatement).setObject(2, 1, Types.INTEGER);
		verify(preparedStatement).close();
		verify(connection).close();
	}

	@Test
	public void testBatchUpdateWithPlainMap() throws Exception {
		@SuppressWarnings("unchecked")
		final Map<String, Integer>[] ids = new Map[2];
		ids[0] = Collections.singletonMap("id", 100);
		ids[1] = Collections.singletonMap("id", 200);
		final int[] rowsAffected = new int[] { 1, 2 };

		given(preparedStatement.executeBatch()).willReturn(rowsAffected);
		given(connection.getMetaData()).willReturn(databaseMetaData);

		JdbcTemplate template = new JdbcTemplate(dataSource, false);
		namedParameterTemplate = new NamedParameterJdbcTemplate(template);
		int[] actualRowsAffected = namedParameterTemplate.batchUpdate("UPDATE NOSUCHTABLE SET DATE_DISPATCHED = SYSDATE WHERE ID = :id", ids);

		assertTrue("executed 2 updates", actualRowsAffected.length == 2);
		assertEquals(rowsAffected[0], actualRowsAffected[0]);
		assertEquals(rowsAffected[1], actualRowsAffected[1]);
		verify(connection).prepareStatement("UPDATE NOSUCHTABLE SET DATE_DISPATCHED = SYSDATE WHERE ID = ?");
		verify(preparedStatement).setObject(1, 100);
		verify(preparedStatement).setObject(1, 200);
		verify(preparedStatement, times(2)).addBatch();
		verify(preparedStatement, atLeastOnce()).close();
		verify(connection, atLeastOnce()).close();
	}

	@Test
	public void testBatchUpdateWithSqlParameterSource() throws Exception {
		SqlParameterSource[] ids = new SqlParameterSource[2];
		ids[0] = new MapSqlParameterSource("id", 100);
		ids[1] = new MapSqlParameterSource("id", 200);
		final int[] rowsAffected = new int[] { 1, 2 };

		given(preparedStatement.executeBatch()).willReturn(rowsAffected);
		given(connection.getMetaData()).willReturn(databaseMetaData);

		JdbcTemplate template = new JdbcTemplate(dataSource, false);
		namedParameterTemplate = new NamedParameterJdbcTemplate(template);
		int[] actualRowsAffected = namedParameterTemplate.batchUpdate("UPDATE NOSUCHTABLE SET DATE_DISPATCHED = SYSDATE WHERE ID = :id", ids);

		assertTrue("executed 2 updates", actualRowsAffected.length == 2);
		assertEquals(rowsAffected[0], actualRowsAffected[0]);
		assertEquals(rowsAffected[1], actualRowsAffected[1]);
		verify(connection).prepareStatement("UPDATE NOSUCHTABLE SET DATE_DISPATCHED = SYSDATE WHERE ID = ?");
		verify(preparedStatement).setObject(1, 100);
		verify(preparedStatement).setObject(1, 200);
		verify(preparedStatement, times(2)).addBatch();
		verify(preparedStatement, atLeastOnce()).close();
		verify(connection, atLeastOnce()).close();
	}

	@Test
	public void testBatchUpdateWithSqlParameterSourcePlusTypeInfo() throws Exception {
		SqlParameterSource[] ids = new SqlParameterSource[2];
		ids[0] = new MapSqlParameterSource().addValue("id", 100, Types.NUMERIC);
		ids[1] = new MapSqlParameterSource().addValue("id", 200, Types.NUMERIC);
		final int[] rowsAffected = new int[] { 1, 2 };

		given(preparedStatement.executeBatch()).willReturn(rowsAffected);
		given(connection.getMetaData()).willReturn(databaseMetaData);

		JdbcTemplate template = new JdbcTemplate(dataSource, false);
		namedParameterTemplate = new NamedParameterJdbcTemplate(template);
		int[] actualRowsAffected = namedParameterTemplate.batchUpdate("UPDATE NOSUCHTABLE SET DATE_DISPATCHED = SYSDATE WHERE ID = :id", ids);

		assertTrue("executed 2 updates", actualRowsAffected.length == 2);
		assertEquals(rowsAffected[0], actualRowsAffected[0]);
		assertEquals(rowsAffected[1], actualRowsAffected[1]);
		verify(connection).prepareStatement("UPDATE NOSUCHTABLE SET DATE_DISPATCHED = SYSDATE WHERE ID = ?");
		verify(preparedStatement).setObject(1, 100, Types.NUMERIC);
		verify(preparedStatement).setObject(1, 200, Types.NUMERIC);
		verify(preparedStatement, times(2)).addBatch();
		verify(preparedStatement, atLeastOnce()).close();
		verify(connection, atLeastOnce()).close();
	}

}
