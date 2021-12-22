/**
 * 
 */
package com.tl.jvm.demo3;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 张飞老师
 */
public class OOMTest {

	public static void main(String[] args) {
		try {
			List<byte[]> list = new ArrayList<>();
			for (int i = 0; i < 2048; i++) {
				list.add(new byte[1*1024*1024]);// 1M
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
