/*
 * Copyright 2002-2012 the original author or authors.
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

package org.springframework.jdbc.core.metadata;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.support.DatabaseMetaDataCallback;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;

/**
 * Factory used to create a {@link CallMetaDataProvider} implementation based on the type of databse being used.
 *
 * @author Thomas Risberg
 * @since 2.5
 */
public class CallMetaDataProviderFactory {

	/** Logger */
	private static final Log logger = LogFactory.getLog(CallMetaDataProviderFactory.class);

	/** List of supported database products for procedure calls */
	public static final List<String> supportedDatabaseProductsForProcedures = Arrays.asList(
			"Apache Derby",
			"DB2",
			"MySQL",
			"Microsoft SQL Server",
			"Oracle",
			"PostgreSQL",
			"Sybase"
		);
	/** List of supported database products for function calls */
	public static final List<String> supportedDatabaseProductsForFunctions = Arrays.asList(
			"MySQL",
			"Microsoft SQL Server",
			"Oracle",
			"PostgreSQL"
		);

	/**
	 * Create a CallMetaDataProvider based on the database metedata
	 * @param dataSource used to retrieve metedata
	 * @param context the class that holds configuration and metedata
	 * @return instance of the CallMetaDataProvider implementation to be used
	 */
	static public CallMetaDataProvider createMetaDataProvider(DataSource dataSource, final CallMetaDataContext context) {
		try {
			return (CallMetaDataProvider) JdbcUtils.extractDatabaseMetaData(dataSource, new DatabaseMetaDataCallback() {
				public Object processMetaData(DatabaseMetaData databaseMetaData) throws SQLException, MetaDataAccessException {
					String databaseProductName = JdbcUtils.commonDatabaseName(databaseMetaData.getDatabaseProductName());
					boolean accessProcedureColumnMetaData = context.isAccessCallParameterMetaData();
					if (context.isFunction()) {
						if (!supportedDatabaseProductsForFunctions.contains(databaseProductName)) {
							if (logger.isWarnEnabled()) {
								logger.warn(databaseProductName + " is not one of the databases fully supported for function calls " +
										"-- supported are: " + supportedDatabaseProductsForFunctions);
							}
							if (accessProcedureColumnMetaData) {
								logger.warn("Metadata processing disabled - you must specify all parameters explicitly");
								accessProcedureColumnMetaData = false;
							}
						}
					}
					else {
						if (!supportedDatabaseProductsForProcedures.contains(databaseProductName)) {
							if (logger.isWarnEnabled()) {
								logger.warn(databaseProductName + " is not one of the databases fully supported for procedure calls " +
										"-- supported are: " + supportedDatabaseProductsForProcedures);
							}
							if (accessProcedureColumnMetaData) {
								logger.warn("Metadata processing disabled - you must specify all parameters explicitly");
								accessProcedureColumnMetaData = false;
							}
						}
					}

					CallMetaDataProvider provider;
					if ("Oracle".equals(databaseProductName)) {
						provider = new OracleCallMetaDataProvider(databaseMetaData);
					}
					else if ("DB2".equals(databaseProductName)) {
						provider = new Db2CallMetaDataProvider((databaseMetaData));
					}
					else if ("Apache Derby".equals(databaseProductName)) {
						provider = new DerbyCallMetaDataProvider((databaseMetaData));
					}
					else if ("PostgreSQL".equals(databaseProductName)) {
						provider = new PostgresCallMetaDataProvider((databaseMetaData));
					}
					else if ("Sybase".equals(databaseProductName)) {
						provider = new SybaseCallMetaDataProvider((databaseMetaData));
					}
					else if ("Microsoft SQL Server".equals(databaseProductName)) {
						provider = new SqlServerCallMetaDataProvider((databaseMetaData));
					}
					else {
						provider = new GenericCallMetaDataProvider(databaseMetaData);
					}
					if (logger.isDebugEnabled()) {
						logger.debug("Using " + provider.getClass().getName());
					}
					provider.initializeWithMetaData(databaseMetaData);
					if (accessProcedureColumnMetaData) {
						provider.initializeWithProcedureColumnMetaData(
								databaseMetaData, context.getCatalogName(), context.getSchemaName(), context.getProcedureName());
					}
					return provider;
				}
			});
		}
		catch (MetaDataAccessException ex) {
			throw new DataAccessResourceFailureException("Error retreiving database metadata", ex);
		}

	}

}
