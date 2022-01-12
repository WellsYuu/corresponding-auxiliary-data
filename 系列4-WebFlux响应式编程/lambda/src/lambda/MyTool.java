package lambda;

/**
 * 使用jdk 8 接口static方法来创建工具类
 * @author 晓风轻
 *
 */
public interface MyTool {
	public static int add(int x, int y) {
		return x + y;
	}
}
