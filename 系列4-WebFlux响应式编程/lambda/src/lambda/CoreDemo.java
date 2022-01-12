package lambda;

import java.util.function.Consumer;
/**
 * lambda底层实现原理
 * 
 * 1. 编译器会为每一个lambda表达式生成一个方法
 * 		方法名是lambda$0,1,2,3，但方法引用的表达式不会生成方法。
 * 2. 在lambda地方会产生一个invokeDynamic指令，这个指令会调用
 * 		bootstrap（引导）方法，bootstrap方法会指向自动生成的lambda$0
 * 		方法或者方法引用的方法。
 * 3. bootstrap方法使用上是调用了LambdaMetafactory.metafactory静态方法
 * 		该方法返回了CallSite(调用站点)，里面包含了MethodHandle（方法句柄）
 * 		也就是最终调用的方法。
 * 4. 引导方法只会调用一次。
 * 
 * 自动生成的方法：
 * 1. 输入和输出和lambda一致
 * 2. 如果没有使用this，那么就是static方法，否则就是成员方法
 *
 */
public class CoreDemo {

	public static void main(String[] args) {
		Consumer<String> consumer = s-> System.out.println(s);
		consumer.accept("222");
		System.out.println(consumer.getClass());
		
		CoreDemo demo = new CoreDemo();
		
		demo.test2();
		// demo.test2();
	}
	
	public void test2() {
		Consumer<Integer> consumer = s-> {
			System.out.println(this);
			System.out.println(s);			
		};
		//Consumer<String> consumer = System.out::println;
		consumer.accept(2222);
		System.out.println(consumer.getClass());
	}
	
//	public void lambda$0(String string) {
//		
//	}
}
