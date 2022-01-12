package stream;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.collections4.MapUtils;

/**
 * 学生 对象
 */
class Student {
	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 年龄
	 */
	private int age;

	/**
	 * 性别
	 */
	private Gender gender;

	/**
	 * 班级
	 */
	private Grade grade;

	public Student(String name, int age, Gender gender, Grade grade) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.grade = grade;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "[name=" + name + ", age=" + age + ", gender=" + gender
				+ ", grade=" + grade + "]";
	}

}

/**
 * 性别
 */
enum Gender {
	MALE, FEMALE
}

/**
 * 班级
 */
enum Grade {
	ONE, TWO, THREE, FOUR;
}

public class CollectDemo {

	public static void main(String[] args) {
		// 测试数据
		List<Student> students = Arrays.asList(
				new Student("小明", 10, Gender.MALE, Grade.ONE),
				new Student("大明", 9, Gender.MALE, Grade.THREE),
				new Student("小白", 8, Gender.FEMALE, Grade.TWO),
				new Student("小黑", 13, Gender.FEMALE, Grade.FOUR),
				new Student("小红", 7, Gender.FEMALE, Grade.THREE),
				new Student("小黄", 13, Gender.MALE, Grade.ONE),
				new Student("小青", 13, Gender.FEMALE, Grade.THREE),
				new Student("小紫", 9, Gender.FEMALE, Grade.TWO),
				new Student("小王", 6, Gender.MALE, Grade.ONE),
				new Student("小李", 6, Gender.MALE, Grade.ONE),
				new Student("小马", 14, Gender.FEMALE, Grade.FOUR),
				new Student("小刘", 13, Gender.MALE, Grade.FOUR));

		// 得到所有学生的年龄列表
		// s -> s.getAge() --> Student::getAge , 不会多生成一个类似 lambda$0这样的函数
		Set<Integer> ages = students.stream().map(Student::getAge)
				.collect(Collectors.toCollection(TreeSet::new));
		System.out.println("所有学生的年龄:" + ages);

		// 统计汇总信息
		IntSummaryStatistics agesSummaryStatistics = students.stream()
				.collect(Collectors.summarizingInt(Student::getAge));
		System.out.println("年龄汇总信息:" + agesSummaryStatistics);

		// 分块
		Map<Boolean, List<Student>> genders = students.stream().collect(
				Collectors.partitioningBy(s -> s.getGender() == Gender.MALE));
		// System.out.println("男女学生列表:" + genders);
		MapUtils.verbosePrint(System.out, "男女学生列表", genders);

		// 分组
		Map<Grade, List<Student>> grades = students.stream()
				.collect(Collectors.groupingBy(Student::getGrade));
		MapUtils.verbosePrint(System.out, "学生班级列表", grades);

		// 得到所有班级学生的个数
		Map<Grade, Long> gradesCount = students.stream().collect(Collectors
				.groupingBy(Student::getGrade, Collectors.counting()));
		MapUtils.verbosePrint(System.out, "班级学生个数列表", gradesCount);

	}

}
