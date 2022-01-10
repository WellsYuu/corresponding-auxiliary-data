package immutable;

/**
 * 描述：     不可变的对象，演示其他类无法修改这个对象，public也不行
 */
public class Person {

    final int age = 18;
      String alice = new String("Alice");
    final String name = alice;
    final TestFinal testFinal = new TestFinal();
    public static void main(String[] args) {
        Person person = new Person();
        person.alice = "44";
        System.out.println(person.name);
    }
}

