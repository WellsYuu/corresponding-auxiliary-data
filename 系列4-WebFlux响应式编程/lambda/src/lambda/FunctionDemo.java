package lambda;

import java.util.function.Consumer;
import java.util.function.IntPredicate;

public class FunctionDemo {

	
	public static void main(String[] args) {
		// 断言函数接口
		IntPredicate predicate = i -> i > 0;
		System.out.println(predicate.test(-9));
		
		//
		// IntConsumer

		// 消费函数接口
		Consumer<String> consumer = s -> System.out.println(s);
		consumer.accept("输入的数据");
	}

}
