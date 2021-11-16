/*
 * Copyright 2002-2008 the original author or authors.
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

package org.springframework.jdbc.core;

import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Generic utility methods for working with JDBC batch statements. Mainly for internal use
 * within the framework.
 *
 * @author Thomas Risberg
 */
public abstract class BatchUpdateUtils {

	public static int[] executeBatchUpdate(String sql, final List<Object[]> batchValues, final int[] columnTypes, JdbcOperations jdbcOperations) {
		return jdbcOperations.batchUpdate(
				sql,
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						Object[] values = batchValues.get(i);
						setStatementParameters(values, ps, columnTypes);
					}

					public int getBatchSize() {
						return batchValues.size();
					}
				});
	}

	protected static void setStatementParameters(Object[] values, PreparedStatement ps, int[] columnTypes) throws SQLException {
		int colIndex = 0;
		for (Object value : values) {
			colIndex++;
			if (value instanceof SqlParameterValue) {
				SqlParameterValue paramValue = (SqlParameterValue) value;
				StatementCreatorUtils.setParameterValue(ps, colIndex, paramValue, paramValue.getValue());
			}
			else {
				int colType;
				if (columnTypes == null || columnTypes.length < colIndex) {
					colType = SqlTypeValue.TYPE_UNKNOWN;
				}
				else {
					colType = columnTypes[colIndex - 1];
				}
				StatementCreatorUtils.setParameterValue(ps, colIndex, colType, value);
			}
		}
	}

}
