import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.enjoy.cap2.config.Cap2MainConfig;
import com.enjoy.cap3.config.Cap3MainConfig;
import com.enjoy.cap4.config.Cap4MainConfig;

public class Cap4Test {
	@Test
	public void test01(){
		AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Cap4MainConfig.class);
		
		System.out.println("IOC�����������........");
		app.getBean("person");//ִ�л�ȡ��ʱ��Ŵ�������ʼ��bean
		
	}
}
