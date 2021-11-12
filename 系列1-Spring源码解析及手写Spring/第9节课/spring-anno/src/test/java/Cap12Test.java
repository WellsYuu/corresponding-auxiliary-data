
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.enjoy.cap12.Cap12MainConfig;
public class Cap12Test {
	@Test
	public void test01(){
		AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Cap12MainConfig.class);
		
		app.close();
	
		
	}
}
