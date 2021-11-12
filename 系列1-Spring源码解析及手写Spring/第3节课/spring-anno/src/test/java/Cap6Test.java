import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.enjoy.cap5.config.Cap5MainConfig;
import com.enjoy.cap6.config.Cap6MainConfig;

public class Cap6Test {
	@Test
	public void test01(){
		AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Cap6MainConfig.class);
		
		System.out.println("IOC�����������........");
		
		
		Object bean1 = app.getBean("jamesFactoryBean");
		Object bean2 = app.getBean("jamesFactoryBean");//ȡMoney
		System.out.println("bean������="+bean1.getClass());
		System.out.println(bean1 == bean2);
		
		

		Object bean3 = app.getBean("&jamesFactoryBean");//ȡMoney
		System.out.println("bean������="+bean3.getClass());
//		String[] beanDefinitionNames = app.getBeanDefinitionNames();
//		for(String name:beanDefinitionNames){
//			System.out.println(name);
//		}
		
		
		
		
		
	}
}
