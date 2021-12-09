package MyHashMap;

import java.util.HashMap;
import java.util.Map;

/**
 * 用来将一个数的各二进制位全部右移若干位.例如:a   =   a>>2,使a的各二进制位右移两位,移到右端的低位被舍弃,最高位则移入原来高位的值.   
  如:a   =   00110111,则a>>2=00001101,b=11010011,则b>>2   =   11110100   
  右移一位相当于除2   取商,而且用右移实现除法比除法运算速度要快   
    
  无符号右移运算符>>>   
  用来将一个数的各二进制位无符号右移若干位,与运算符>>相同,移出的低位被舍弃,但不同的是最高位补0,如a   =   00110111,则a>>>2   =   00001101,b=11010011,则b>>>2   =   00110100 

 * @author Administrator
 *
 */
public class Test {
	public static void main(String[] args) {
		Map<String, String> hashMap = new HashMap<String, String>();
		
		hashMap.put("sam", "valueabc");
		hashMap.put("sam", "valueabc2");
			System.out.println(hashMap.get("sam"));
		         //按位运算符 异或 两个位运算不一致为16 -1 = 15  一致为0
		//jdk 里面规定数组的长度是 2 N -1
		//2 3 =8 -1 = 7
				//.misc.Hashing.stringHash32((String) k);来获取索引值
			   //0000 1111  >>> 3 = ？  0000 000 1
		//00001  位移算法 都是降低我们冲突
			//em.out.println(Integer.toBinaryString(15));
				//System.out.println("1<<6  的值是"+(1<<6));
			//二进制转十进制    1<<6 = 64  64-1 = 63 : 111111
			//System.out.println(Integer.parseInt("111101",2));
		//System.out.println("3<<1  的值是"+(3<<1*2));
	}

}
