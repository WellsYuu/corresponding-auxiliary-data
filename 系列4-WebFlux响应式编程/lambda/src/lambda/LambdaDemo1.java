package lambda;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.function.Function;

@FunctionalInterface
interface Interface1 {
	int doubleNum(int i);

	default int add(int x, int y) {
		return x + y;
	}

	static int sub(int x, int y) {
		return x - y;
	}
}

@FunctionalInterface
interface Interface2 {
	int doubleNum(int i);

	default int add(int x, int y) {
		return x + y;
	}
}

@FunctionalInterface
interface Interface3 extends Interface2, Interface1 {

	@Override
	default int add(int x, int y) {
		return Interface1.super.add(x, y);
	}

}

public strictfp class LambdaDemo1 {

	public static void main(String[] args) {
		Interface1 i1 = (i) -> i * 2;

		Interface1.sub(10, 3);
		System.out.println(i1.add(3, 7));
		System.out.println(i1.doubleNum(20));

		// 这种是最常见写法
		Interface1 i2 = i -> i * 2;

		Interface1 i3 = (int i) -> i * 2;

		Interface1 i4 = (int i) -> {
			System.out.println("-----");
			return i * 2;
		};

	}

}
