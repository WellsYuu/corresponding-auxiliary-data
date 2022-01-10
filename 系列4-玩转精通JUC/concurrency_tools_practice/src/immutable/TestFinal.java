package immutable;

/**
 * 描述：     测试final能否被修改
 */
public class TestFinal {

    String test;

    public static void main(String[] args) {
        final Person person = new Person();
        person.testFinal.test = "13g";
        System.out.println(person.testFinal.test);
        person.testFinal.test = "15g";
        System.out.println(person.testFinal.test);

//        String abc = "abc";
//        System.out.println(abc.substring(1, 2));
//        System.out.println(abc);
//        person.bag = "book";
//        person = new Person();
//        int age = person.age;
//        String name = person.name;

    }
}
