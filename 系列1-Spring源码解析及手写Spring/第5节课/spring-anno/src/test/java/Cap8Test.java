import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.enjoy.cap7.config.Cap7MainConfigOfLifeCycle;
import com.enjoy.cap8.bean.Bird;
import com.enjoy.cap8.config.Cap8MainConfig;

public class Cap8Test {
	@Test
	public void test01(){
		AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Cap8MainConfig.class);
		//从容器中获取所有bean
		String[] names = app.getBeanDefinitionNames();
		
		for(String name:names){
			System.out.println(name);
		}
		Bird bird = (Bird) app.getBean("bird");

		System.out.println(bird);
		System.out.println("IOC容器创建完成........");
		
		
		
		ConfigurableEnvironment environment = app.getEnvironment();
		System.out.println("environment===="+environment.getProperty("bird.color"));
		app.close();
		
		
		
		
		
	}
}
