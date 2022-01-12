package lambda;

@FunctionalInterface
interface IMath {
	int add(int x, int y);
}

@FunctionalInterface
interface IMath2 {
	int sub(int x, int y);
}


public class TypeDemo {

	public static void main(String[] args) {
		// 变量类型定义
		IMath lambda = (x, y) -> x + y;

		// 数组里
		IMath[] lambdas = { (x, y) -> x + y };

		// 强转
		Object lambda2 = (IMath) (x, y) -> x + y;
		
		// 通过返回类型
		IMath createLambda = createLambda();
		
		TypeDemo demo = new TypeDemo();
		// 当有二义性的时候，使用强转对应的接口解决
		demo.test( (IMath2)(x, y) -> x + y);
	}
	
	public void test(IMath math) {
		
	}
	
	public void test(IMath2 math) {
		
	}
	
	public static IMath createLambda() {
		return  (x, y) -> x + y;
	}

}
