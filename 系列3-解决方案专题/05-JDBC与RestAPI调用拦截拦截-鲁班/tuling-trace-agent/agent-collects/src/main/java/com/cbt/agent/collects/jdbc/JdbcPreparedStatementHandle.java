package com.cbt.agent.collects.jdbc;

import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.internal.Row;
import com.cbt.agent.common.logger.Logger;
import com.cbt.agent.common.logger.LoggerFactory;
import com.cbt.agent.trace.TraceContext;
import com.cbt.agent.trace.TraceNode;
import com.cbt.agent.trace.TraceSession;
import com.cbt.agent.collect.*;

import javax.sql.rowset.CachedRowSet;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * 
 * Description: jdbc 预处理对象切入<br/>
 * 
 * @author zengguangwei@cbt.com
 * @date: 2016年6月6日 下午8:15:29
 * @version 1.0
 * @since JDK 1.7
 */
public class JdbcPreparedStatementHandle implements CollectHandle {
	private static final Logger logger = LoggerFactory
			.getLogger(JdbcPreparedStatementHandle.class);

	@Override
	public Event invokerBefore(Event event, InParams in) {
		return null;
	}

	// OutResult 必须带上参数
	@Override
	public void invokerAfter(Event event, OutResult out) {
		// JdbcEvent event = (JdbcEvent) out.others.get("JdbcEvent");
		if (TraceContext.getDefault().getCurrentSession() == null) {
			return;
		} else if (event == null || !event.isActive()) {
			return;
		}

		String methodName = out.methodName;
		if (methodName.startsWith("set") && out.args.length > 1) {
			doSetParamAfter(event, out);
		} else if (methodName.equals("executeQuery")) {
			doExecuteQueryAfter(event, out);
		} else if (methodName.equals("executeUpdate")) {
			doExecuteUpdateAfter(event, out);
		} else if (methodName.equals("execute")) {
			doExecuteAfter(event, out);
		} else if (methodName.equals("close")) {
			doCloseAfter(event, out);
		} else if (methodName.equals("getResultSet")) {
			doGetResultSet(event, out);
		}
	}

	private void doGetResultSet(Event event, OutResult out) {
		Object[] eventData = (Object[]) event.getData();
		JdbcData data = (JdbcData) eventData[0];
		try {
			if (out.error != null) {
				data.setError(out.error);
			} else if (out.result != null) {
				ResultSet resultSet = (ResultSet) out.result;
				data.setResultSet(copyResult(resultSet));
				data.setResultSize((resultSet).getFetchSize());
			}
			// data.setExecuteType("Query");
		} catch (SQLException e) {
			logger.error("get ResultSet size fail", e);
		} finally {
			try {
				((ResultSet) out.result).beforeFirst();
			} catch (SQLException e) {
				logger.error("get ResultSet size fail", e);
			}
		}
	}

	private void doExecuteAfter(Event event, OutResult out) {
		Object[] eventData = (Object[]) event.getData();
		JdbcData data = (JdbcData) eventData[0];
		if (out.error != null)
			data.setError(out.error);
		data.setExecuteType("Execute");
	}

	private void doExecuteUpdateAfter(Event event, OutResult out) {
		Object[] eventData = (Object[]) event.getData();
		JdbcData data = (JdbcData) eventData[0];
		if (out.error != null)
			data.setError(out.error);
		data.setExecuteType("Update");
	}

	private void doExecuteQueryAfter(Event event, OutResult out) {
		Object[] eventData = (Object[]) event.getData();
		JdbcData data = (JdbcData) eventData[0];
		try {
			if (out.error != null) {
				data.setError(out.error);
			} else if (out.result != null) {
				ResultSet resultSet = (ResultSet) out.result;
				data.setResultSet(copyResult(resultSet));
				data.setResultSize((resultSet).getFetchSize());
			}
			data.setExecuteType("Query");
		} catch (SQLException e) {
			logger.error("get ResultSet size fail", e);
			// data.setError(e);
		} finally {
			try {
			    if(out.result!=null){
				((ResultSet) out.result).beforeFirst();
			    }
			} catch (SQLException e) {
				logger.error("get ResultSet size fail", e);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "restriction", "resource" })
	public static Object[][] copyResult(ResultSet resultSet)
			throws SQLException {
		Object[][] result = null;
		CachedRowSet rowset = new CachedRowSetImpl();
		rowset.setMaxRows(50);
		rowset.setPageSize(50);
		rowset.populate(resultSet, 1);
		Row[] rows = rowset.toCollection().toArray(new Row[rowset.size()]);
		
		// 获取表头
		int columnCount = rowset.getMetaData().getColumnCount();
		String[] columnNames = new String[columnCount];
		for (int i = 0; i < columnCount; i++) {
			columnNames[i] = rowset.getMetaData().getColumnLabel(i + 1);
		}
		result = new Object[rows.length + 1][columnCount];
		result[0] = columnNames;
		
		// 获取内容，勿略二进制，大字符串进行截断
		for (int i=0;i<rows.length;i++) {
			for(int k=0;k<columnCount;k++){
				int type = rowset.getMetaData().getColumnType(k+1);
				Object content=rows[i].getOrigRow()[k];
				if (type == Types.BINARY || type == Types.VARBINARY
						|| type == Types.LONGVARBINARY) {
					result[i + 1][k] = "$!nonsupport binary data";
				} else if (content instanceof String) {
					int maxSize = Math.min(((String) content).length(), 2048);
					result[i + 1][k] = ((String) content).substring(0, maxSize);
				} else if (content instanceof Serializable) {
					result[i + 1][k] = content;
				}
			}
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "unchecked" })
	private void doSetParamAfter(Event event, OutResult out) {
		if (event == null || !event.isActive())
			return;

		Object[] eventData = (Object[]) event.getData();
		JdbcData data = (JdbcData) eventData[0];
		if (out.error != null) {
			data.setError(out.error);
		} else if (out.args[1] instanceof StringReader) {
			StringReader sreader = (StringReader) out.args[1];
			char[] cbuf = new char[4096]; // 超过4096 直接截断
			try {
				sreader.reset();
				int size = sreader.read(cbuf);
				if (size > 0) {
					data.getParams()
							.add(new String(cbuf, 0, size)
									+ (size == 4096 ? "        ...Content Too big be truncation"
											: ""));
				} else {
					data.getParams().add(new String(""));
				}
			} catch (IOException e) {
				logger.error("jdbc 大字符流解析异常", e);
				data.getParams().add(new String("!Collect error"));
			}
		} else if (out.args[1] instanceof Serializable) {
			data.getParams().add(out.args[1]);
		} else {
			data.getParams().add(
					"!Does not support type。method=" + out.methodName);
		}
	}

	// 结束该jdbc执行事件 生成TraceNode
	private void doCloseAfter(Event event, OutResult out) {
		Object[] eventData = (Object[]) event.getData();
		JdbcData data = (JdbcData) eventData[0];
		TraceNode node = (TraceNode) eventData[1];
		if (out.error != null)
			data.setError(out.error);
		node = JdbcData.TraceNodeBuilder.builderNode(data, node);
		node.setEndTime(System.currentTimeMillis());
		TraceSession session = TraceContext.getDefault().getCurrentSession();
		session.addNode(node);
	}

	@Override
	public EventType getEventType() {
		return EventType.JDBC;
	}

	public static void main(String[] args) throws IOException {
		StringReader reader = new StringReader("123456789");
		reader.read(new char[1024]);
		reader.reset();
		reader.read(new char[1024]);
	}

}
