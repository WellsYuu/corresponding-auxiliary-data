package db_pools;

/**
 * 线程池对象调用的封装
 * @author sam
 *
 */
public class PoolManager {
	private static class createPool {
		private static MyPoolImpl poolImpl = new MyPoolImpl();
	}

	/**
	 * 内部类单利模式 仿造类加载原理完美实现线程安全问题
	 * 
	 * @return
	 */
	public static MyPoolImpl getInstace() {
		return createPool.poolImpl;
	}
}
