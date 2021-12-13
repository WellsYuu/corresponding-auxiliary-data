package javax.core.common.utils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 提供各种对数据进行处理的方法. <br>
 */
@SuppressWarnings("unchecked")
public class DataUtils {
	private static final BigDecimal ONE = new BigDecimal("1");
	private static Log logger = LogFactory.getLog(DataUtils.class);

	/**
	 * 构造方法，禁止实例化
	 */
	private DataUtils() {}
	
	/** mergePO 时支持的数据类型 */
	private static Map<Class, String> supportTypeMap = new HashMap<Class, String>();
	static {
		//基本数据类型
		supportTypeMap.put(Integer.class, "");
		supportTypeMap.put(Long.class, "");
		supportTypeMap.put(Double.class, "");
		supportTypeMap.put(Byte.class, "");
		supportTypeMap.put(Character.class, "");
		supportTypeMap.put(Short.class, "");
		supportTypeMap.put(Float.class, "");
		supportTypeMap.put(Boolean.class, "");
		supportTypeMap.put(int.class, "");
		supportTypeMap.put(long.class, "");
		supportTypeMap.put(double.class, "");
		supportTypeMap.put(byte[].class, "");
		supportTypeMap.put(char.class, "");
		supportTypeMap.put(short.class, "");
		supportTypeMap.put(float.class, "");
		supportTypeMap.put(boolean.class, "");
		
		//其他常用类型
		supportTypeMap.put(Date.class, "");
		supportTypeMap.put(BigDecimal.class, "");
		supportTypeMap.put(String.class, "");
	}

	/**
	 * 添加mergePO时支持的类型
	 * 
	 * @param clazz
	 */
	public static void addSupportType(Class clazz) {
		supportTypeMap.put(clazz, "");
	}

	/**
	 * 当整型数值为0时,返回字符串"",否则将整型值转化为字符串返回. <br>
	 * <br>
	 * <b>示例 </b> <code>
	 * <br>DataUtils.zeroToEmpty(0) 返回 &quot;&quot;
	 * <br>DataUtils.zeroToEmpty(1) 返回 &quot;1&quot;
	 * </code>
	 * 
	 * @param i
	 *            输入的整型值
	 * @return 返回字符串
	 */
	public static String zeroToEmpty(int i) {
		return i == 0 ? "" : String.valueOf(i);
	}

	/**
	 * 当浮点型数值为0时,返回字符串"",否则将浮点型值转化为字符串返回. <br>
	 * <br>
	 * <b>示例 </b> <code>
	 * <br>DataUtils.zeroToEmpty(0d) 返回 &quot;&quot;
	 * <br>DataUtils.zeroToEmpty(1.2d) 返回 &quot;1.2&quot;
	 * </code>
	 * 
	 * @param d
	 *            输入的浮点型值
	 * @return 返回字符串
	 */
	public static String zeroToEmpty(double d) {
		return d == 0 ? "" : String.valueOf(d);
	}

	/**
	 * 当字符串为null时,返回字符串"". <br>
	 * <br>
	 * <b>示例 </b> <code>
	 * <br>DataUtils.nullToEmpty(null) 返回 &quot;&quot;
	 * <br>DataUtils.nullToEmpty(&quot;null&quot;) 返回 &quot;null&quot;
	 * <br>DataUtils.nullToEmpty(&quot;abc&quot;) 返回 &quot;abc&quot;
	 * </code>
	 * 
	 * @param str
	 *            输入字符串
	 * @return 返回字符串
	 */
	public static String nullToEmpty(String str) {
		return str == null ? "" : str;
	}

	/**
	 * 当字符串为""时,返回null. <br>
	 * <br>
	 * <b>示例 </b> <code>
	 * <br>DataUtils.nullToEmpty(null) 返回 null
	 * <br>DataUtils.nullToEmpty(&quot;&quot;) 返回 null
	 * <br>DataUtils.nullToEmpty(&quot;abc&quot;) 返回 &quot;abc&quot;
	 * </code>
	 * 
	 * @param str
	 *            输入字符串
	 * @return 返回字符串
	 */
	public static String emptyToNull(String str) {
		if (str == null) {
			return null;
		}
		if (str.trim().length() == 0) {
			return null;
		}
		return str;
	}

	/**
	 * 当字符串为"null"或为null时,返回字符串"". <br>
	 * <br>
	 * <b>示例 </b> <code>
	 * <br>DataUtils.dbNullToEmpty(null) 返回 &quot;&quot;
	 * <br>DataUtils.dbNullToEmpty(&quot;null&quot;) 返回 &quot;&quot;
	 * <br>DataUtils.dbNullToEmpty(&quot;abc&quot;) 返回 &quot;abc&quot;
	 * </code>
	 * 
	 * @param str
	 *            输入字符串
	 * @return 返回字符串
	 */
	public static String dbNullToEmpty(String str) {
		if (str == null || str.equalsIgnoreCase("null")) {
			return "";
		}
		return str;
	}

	/**
	 * 当字符串为null或""或全部为空格时,返回字符串"0",否则将字符串原封不动的返回. <br>
	 * <br>
	 * <b>示例 </b> <code>
	 * <br>DataUtils.nullToZero(null) 返回 &quot;0&quot;
	 * <br>DataUtils.nullToZero(&quot;&quot;) 返回 &quot;0&quot;
	 * <br>DataUtils.nullToZero(&quot;123&quot;) 返回 &quot;123&quot;
	 * <br>DataUtils.nullToZero(&quot;abc&quot;) 返回 &quot;abc&quot; 注意：从方法的本意出发，请用于数值型字符串
	 * </code>
	 * 
	 * @param str
	 *            输入字符串
	 * @return 返回字符串
	 */
	public static String nullToZero(String str) {
		if (str == null || str.trim().length() == 0) {
			return "0";
		}
		return str;
	}

	/**
	 * 对表达布尔型含义的字符串转换为中文的"是"/"否". <br>
	 * <br>
	 * <b>示例 </b> <code>
	 * <br>DataUtils.getBooleanDescribe(&quot;y&quot;) 返回 &quot;是&quot;
	 * <br>DataUtils.getBooleanDescribe(&quot;yes&quot;) 返回 &quot;是&quot;
	 * <br>DataUtils.getBooleanDescribe(&quot;Y&quot;) 返回 &quot;是&quot;
	 * <br>DataUtils.getBooleanDescribe(&quot;true&quot;) 返回 &quot;是&quot;
	 * <br>DataUtils.getBooleanDescribe(&quot;T&quot;) 返回 &quot;是&quot;
	 * <br>
	 * <br>DataUtils.getBooleanDescribe(&quot;n&quot;) 返回 &quot;否&quot;
	 * <br>DataUtils.getBooleanDescribe(&quot;No&quot;) 返回 &quot;否&quot;
	 * <br>DataUtils.getBooleanDescribe(&quot;N&quot;) 返回 &quot;否&quot;
	 * <br>DataUtils.getBooleanDescribe(&quot;false&quot;) 返回 &quot;否&quot;
	 * <br>DataUtils.getBooleanDescribe(&quot;f&quot;) 返回 &quot;否&quot;
	 * </code>
	 * 
	 * @param str
	 *            表达布尔型含义的字符串. <br>
	 *            合法的输入包括"y","n","yes","no","true","false","t","f","是","否","1","0",""这些字符串的各种大小写形式也属于合法的
	 *            <br>
	 *            除了上述合法的入参值之外，输入其它的字符串，将抛出异常
	 * @return 布尔变量对应的中文描述："是"/"否"/""
	 */
	public static String getBooleanDescribe(String str) {
		if (str == null) {
			throw new IllegalArgumentException("argument is null");
		} 
		if (str.equalsIgnoreCase("y") || str.equalsIgnoreCase("yes")
				|| str.equalsIgnoreCase("true") || str.equalsIgnoreCase("t")
				|| str.equalsIgnoreCase("是") || str.equalsIgnoreCase("1")) {
			return "是";
		} else if (str.equalsIgnoreCase("n") || str.equalsIgnoreCase("no")
				|| str.equalsIgnoreCase("false") || str.equalsIgnoreCase("f")
				|| str.equalsIgnoreCase("否") || str.equalsIgnoreCase("0")) {
			return "否";
		} else if (str.trim().equals("")) {
			return "";
		}
		throw new IllegalArgumentException(
				"argument not in ('y','n','yes','no','true','false','t','f','是','否','1','0','')");
	}

	/**
	 * 对表达布尔型含义的字符串转换为boolean型的true/false. <br>
	 * <br>
	 * <b>示例 </b> <code>
	 * <br>DataUtils.getBoolean(&quot;y&quot;) 返回 true
	 * <br>DataUtils.getBoolean(&quot;yes&quot;) 返回 true
	 * <br>DataUtils.getBoolean(&quot;Y&quot;) 返回 true
	 * <br>DataUtils.getBoolean(&quot;true&quot;) 返回 true
	 * <br>DataUtils.getBoolean(&quot;T&quot;) 返回 true
	 * <br>
	 * <br>DataUtils.getBoolean(&quot;n&quot;) 返回 false
	 * <br>DataUtils.getBoolean(&quot;No&quot;) 返回 false
	 * <br>DataUtils.getBoolean(&quot;N&quot;) 返回 false
	 * <br>DataUtils.getBoolean(&quot;false&quot;) 返回 false
	 * <br>DataUtils.getBoolean(&quot;f&quot;) 返回 false
	 * </code>
	 * 
	 * @param str
	 *            表达布尔型含义的字符串. <br>
	 *            合法的输入包括"y","n","yes","no","true","false","t","f","是","否","1","0",""这些字符串的各种大小写形式也属于合法的
	 *            <br>
	 *            除了上述合法的入参值之外，输入其它的字符串，将抛出异常
	 * @return boolean型的true/false
	 */
	public static boolean getBoolean(String str) {
		if (str == null) {
			throw new IllegalArgumentException("argument is null");
		}
		if (str.equalsIgnoreCase("y") || str.equalsIgnoreCase("yes")
				|| str.equalsIgnoreCase("true") || str.equalsIgnoreCase("t")
				|| str.equalsIgnoreCase("是") || str.equalsIgnoreCase("1")) {
			return true;
		} else if (str.equalsIgnoreCase("n") || str.equalsIgnoreCase("no")
				|| str.equalsIgnoreCase("false") || str.equalsIgnoreCase("f")
				|| str.equalsIgnoreCase("否") || str.equalsIgnoreCase("0")) {
			return false;
		} else if (str.trim().equals("")) {
			return false;
		}
		throw new IllegalArgumentException(
				"argument not in ('y','n','yes','no','true','false','t','f','是','否','1','0','')");
	}

	/**
	 * 返回对应boolean型变量的字符串型中文描述：'是'/'否'. <br>
	 * <br>
	 * <b>示例 </b> <code>
	 * <br>DataUtils.getBooleanDescribe(true) 返回 '是'
	 * <br>DataUtils.getBooleanDescribe(false) 返回 '否'
	 * </code>
	 * 
	 * @param bln
	 *            布尔型变量. <br>
	 * @return 字符串型中文描述：'是'/'否'
	 */
	public static String getBooleanDescribe(boolean bln) {
		if (bln) {
			return getBooleanDescribe("true");
		}
		return getBooleanDescribe("false");
	}

	/**
	 * 比较两个存放了数字的字符串的大小，如果不为数字将抛出异常. <br>
	 * <br>
	 * <b>示例 </b> <code>
	 * <br>DataUtils.compareByValue(&quot;19&quot;,&quot;2&quot;) 返回 1
	 * <br>DataUtils.compareByValue(&quot;0021&quot;,&quot;21&quot;) 返回 0
	 * <br>DataUtils.compareByValue(&quot;3001&quot;,&quot;5493&quot;) 返回 -1
	 * </code>
	 * 
	 * @param str1
	 *            第一个字符串
	 * @param str2
	 *            第二个字符串
	 * @return 返回比较的结果 str1>str2返回1，str1 <str2返回-1，str1=str2返回0
	 */
	public static int compareByValue(String str1, String str2) {
		BigDecimal big1 = new BigDecimal(str1);
		BigDecimal big2 = new BigDecimal(str2);
		return big1.compareTo(big2);
	}

	/**
	 * 提供精确的小数位四舍五入处理. <br>
	 * <br>
	 * <b>示例 </b> <code>
	 * <br>DataUtils.round(0.574,2) 返回 0.57
	 * <br>DataUtils.round(0.575,2) 返回 0.58
	 * <br>DataUtils.round(0.576,2) 返回 0.58
	 * </code>
	 * 
	 * @param value
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double value, int scale) {
		BigDecimal b = new BigDecimal(Double.toString(value));
		return b.divide(ONE, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 拷贝简单对象.(null也将拷贝)
	 * 
	 * @param source
	 *            传入的源对象
	 * @param target
	 *            传入的目标对象
	 */
	public static void copySimpleObject(Object source, Object target) {
		copySimpleObject(source, target, true);
	}

	/**
	 * 拷贝简单对象.
	 * 
	 * @param source
	 *            传入的源对象
	 * @param target
	 *            传入的目标对象
	 * @param isCopyNull
	 *            是否拷贝Null值
	 */
	public static void copySimpleObject(Object source, Object target,
			boolean isCopyNull) {
		if (target == null || source == null) {
			return;
		}
		List targetMethodList = BeanUtils.getSetter(target.getClass());
		List sourceMethodList = BeanUtils.getGetter(source.getClass());
		Map<String, Method> map = new HashMap<String, Method>();
		for (Iterator iter = sourceMethodList.iterator(); iter.hasNext();) {
			Method method = (Method) iter.next();
			map.put(method.getName(), method);
		}
		for (Iterator iter = targetMethodList.iterator(); iter.hasNext();) {
			Method method = (Method) iter.next();
			String fieldName = method.getName().substring(3);
			try {
				Method sourceMethod = (Method) map.get("get" + fieldName);
				if (sourceMethod == null) {
					sourceMethod = (Method) map.get("is" + fieldName);
				}
				if (sourceMethod == null) {
					continue;
				}
				if (!supportTypeMap.containsKey(sourceMethod.getReturnType())) {
					continue;
				}

				Object value = sourceMethod.invoke(source, new Object[0]);
				if (isCopyNull) {
					method.invoke(target, new Object[] { value });
				} else {
					if (value != null) {
						method.invoke(target, new Object[] { value });
					}
				} 
			} catch (Exception e) {
				if(logger.isDebugEnabled()){
					logger.debug(e); 
				}
			}
		}
	}

	/**
	 * 把通过JdbcTemplate查出的结果集封装到List中<br>
	 * 只要字段名和DTO的属性名能对应上的就把值封装进去，对应不上的就不管了
	 * 
	 * @param jdbcResultList
	 *            用JdbcTemplate查出的结果集
	 * @param clazz
	 *            DTO的Class对象
	 * @return 把每行数据封装到一个DTO对象中，最后返回DTO的List
	 */
	public static List generateListFromJdbcResult(List jdbcResultList,
			Class clazz) {
		List<Object> objectList = new ArrayList<Object>();
		try {
			List methodList = BeanUtils.getSetter(clazz);
			for (int i = 0; i < jdbcResultList.size(); i++) {
				Map rowMap = (Map) jdbcResultList.get(i);
				Object[] rowKeys = rowMap.keySet().toArray();
				Object object = clazz.newInstance();
				for (int j = 0; j < rowKeys.length; j++) {
					String column = (String) rowKeys[j];
					for (int k = 0; k < methodList.size(); k++) {
						Method method = (Method) methodList.get(k);
						String upperMethodName = method.getName().toUpperCase();
						if (upperMethodName.equals("SET" + column)) {
							Class type = method.getParameterTypes()[0];
							Object value = rowMap.get(column);
							if (value != null) {
								if (type == Integer.class) {
									value = new Integer(value.toString());
								} else if (type == Double.class) {
									value = new Double(value.toString());
								} else if (type == Long.class) {
									value = new Long(value.toString());
								}
							}
							method.invoke(object, new Object[] { value });
							break;
						}
					}
				}
				objectList.add(object);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return objectList;
	}

	/**
	 * 把Object对象转换为Integer对象。
	 * 
	 * @param object
	 * @return Integer对象或null（如果object是null）。
	 */
	public static Integer getInteger(Object object) {
		Integer _integer = null;
		if (object != null) {
			_integer = new Integer(object.toString());
		}
		return _integer;
	}

	/**
	 * 把Object对象转换为Long对象。
	 * 
	 * @param object
	 * @return Long对象或null（如果object是null）。
	 */
	public static Long getLong(Object object) {
		Long _long = null;
		if (object != null) {
			_long = new Long(object.toString());
		}
		return _long;
	}

	/**
	 * 把Object对象转换为Double对象。
	 * 
	 * @param object
	 * @return Double对象或null（如果object是null）。
	 */
	public static Double getDouble(Object object) {
		Double _double = null;
		if (object != null) {
			_double = new Double(object.toString());
		}
		return _double;
	}

	/**
	 * 把Object对象转换为String对象。
	 * 
	 * @param object
	 * @return String对象或null（如果object是null）。
	 */
	public static String getString(Object object) {
		String string = null;
		if (object != null) {
			string = new String(object.toString());
		}
		return string;
	}

	public static String getPlainNumber(Integer integer) {
		if (integer == null) {
			return "";
		}
		DecimalFormat df = new DecimalFormat("###0");
		String plainNumber = df.format(integer);
		return plainNumber;
	}

	public static String getPlainNumber(Long _long) {
		if (_long == null) {
			return "";
		}
		DecimalFormat df = new DecimalFormat("###0");
		String plainNumber = df.format(_long);
		return plainNumber;
	}

	public static String getPlainNumber(Double _double) {
		if (_double == null) {
			return "";
		}
		DecimalFormat df = new DecimalFormat("###0.00");
		String plainNumber = df.format(_double);
		return plainNumber;
	}

	/**
	 * 判断字符串是不是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (str == null) return false;
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
}