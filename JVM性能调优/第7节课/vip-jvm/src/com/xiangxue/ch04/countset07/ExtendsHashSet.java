package com.xiangxue.ch04.countset07;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：统计增加的元素个数，继承
 */
public class ExtendsHashSet<E> extends HashSet<E> {
	private int addCount = 0;//计数器

	public ExtendsHashSet() {
	}

	@Override
	public boolean add(E e) {
		addCount++;
		return super.add(e);
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		addCount = addCount+c.size();
		return super.addAll(c);
	}

	public int getAddCount() {
		return addCount;
	}

	public static void main(String[] args) {
		ExtendsHashSet<String> s = new ExtendsHashSet<String>();
		s.addAll(Arrays.asList("Mark","James","Lison"));
		System.out.println(s.getAddCount());
	}
}
