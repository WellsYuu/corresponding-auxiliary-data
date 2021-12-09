package MyHashMap;

import java.util.HashMap;
//Java中的hashCode方法就是根据一定的规则将与对象相关的信息
//（比如对象的存储地址，对象的 字段等）映射成一个数值，这个数值称作为散列值
public class MyTest {
    private static class Person{
    	//身份证
        int idCard;
        //姓名
        String name;

        public Person(int idCard, String name) {
            this.idCard = idCard;
            this.name = name;
        }
        //重写hashCode 覆盖了 Object当中最笼统hashCode
    	@Override
    	public int hashCode() {
    		return idCard%10;
    	}
        @Override
        public boolean equals(Object o) {
            if (this == o && this.hashCode() == o.hashCode()) {
                return true;
            }
            if (o == null || getClass() != o.getClass()){
                return false;
            }
            Person person = (Person) o;
            //两个对象是否等值，通过idCard来确定
            return (this.hashCode() == person.hashCode()) &&(this.idCard == person.idCard);
        }

    }
    //传统的JDK对象的存储地址判断两个对象 这样统一的判断两个对象的方式不适合我们的业务需求 所以判断两个对象是否是
    //同一个对象应该要有自己的hashCode
    public static void main(String []args){
        HashMap<Person,String> map = new HashMap<Person, String>();
        Person person = new Person(1234,"Sam");
        System.out.println("equals结果："+new Person(1234,"Sammy").equals(person));
        //put到hashmap中去 key:Person  value:
        //存：hashcode（person）--》hash--》indexFor-->最终索引位置
        //map.put(person,"邪恶力量");
       //在hashcode 拿到的HashCode不一样
        //HashCode返回的就是一个对象物理值
        //取：hashcode（new Person(1234,"Sammy")）-->hash-->indexFor-->最终索引位置
        //get取出，从逻辑上讲应该能输出“邪恶力量”
        //System.out.println("结果:"+map.get(new Person(1234,"Sammy")));
    }

}