package com.xiangxue.ch5;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：
 */
public class TestMap {
	public static void main(String[] args) {
		int initialCapacity = 16;
        float loadFactor=0.75f; 
        int concurrencyLevel =16;
        
        if (!(loadFactor > 0) || initialCapacity < 0 || concurrencyLevel <= 0)
            throw new IllegalArgumentException();
        if (concurrencyLevel > 65536)
            concurrencyLevel = 65536;
        // Find power-of-two sizes best matching arguments
        int sshift = 0;
        int ssize = 1;
        while (ssize < concurrencyLevel) {
            ++sshift;
            ssize <<= 1;
        }
        int segmentShift = 32 - sshift;
        int segmentMask = ssize - 1;
        if (initialCapacity > 1073741824)
            initialCapacity = 1073741824;
        int c = initialCapacity / ssize;
        if (c * ssize < initialCapacity)
            ++c;
        int cap = 2;
        while (cap < c)
            cap <<= 1;
        System.out.println(cap * loadFactor);
        // create segments and segments[0]
//        Segment<K,V> s0 =
//            new Segment<K,V>(loadFactor, (int)(cap * loadFactor),
//                             (HashEntry<K,V>[])new HashEntry[cap]);
//        Segment<K,V>[] ss = (Segment<K,V>[])new Segment[ssize];
//        UNSAFE.putOrderedObject(ss, SBASE, s0); // ordered write of segments[0]
//        this.segments = ss;
        System.out.println("the cap is : " + cap);
        System.out.println("the resize cap is : " +cap * loadFactor);
        int hash = hash(123456);
        System.out.println("the hash is : " + Integer.toBinaryString(hash));
        System.out.println("the hash >>> segmentShift is : "+ segmentShift+":"
        		+ Integer.toBinaryString(hash >>> segmentShift));
        System.out.println("the segmentMask is : " +segmentMask+":"
        			+ Integer.toBinaryString(segmentMask));
        int j = (hash >>> segmentShift) & segmentMask;
	}
	
    private static int hash(Object k) {

    	int h = 13;
        h ^= k.hashCode();

        h += (h <<  15) ^ 0xffffcd7d;
        h ^= (h >>> 10);
        h += (h <<   3);
        h ^= (h >>>  6);
        h += (h <<   2) + (h << 14);
        return h ^ (h >>> 16);
    }	

}
