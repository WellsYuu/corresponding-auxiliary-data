import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.enjoy.cap7.config.Cap7MainConfigOfLifeCycle;

public class Cap7Test {
	@Test
	public void test01(){
		AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Cap7MainConfigOfLifeCycle.class);
		
		System.out.println("IOC�����������........");
		
		app.close();
		
		
		
		
		
	}
}
