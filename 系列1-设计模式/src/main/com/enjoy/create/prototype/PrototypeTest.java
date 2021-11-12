package com.enjoy.create.prototype;

/**
 * 原型创建模式测试
 */
public class PrototypeTest {
    public static void main(String[] args) {
        Sheep old = new Sheep(2, "雄性", new Admin(25, "女"));
        System.out.println(old.toString());
        Sheep current = old.clone();
        System.out.println(current.toString());

        //对克隆羊做处理
        current.setAge(1);
        current.setSex("雌性");
        current.getAdmin().setAge(34);
        current.getAdmin().setSex("男");
        System.out.println(old.toString());
        System.out.println(current.toString());
    }

}
