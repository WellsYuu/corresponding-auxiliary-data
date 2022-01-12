package lambda;

import java.util.function.Supplier;

class Log {

	/**
	 * 不打印debug级别的日志
	 * 
	 * @return
	 */
	public boolean isDebug() {
		return true;
	}

	public void debug(String string) {
		if (this.isDebug()) {
			System.out.println(string);
		}
	}

	/**
	 * 
	 * @param supplier
	 *            传入一个提供字符串的函数
	 */
	public void debug(Supplier<String> supplier) {
		if (this.isDebug()) {
			// 真正要打印的时候，才调用
			System.out.println(supplier.get());
		}
	}

}

public class LazyDemo1 {

	public static void main(String[] args) {
		LazyDemo1 demo = new LazyDemo1();
		Log log = new Log();
		log.debug(() -> "打印日志之前必须判断日志级别: " + demo.toString());
	}

	@Override
	public String toString() {
		System.out.println("toString 被调用了");
		return super.toString();
	}

}
