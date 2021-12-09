package MyHashMap;

import java.util.ArrayList;
import java.util.List;
public class MyHashMap<K, V> implements MyMap<K, V> {
	// 定义默认数组大小 16 defaulAddSizeFactor=useSize/defaulLenth 4/16 =0.25
	private static int defaulLenth = 1 << 4;
	// 扩容标准 所使用的 useSize /数组长度 > 0.75
	// defaulAddSizeFactor过大 造成扩容概率变低 存储小 但是就是存 和 取效率降低
	// 0.9 有限的数组长度空间位置内会形成链表 在存或者取值都需要进行大量的遍历和判断（逻辑）
	private static double defaulAddSizeFactor = 0.75;
	// 使用数组位置的总是
	private int useSize;
	// 定义Map 骨架只要 数组之一 数组
	private Entry<K, V>[] table = null;

	// SPRING 门面模式运用
	public MyHashMap() {
		this(defaulLenth, defaulAddSizeFactor);
	}

	public MyHashMap(int length, double defaulAddSizeFactor) {
		if (length < 0) {
			throw new IllegalArgumentException("参数不能为负数" + length);

		}
		if (defaulAddSizeFactor <= 0 || Double.isNaN(defaulAddSizeFactor)) {
			throw new IllegalArgumentException("扩容标准必须是大于0的数字" + defaulAddSizeFactor);
		}
		this.defaulLenth = length;
		this.defaulAddSizeFactor = defaulAddSizeFactor;
		table = new Entry[defaulLenth];
	}

	@Override
	public V put(K k, V v) {
		if (useSize > defaulAddSizeFactor * defaulLenth) {
			up2Size();
		}
		int index = getIndex(k, table.length);
		Entry<K, V> entry = table[index];
		if (entry == null) {
			table[index] = new Entry(k, v, null);
			useSize++;
		} else if (entry != null) {
			table[index] = new Entry(k, v, entry);
		}

		return table[index].getValue();
	}

	// 寻找
	private int getIndex(K k, int length) {
		int m = length - 1;
		int index = hash(k.hashCode()) & m;
		return index;
	}

	// 自定义写自己的哈希算法
	// >>>
	private int hash(int hashCode) {
		hashCode = hashCode ^ ((hashCode >>> 20) ^ (hashCode >>> 12));
		return hashCode ^ ((hashCode >>> 7) ^ (hashCode >>> 4));
		
	}

	// 如何扩容 无非就是新建2倍空间的数组
	private void up2Size() {
		Entry<K, V>[] newtTable = new Entry[2 * defaulLenth];
		// 将老数组的内容拿到新数组当中
		againHash(newtTable);
	}

	private void againHash(MyHashMap<K, V>.Entry<K, V>[] newtTable) {
		List<Entry<K, V>> entryList = new ArrayList<MyHashMap<K, V>.Entry<K, V>>();
		// for出来就代表内容全部由遍历到entryList当中
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null) {
				continue;
			}
			// 继续找存到数组上的Entry对象、
			foundEntryByNext(table[i], entryList);

		}
		// 设置entryList
		if (entryList.size() > 0) {
			
			defaulLenth = 2 * defaulLenth;
			for (Entry<K, V> entry : entryList) {
				if (entry.next != null) {
					entry.next = null;
				}
				useSize = 0;
				put(entry.getKey(), entry.getValue());
			}
		}

	}

	private void foundEntryByNext(MyHashMap<K, V>.Entry<K, V> entry, List<MyHashMap<K, V>.Entry<K, V>> entryList) {
		if (entry != null && entry.next != null) {
			entryList.add(entry);
			// 递归 不断的一层一层取存entry
			foundEntryByNext(entry.next, entryList);
		} else {
			// 没有链表的情况
			entryList.add(entry);
		}
	}

	public int getUseSize() {
		return useSize;
	}

	public V get(K k) {
		// hashcode（new Person(1234,"Sammy")）-->hash-->getIndex-->最终索引位置
		int index = getIndex(k, table.length);
		if (table[index] == null) {
			throw new NullPointerException();
		}
		// key 存在情况
		return findValueByEqualKey(k, table[index]);
	}

	// 不同k 可能在同一个位置
	private V findValueByEqualKey(K k, MyHashMap<K, V>.Entry<K, V> entry) {
		if (k == entry.getKey() || k.equals(entry.getKey())) {
			return entry.getValue();
		} else if (entry.next != null) {
			// 循环一层一层递归 和传进来k相同的entry
			return findValueByEqualKey(k, entry.next);
		}
		return null;
	}

	// 创建一个内部存储的对象类型
	class Entry<K, V> implements MyMap.Entry<K, V> {
		K k;
		V v;
		// 指向被this挤压下去的Entry对象
		Entry<K, V> next;

		public Entry(K k, V v, Entry<K, V> next) {
			this.k = k;
			this.v = v;
			this.next = next;
		}

		public K getKey() {
			// TODO Auto-generated method stub
			return k;
		}

		public V getValue() {
			// TODO Auto-generated method stub
			return v;
		}

	}
}
