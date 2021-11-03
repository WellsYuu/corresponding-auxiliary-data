package com.enjoylearning.mybatis.build;

import java.math.BigDecimal;
import java.util.Date;

//Fluent编程风格 ，比如：zookeeper的Curator


/*
 * 
 * Random random = new Random();
random.ints().limit(10).forEach(System.out::println);

*/
public class Director {
	
	public static void main(String[] args) {
		RedPacket redPacket = RedPacketBuilderImpl.getBulider().setPublisherName("lison")
				                                               .setAcceptName("vip群")
                                                               .setPacketAmount(new BigDecimal("888"))
                                                               .setPacketType(1)
                                                               .setOpenPacketTime(new Date())
                                                               .setPulishPacketTime(new Date()).build();

		System.out.println(redPacket);
	}

}
