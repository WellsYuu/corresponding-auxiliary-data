package stream;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo3 {

	public static void main(String[] args) {
		String str = "my name is 007";

		// ��ÿ�����ʵĳ��ȵ��ó���
		Stream.of(str.split(" ")).filter(s -> s.length() > 2)
				.map(s -> s.length()).forEach(System.out::println);

		// flatMap A->B����(�Ǹ�����), ���յõ����е�AԪ�����������B���Լ���
		// intStream/longStream ������Stream������, ����Ҫ����װ�� boxed
		Stream.of(str.split(" ")).flatMap(s -> s.chars().boxed())
				.forEach(i -> System.out.println((char) i.intValue()));

		// peek ����debug. �Ǹ��м����,�� forEach ����ֹ����
		System.out.println("--------------peek------------");
		Stream.of(str.split(" ")).peek(System.out::println)
				.forEach(System.out::println);

		// limit ʹ��, ��Ҫ����������
		new Random().ints().filter(i -> i > 100 && i < 1000).limit(10)
				.forEach(System.out::println);

	}

}
