package db_pools;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 检验产品是否成功
 * 
 * @author Sam
 *
 */
public class MypoolTest {
	// PoolManger拿到连接池对象
	private static MyPoolImpl poolImpl = PoolManager.getInstace();

	/**
	 * 单个线程使用连接池对象做查询业务
	 * 
	 * @param args
	 */
	public synchronized static void selctData() {
	PooledConnection connection = poolImpl.getConnection();
		ResultSet rs = connection.queryBysql("SELECT * FROM items");
		System.out.println("线程名称： " + Thread.currentThread().getName());
		try {
			while (rs.next()) {
				System.out.print(rs.getString("ID") + "\t\t");
				System.out.print(rs.getString("NAME") + "\t\t");
				System.out.print(rs.getString("PRICE") + "\t\t");
				System.out.println();
			}
			rs.close();
			// 业务做完之后 我们就释放 回连接池
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// 玩的大 new 1550 客户端 jar 当然比 国内知名德鲁伊 团队
		for (int i = 0; i < 1500; i++) {
			new Thread(new Runnable() {
				public void run() {
					selctData();
				}
			}).start();
		}

	}
}
