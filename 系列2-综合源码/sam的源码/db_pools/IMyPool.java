package db_pools;

/**
 * 面向接口编程 抽取连接池架构的接口
 * PooledConnection：可复用类型的管道
 * @author Sam
 * 
 */
public interface IMyPool {
	// 对外 提供链接管道
	PooledConnection getConnection();

	// 对内 创建连接
	void createConnections(int count);
}