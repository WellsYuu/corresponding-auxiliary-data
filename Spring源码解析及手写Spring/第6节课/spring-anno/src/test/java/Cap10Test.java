import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.enjoy.cap10.aop.Calculator;
import com.enjoy.cap10.config.Cap10MainConfig;
import com.enjoy.cap7.config.Cap7MainConfigOfLifeCycle;
import com.enjoy.cap8.bean.Bird;
import com.enjoy.cap8.config.Cap8MainConfig;
import com.enjoy.cap9.bean.Moon;
import com.enjoy.cap9.bean.Sun;
import com.enjoy.cap9.config.Cap9MainConfig;
import com.enjoy.cap9.dao.TestDao;
import com.enjoy.cap9.service.TestService;

public class Cap10Test {
	@Test
	public void test01(){
		AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Cap10MainConfig.class);
		
		//Calculator c = new Calculator();
		Calculator c = app.getBean(Calculator.class);
		int result = c.div(4, 2);
		System.out.println(result);
		app.close();
	
		
	}
}
