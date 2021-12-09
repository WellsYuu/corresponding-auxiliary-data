package MyHashMap;
/**
 * 推介的面向接口编程的思想
 * Collection
 * 双列集合接口MyMap
 * @author Sam
 *
 */
public interface MyMap<K,V> {
     //MyMap基本功能是   快速存 
	public V put(K k,V v);
	//快速取
	public V get(K k);
	//定义一个内部接口
	 public interface Entry<K,V>{
		 //map.getKey
		 public K getKey();
		 public V getValue();
		 
	 }
}
