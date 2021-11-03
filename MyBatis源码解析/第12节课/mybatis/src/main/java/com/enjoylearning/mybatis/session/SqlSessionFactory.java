package com.enjoylearning.mybatis.session;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.enjoylearning.mybatis.config.Configuration;
import com.enjoylearning.mybatis.config.MappedStatement;

public class SqlSessionFactory {
	//配置对象全局唯一 加载数据库信息和mapper文件信息
	private Configuration conf = new Configuration();

	public SqlSessionFactory() {
		//加载数据库信息
		 loadDbInfo();
		 //加载mapper文件信息
		 loadMappersInfo();
	}
	
	
	 public SqlSession openSession(){
		 SqlSession sqlSession  = new DefaultSqlSession(conf);
		 return sqlSession;
	 }

	private void loadMappersInfo() {
		URL resources =null;
		resources = SqlSessionFactory.class.getClassLoader().getResource(conf.MAPPER_CONFIG_LOCATION);
		File mappers = new File(resources.getFile());
		if(mappers.isDirectory()){
			File[] listFiles = mappers.listFiles();
			for (File file : listFiles) {
				loadMapperInfo(file);
			}
		}
	}

	private void loadMapperInfo(File file) {
		// 创建saxReader对象  
		SAXReader reader = new SAXReader();  
		// 通过read方法读取一个文件 转换成Document对象  
		Document document=null;
		try {
			document = reader.read(file);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		//获取根节点元素对象  
		Element node = document.getRootElement();
		//获取命名空间
		String namespace = node.attribute("namespace").getData().toString();
		//获取select子节点列表
		List<Element> selects = node.elements("select");
		for (Element element : selects) {//遍历select节点，将信息记录到MappedStatement对象，并登记到configuration对象中
			MappedStatement mappedStatement = new MappedStatement();
			String id = element.attribute("id").getData().toString();
			String resultType = element.attribute("resultType").getData().toString();
			String sql = element.getData().toString();
			String sourceId = namespace+"."+id;
			mappedStatement.setSourceId(sourceId);
			mappedStatement.setResultType(resultType);
			mappedStatement.setSql(sql);
			mappedStatement.setNamespace(namespace);
			conf.getMappedStatements().put(sourceId, mappedStatement);//登记到configuration对象中
		}
	}

	private void loadDbInfo() {
		InputStream dbIn = SqlSessionFactory.class.getClassLoader().getResourceAsStream(conf.DB_CONFIG_FILE);
		 Properties p = new Properties();
		 try {
			p.load(dbIn);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 conf.setDbDriver(p.get("jdbc.driver").toString());
		 conf.setDbPassword(p.get("jdbc.password").toString());
		 conf.setDbUrl(p.get("jdbc.url").toString());
		 conf.setDbUserName(p.get("jdbc.username").toString());
	}
	
	public static void main(String[] args) {
		SqlSessionFactory fa = new SqlSessionFactory();
		System.out.println(fa);
	}
	
	
	

}
