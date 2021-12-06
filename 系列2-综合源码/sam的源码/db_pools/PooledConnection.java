package db_pools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * PooledConnection：自定义连接数据库管道对象Bean 重点：原生的Connection上面没有复用的标志
 * Sam老司机自己封装设计isBusy来解决线程被占用的问题 且解决线程使用该管道完成任务后的释放标志：isBusy 总结：这个思想就封装思想
 * 也就是扩展功能
 * 
 * @author Sam
 *
 */
public class PooledConnection {
	// 表示繁忙的标志 复用的标志 线程安全
	private boolean isBusy = false;
	// java.sql.Connection：真正数据库连接管道
	private Connection connection;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * 设置标志我们的管道是否被占用
	 * 
	 * @return
	 */
	public boolean isBusy() {
		return isBusy;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	// 构造方法 其他的程序调用它的时候 是要创建并且给它初始化组件
	public PooledConnection(Connection connection, boolean isBusy) {
		this.connection = connection;
		this.isBusy = isBusy;
	}

	// 这个功能也是自定义添加 这个释放说为管道复用来服务
	public void close() {
		this.isBusy = false;
	}

	// 给我们自定义的管道一个操作数据库数据功能
	public ResultSet queryBysql(String sql) {
		Statement sm = null;
		ResultSet rs = null;
		try {
			sm = connection.createStatement();
			rs = sm.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

}
