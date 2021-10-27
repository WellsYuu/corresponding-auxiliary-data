package cn.enjoyedu.ch01.buffer;

import java.nio.ByteBuffer;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 类说明：
 */
public class BufferMethod {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(20);
        System.out.println("buffer = " + buffer);
        System.out.println("--------Test reset----------");


        System.out.println("--------Test rewind--------");


        System.out.println("--------Test compact--------");


        System.out.println("------Test get-------------");


        System.out.println("--------Test put-------");

    }
}
