package lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 变量引用
 */
public class VarDemo {

	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		Consumer<String> consumer = s -> System.out.println(s + list);
		consumer.accept("1211");
	}

}
