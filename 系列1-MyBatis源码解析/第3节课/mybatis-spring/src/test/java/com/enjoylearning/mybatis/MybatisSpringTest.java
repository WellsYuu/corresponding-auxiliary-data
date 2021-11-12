package com.enjoylearning.mybatis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ResourceUtils;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:applicationContext.xml")
public class MybatisSpringTest {
	


	@Test
	public void testQuery(){
//		String email = "qq.com";
//		Byte sex = 1;
//		List<TUser> users = us.getUserByEmailAndSex(email, sex);
//		System.out.println(users.size());
	}
	
	@Test
	public void mybatisGeneratorTest() throws FileNotFoundException{
		List<String> warnings = new ArrayList<String>();  
        boolean overwrite = true;
        String genCfg = "generatorConfig.xml";  
        File configFile = new File(getClass().getClassLoader().getResource(genCfg).getFile());
        ConfigurationParser cp = new ConfigurationParser(warnings);  
        Configuration config = null;  
        try {  
            config = cp.parseConfiguration(configFile);  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (XMLParserException e) {  
            e.printStackTrace();  
        }  
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);  
        MyBatisGenerator myBatisGenerator = null;  
        try {  
            myBatisGenerator = new MyBatisGenerator(config, callback, warnings);  
        } catch (InvalidConfigurationException e) {  
            e.printStackTrace();  
        }  
        try {  
            myBatisGenerator.generate(null);  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }
	

}
