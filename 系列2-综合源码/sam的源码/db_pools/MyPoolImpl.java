package db_pools;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

public class MyPoolImpl implements IMyPool {
	// 源代码当中所以参数属性都是对外的配置属性
	private static String driver = null;
	private static String url = null;
	private static String user = null;
	private static String password = null;
	// 限制连接池中的管道数量参数
	private static int initCount = 3;
	private static int stepSize = 10;
	private static int poolMaxSize = 150;
	// 线程安全的集合 用来放我们的数据库链接管道
	private static Vector<PooledConnection> pooledConnections = new Vector<PooledConnection>();

	public MyPoolImpl() {
		init();
	}

	private void init() {
		InputStream inStream = MyPoolImpl.class.getClassLoader().getResourceAsStream("jdbcPool.properties");
		Properties pro = new Properties();
		try {
			// 数据加载是否有信息进来
			pro.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		driver = pro.getProperty("jdbcDriver");
		url = pro.getProperty("jdbcurl");
		user = pro.getProperty("userName");
		password = pro.getProperty("password");
		// 对字节信息进行判断
		if (Integer.valueOf(pro.getProperty("initCount")) > 0) {
			//initCount = Integer.valueOf(pro.getProperty("initCount"));
		} else if (Integer.valueOf(pro.getProperty("stepSize")) > 0) {
			//stepSize = Integer.valueOf(pro.getProperty("stepSize"));
		} else if (Integer.valueOf(pro.getProperty("poolMaxSize")) > 0) {
			poolMaxSize = Integer.valueOf(pro.getProperty("poolMaxSize"));
		}
		// 准备创建什么类型管道 driver
		try {
			Driver dbDriver = (Driver) Class.forName(driver).newInstance();
			DriverManager.registerDriver(dbDriver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 创建管道
		// DriverManager.getConnection(url, user, password);
		//createConnections(initCount);
	}

	@Override
	public PooledConnection getConnection() {
		if (pooledConnections.size() == 0) {

			System.out.println("获取数据库连接管道失败，没有任何管道！");
			// Spring :手动刷一把
			createConnections(initCount);
		}
		// 审核：是否没有被占有 且是否有效
		PooledConnection connection = getRealConnection();
		while (connection == null) {
			createConnections(stepSize);

			connection = getRealConnection();
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return connection;
	}

	private synchronized PooledConnection getRealConnection() {
		for(PooledConnection conn:pooledConnections){
			if(!conn.isBusy()){
				//java.sql.Connection
				Connection connection =conn.getConnection();
				try {
					//发送一个指令给数据库 看是否收到回应
					if(!connection.isValid(2000)){
						connection=DriverManager.getConnection(url, user, password);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//其他线程没办法在拿他
				conn.setBusy(true);
				return conn;
			}
		}
		return null;
	}

	@Override
	public void createConnections(int count) {
		if (pooledConnections.size() + count <= poolMaxSize) {
			for (int i = 0; i < count; i++) {
				try {
					Connection connection = DriverManager.getConnection(url, user, password);
					PooledConnection pooledConnection = new PooledConnection(connection, false);
					pooledConnections.add(pooledConnection);
					System.out.println("初始化" + (i + 1) + "个管道！");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("创建失败，创建参数非法！");
		}

	}

}
