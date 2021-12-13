package javax.core.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * String工具类
 * 
 * @author Tom
 */
public class StringUtils {
	
	private StringUtils(){}

	/**
	 * 是否为空 是则返回一个空字符
	 * 
	 * @param str
	 * @return String
	 */
	public static String notNull(String str) {
		return str == null ? "" : str;
	}

	/**
	 * 格式化字符
	 * 
	 * @param str
	 * @param args
	 * @return String
	 */
	public static String format(String str, Object... args) {
		String result = str;
		java.util.regex.Pattern p = java.util.regex.Pattern
				.compile("\\{(\\d+)\\}");
		java.util.regex.Matcher m = p.matcher(str);
		while (m.find()) {
			int index = Integer.parseInt(m.group(1));
			if (index < args.length) {
				result = result.replace(m.group(),
						ObjectUtils.notNull(args[index], "").toString());
			}
		}
		return result;
	}

	/**
	 * 编码
	 * 
	 * @param str
	 * @return
	 */
	public static String coding(String str) {
		return coding(str, "ISO-8859-1");
	}

	public static String coding(String str, String charset) {
		return coding(str, charset, "UTF-8");
	}

	public static String coding(String str, String charset, String tocharset) {
		try {
			return str == null ? "" : new String(str.getBytes(charset),
					tocharset);
		} catch (Exception E) {
			return str;
		}
	}

	/**
	 * Escape 编码
	 * 
	 * @param src
	 * @return
	 */
	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	/**
	 * 截取字符串
	 * 
	 * @param src
	 * @param start
	 * @param length
	 * @param ov
	 * @return
	 */
	public static String substring(String src, int start, int length, String ov) {
		if (src != null && length(src) > length) {
			try {
				byte[] rc = src.getBytes("GBK");
				short charlen = 2;
				int count = 0;
				if (!new String(rc).equals(src)) {
					rc = src.getBytes("UTF-8");
					charlen = 3;
				}
				length = Math.max(Math.min(length - start, rc.length), 1);
				byte[] bs = new byte[length];
				System.arraycopy(rc, start, bs, 0, length);
				for (byte c : bs)
					if (c < 0)
						count++;
				if (count % charlen != 0)
					return substring(src, start, length - (count % charlen), ov);
				return new String(bs) + ov;
			} catch (Exception e) {
				return src.substring(start, length - ov.length()) + ov;
			}
		}
		return src;
	}

	/**
	 * bytes to hex string
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytes2Hex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			int i = b & 0xFF;
			if (i <= 0xF) {
				sb.append("0");
			}
			sb.append(Integer.toHexString(i));
		}
		return sb.toString();
	}

//	public static final List<Character> HEX_CHAR_LIST;
//	static {
//		HEX_CHAR_LIST = new ArrayList<Character>();
//		HEX_CHAR_LIST.add(new Character('0'));
//		HEX_CHAR_LIST.add(new Character('1'));
//		HEX_CHAR_LIST.add(new Character('2'));
//		HEX_CHAR_LIST.add(new Character('3'));
//		HEX_CHAR_LIST.add(new Character('4'));
//		HEX_CHAR_LIST.add(new Character('5'));
//		HEX_CHAR_LIST.add(new Character('6'));
//		HEX_CHAR_LIST.add(new Character('7'));
//		HEX_CHAR_LIST.add(new Character('8'));
//		HEX_CHAR_LIST.add(new Character('9'));
//		HEX_CHAR_LIST.add(new Character('a'));
//		HEX_CHAR_LIST.add(new Character('b'));
//		HEX_CHAR_LIST.add(new Character('c'));
//		HEX_CHAR_LIST.add(new Character('d'));
//		HEX_CHAR_LIST.add(new Character('e'));
//		HEX_CHAR_LIST.add(new Character('f'));
//	}

//	private static byte hex2Byte(String s) {
//		int high = HEX_CHAR_LIST.indexOf(new Character(s.charAt(0))) << 4;
//		int low = HEX_CHAR_LIST.indexOf(new Character(s.charAt(1)));
//
//		return (byte) (high + low);
//	}

	/**
	 * hex string to bytes
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte[] hex2Bytes(String input) {
		byte[] res = new byte[input.length() / 2];        
		char[] chs = input.toCharArray();  
		for(int i = 0,c = 0; i < chs.length; i += 2,c ++){  
			res[c] = (byte) (Integer.parseInt(new String(chs,i,2), 16));  
		}
		return res;  
	}

	public static String getPrefix(String content, String regex) {
		int _index = content.indexOf(regex);
		if (_index >= 0) {
			return content.substring(0, _index);
		}
		return content;
	}

	/**
	 * 将指定位置的字符转换为小写
	 * 
	 * @param str
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	public static String lowerCase(String str, int beginIndex, int endIndex) {
		StringBuilder builder = new StringBuilder();
		builder.append(str.substring(0, beginIndex));
		builder.append(str.substring(beginIndex, endIndex).toLowerCase());
		builder.append(str.substring(endIndex));
		return builder.toString();
	}

	/**
	 * 将指定位置的字符转换为大写
	 * 
	 * @param str
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	public static String upperCase(String str, int beginIndex, int endIndex) {
		StringBuilder builder = new StringBuilder();
		builder.append(str.substring(0, beginIndex));
		builder.append(str.substring(beginIndex, endIndex).toUpperCase());
		builder.append(str.substring(endIndex));
		return builder.toString();
	}

	/**
	 * 将首字母转换为小写
	 * 
	 * @param iString
	 * @return
	 */
	public static String lowerCaseFirstChar(String iString) {
		StringBuilder builder = new StringBuilder();
		builder.append(iString.substring(0, 1).toLowerCase());
		builder.append(iString.substring(1));
		return builder.toString();
	}

	/**
	 * 将首字母转换为大写
	 * 
	 * @param iString
	 * @return
	 */
	public static String upperCaseFirstChar(String iString) {
		StringBuilder builder = new StringBuilder();
		builder.append(iString.substring(0, 1).toUpperCase());
		builder.append(iString.substring(1));
		return builder.toString();
	}

	/**
	 * 检查子符串出现过几次
	 * 
	 * @param str
	 *            源字符串
	 * @param subStr
	 *            子字符串
	 * @return
	 */
	public static int timesOf(String str, String subStr) {
		int foundCount = 0;
		if (subStr.equals("")) {
			return 0;
		}
		int fromIndex = str.indexOf(subStr);
		while (fromIndex != -1) {
			++foundCount;
			fromIndex = str.indexOf(subStr, fromIndex + subStr.length());
		}
		return foundCount;
	}

	/**
	 * 去除两边空格（包括全角空格）, null 值以空字符代替
	 * 
	 * @param s
	 * @return
	 */
	public static String trim(String s) {
		if (s == null)
			return "";
		else
			return s.trim();
	}

	/**
	 * 去除右边的空格
	 * 
	 * @param str
	 * @return
	 */
	public static String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; --i) {
			if (str.charAt(i) != ' ') {
				break;
			}
			--length;
		}
		return str.substring(0, length);
	}

	/**
	 * 去除左边的空格
	 * 
	 * @param str
	 * @return
	 */
	public static String leftTrim(String str) {
		if (str == null) {
			return "";
		}
		int start = 0;
		int i = 0;
		for (int n = str.length(); i < n; ++i) {
			if (str.charAt(i) != ' ') {
				break;
			}
			++start;
		}
		return str.substring(start);
	}

	/**
	 * 将字符串分割后，并去除其中的空值
	 * 
	 * @param originalString
	 * @param delimiterString
	 * @return
	 */
	public static String[] split(String originalString, String delimiterString) {
		int index = 0;
		String[] returnArray = null;
		int length = 0;

		if ((originalString == null) || (delimiterString == null)
				|| (originalString.equals(""))) {
			return new String[0];
		}

		if ((originalString.equals("")) || (delimiterString.equals(""))
				|| (originalString.length() < delimiterString.length())) {
			return new String[] { originalString };
		}

		String strTemp = originalString;
		while ((strTemp != null) && (!(strTemp.equals("")))) {
			index = strTemp.indexOf(delimiterString);
			if (index == -1) {
				break;
			}
			++length;
			strTemp = strTemp.substring(index + delimiterString.length());
		}
		returnArray = new String[++length];

		for (int i = 0; i < length - 1; ++i) {
			index = originalString.indexOf(delimiterString);
			returnArray[i] = originalString.substring(0, index);
			originalString = originalString.substring(index
					+ delimiterString.length());
		}

		returnArray[(length - 1)] = originalString;
		return returnArray;
	}

	/**
	 * 将形如 key1=value1,key2=value2 的字符串，转换为Map
	 * 
	 * @param str
	 * @param splitString
	 * @return
	 */
	public static Map<String, String> toMap(String str, String splitString) {
		return strToMap(str,splitString,"=");
	}

	/**
	 * Json字符串转换为Map
	 * 
	 * @param jsonStr
	 * @param splitStr
	 * @return
	 */
	public static Map<String, String> jsonToMap(String jsonStr, String splitStr) {
		jsonStr = jsonStr.startsWith("{") ? jsonStr.substring(1) : jsonStr;
		jsonStr = jsonStr.endsWith("}") ? jsonStr.substring(0,
				jsonStr.length() - 1) : jsonStr;
		return strToMap(jsonStr,splitStr,":");
	}
	
	/**
	 * 将字符串拆分成Map "str1=1,str2=abc,..."
	 * @param str 待拆分字符串
	 * @param splitStr	拆分字符 ","
	 * @param linkStr 连接字符 "="
	 * @return
	 */
	public static Map<String, String> strToMap(String str,String splitStr,String linkStr){
		Map<String, String> map = Collections.synchronizedSortedMap(new TreeMap<String, String>());
		String[] values = split(str, splitStr);
		for (int i = 0; i < values.length; ++i) {
			String tempValue = values[i];
			int pos = tempValue.indexOf(linkStr);
			String key = "", value = "";
			if (pos > -1) {
				key = tempValue.substring(0, pos);
				value = tempValue.substring(pos + splitStr.length());
			} else {
				key = tempValue;
			}
			map.put(key.replace("\"", ""), value.replace("\"", ""));
		}
		return map;
	}
	
	public static String iso2gbk(String s) {
		if (s == null)
			return "";
		try {
			return new String(s.getBytes("ISO-8859-1"), "GBK").trim();
		} catch (Exception e) {
			return s;
		}
	}

	public static String gbk2iso(String s) {
		if (s == null)
			return "";
		try {
			return new String(s.getBytes("GBK"), "ISO-8859-1").trim();
		} catch (Exception e) {
			return s;
		}
	}

	public static String utf2iso(String s) {
		if (s == null)
			return "";
		try {
			return new String(s.getBytes(), "UTF-8").trim();
		} catch (Exception e) {
			return s;
		}
	}

	/**
	 * 判断字符串是否不为空,如果为空则指定一个字符串代替
	 * 
	 * @param str
	 *            str1
	 * @return String
	 */
	public static String notNull(String str, String str1) {
		return (str == null || "".equals(str)) ? str1 : str;
	}

	/**
	 * 判断字符串是否真空
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isNull(String str) {
		return str == null ? true : false;
	}

	/**
	 * 判断字符是否为空或者空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String... str) {
		for (String s : str) {
			if (isNull(s) || notNull(s).trim().length() < 1)
				return true;
		}
		return false;
	}

	/**
	 * 字符长度
	 * 
	 * @param str
	 * @return
	 */
	public static int length(String str) {
		try {
			return str.getBytes("GBK").length;
		} catch (UnsupportedEncodingException e) {
			return str.length();
		}
	}

	/**
	 * Escape SQL tags, ' to ''; \ to \\
	 * 
	 * @param input
	 *            string to replace
	 * @return string
	 */
	public static String escapeSQLTags(String input) {
		if (input == null || input.length() == 0) {
			return input;
		}
		StringBuffer buf = new StringBuffer();
		char ch = ' ';
		for (int i = 0; i < input.length(); i++) {
			ch = input.charAt(i);
			if (ch == '\\') {
				buf.append("\\");
			} else if (ch == '\'') {
				buf.append("\'");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(
							src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(
							src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	/**
	 * 将指定字符转换为 Unicode 编码
	 * 
	 * @param str
	 * @return
	 */
	public static String native2ascii(String str) {
		char[] ca = str.toCharArray();
		StringBuffer buffer = new StringBuffer(ca.length * 6);
		for (int x = 0; x < ca.length; ++x) {
			char a = ca[x];
			if (a > 255)
				buffer.append("\\u").append(Integer.toHexString(a));
			else {
				buffer.append(a);
			}
		}
		return buffer.toString();
	}

	/**
	 * 将指定长度的字符串用指定字符代替
	 * 
	 * @param str
	 * @param len
	 * @param showStr
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getPartString(String str, int len, String showStr)
			throws UnsupportedEncodingException {
		byte b[];
		int counterOfDoubleByte = 0;
		b = str.getBytes("UTF-8");
		if (b.length <= len)
			return str;
		for (int i = 0; i < len; i++) {
			if (b[i] < 0)
				counterOfDoubleByte++;
		}
		if (counterOfDoubleByte % 2 == 0)
			return new String(b, 0, len, "UTF-8") + showStr;
		else
			return new String(b, 0, len - 1, "UTF-8") + showStr;
	}

	/**
	 * 将指定的字符进行分页
	 * 
	 * @param pageIndex
	 *            当前页索引,从 1 开始
	 * @param pageSize
	 *            规定每页固定输出的字符总长度
	 * @return
	 */
	public static String pagination(String text, int pageIndex, int pageSize) {
		if (isEmpty(text))
			return text;
		return pagination(text, pageIndex, pageSize, text.length());
	}

	/**
	 * 将指定的字符进行分页
	 * 
	 * @param pageIndex
	 *            当前页索引,从 1 开始
	 * @param pageSize
	 *            规定每页固定输出的字符总长度
	 * @param lineLength
	 *            规定每行显示多少个字符,这个变量用于控制显示效果
	 * @return
	 */
	public static String pagination(String text, int pageIndex, int pageSize,
			int lineLength) {
		if (isEmpty(text))
			return text;
		StringBuffer sb = new StringBuffer();
		// 计算总页数
		Integer totalPage = ((text.length() % pageSize == 0) ? text.length()
				/ pageSize : (text.length() / pageSize + 1));
		if (pageIndex > totalPage) {
			return sb.append("页码无效").toString();
		}
		String result = text.toString()
				.substring(
						(pageIndex - 1) * pageSize,
						(((pageIndex - 1) * pageSize + pageSize) >= text
								.length() - 1) ? text.length()
								: ((pageIndex - 1) * pageSize + pageSize));

		char[] chars = result.toCharArray();
		int length = lineLength;
		for (int i = 0; i < chars.length; i++) {
			if (length == 0) {
				length = lineLength;
				sb.append("\n");
			}
			length--;
			sb.append(chars[i]);
		}
		return sb.toString();
	}

	/**
	 * 返回字符串Byte数组长度. <br>
	 * <br>
	 * <b>示例: </b> <br>
	 * StringUtils.getBytesLength(&quot;幕课网&quot;) 返回值： 6
	 * StringUtils.getBytesLength(&quot;chaoxing.com&quot;) 返回值：12
	 * StringUtils.getBytesLength(&quot;&quot;) 返回值： 0
	 * StringUtils.getBytesLength(null) 返回值： -1
	 * 
	 * @param str
	 *            中文字符串或者英文字符串，或者null
	 * @return 返回字符串byte数组长度，如果是空字符串则返回0，如果是null则返回-1.
	 */
	public static int getBytesLength(String str) {
		if (str == null) {
			return -1;
		}
		return str.getBytes().length;
	}

	/**
	 * 字符串查找，根据条件字符串在目标字符串中匹配，返回匹配索引 <br>
	 * <br>
	 * <b>示例 </b> <br>
	 * StringUtils.indexOf(&quot;Tom's name is Tom&quot;,
	 * &quot;tom&quot;,0,true) 返回值： -1<br>
	 * StringUtils.indexOf(&quot;Tom's name is Tom&quot;,
	 * &quot;Tom&quot;,0,true) 返回值： 0 <br>
	 * StringUtils.indexOf(&quot;Tom's name is Tom&quot;,
	 * &quot;Tom&quot;,2,true) 返回值： 14 <br>
	 * 
	 * @param str
	 *            目标字符串，不能为null
	 * @param subStr
	 *            匹配条件字符串，不能为null
	 * @param fromIndex
	 *            开始索引
	 * @param caseSensitive
	 *            是否字母大小写匹配，true：区分；false：不区分
	 * @return 返回从fromIndex索引位置开始的查询子字符串在目标字符串中的索引值，如果没有匹配项则返回-1.
	 */
	public static int indexOf(String str, String subStr, int fromIndex,
			boolean caseSensitive) {
		if (caseSensitive == false) {
			return str.toLowerCase().indexOf(subStr.toLowerCase(), fromIndex);
		}
		return str.indexOf(subStr, fromIndex);
	}

	/**
	 * 字符换替换。把字符串str中searchStr字符串替换成replaceStr字符串. <br>
	 * <br>
	 * <b>示例： </b> <br>
	 * StringUtils.replace(&quot;Tom's name is
	 * Tom&quot;,&quot;Tom&quot;,&quot;Peter&quot;,true) 返回值： &quot;Peter's name
	 * is Peter&quot; <br>
	 * StringUtils.replace(&quot;Tom's name is
	 * Tom&quot;,&quot;tom&quot;,&quot;Peter&quot;,true) 返回值：&quot;Tom's name is
	 * Tom&quot; <br>
	 * StringUtils.replace(&quot;Tom's name is
	 * Tom&quot;,&quot;tom&quot;,&quot;Peter&quot;,false) 返回值：&quot;Peter's name
	 * is Peter&quot; <br>
	 * 
	 * @param str
	 *            目标字符串，不能为null
	 * @param searchStr
	 *            查询字符换，不能为null
	 * @param replaceStr
	 *            替换字符串
	 * @param caseSensitive
	 *            是否字母大小写匹配，true：区分；false：不区分
	 * @return 返回替换后的字符串
	 */
	public static String replace(String str, String searchStr,
			String replaceStr, boolean caseSensitive) {
		String result = "";
		int i = 0;
		int j = 0;
		if (str == null) {
			return null;
		}
		if (str.equals("")) {
			return "";
		}
		if (searchStr == null || searchStr.equals("")) {
			return str;
		}
		if (replaceStr == null) {
			replaceStr = "";
		}
		while ((j = indexOf(str, searchStr, i, caseSensitive)) > -1) {
			result = result + str.substring(i, j) + replaceStr;
			i = j + searchStr.length();
		}
		result = result + str.substring(i, str.length());
		return result;
	}

	/**
	 * 字符串替换，根据规则把oldStr替换后返回newStr.<br>
	 * <br>
	 * <b>示例： </b> <br>
	 * StringUtils.replace(null, *, *) = null <br>
	 * StringUtils.replace(&quot;&quot;, *, *) = &quot;&quot; <br>
	 * StringUtils.replace(&quot;aba&quot;, null, null) = &quot;aba&quot; <br>
	 * StringUtils.replace(&quot;aba&quot;, null, null) = &quot;aba&quot; <br>
	 * StringUtils.replace(&quot;aba&quot;, &quot;a&quot;, null) =
	 * &quot;aba&quot; <br>
	 * StringUtils.replace(&quot;aba&quot;, &quot;a&quot;, &quot;&quot;) =
	 * &quot;b&quot; <br>
	 * StringUtils.replace(&quot;aba&quot;, &quot;a&quot;, &quot;z&quot;) =
	 * &quot;zbz&quot; <br>
	 * 
	 * @param str
	 *            目标字符串，如果为null则返回null
	 * @param searchStr
	 *            匹配字符串，如果为null则不匹配任何值
	 * @param replaceStr
	 *            替换字符串，如果为null则不替换任何字符串
	 * @return 返回全部替换后的字符串
	 */
	public static String replace(String str, String searchStr, String replaceStr) {
		return replace(str, searchStr, replaceStr, true);
	}

	/**
	 * 字符替换，根据规则把searchChar替换后返回newStr.<br>
	 * <br>
	 * <b>示例： </b> <br>
	 * StringUtils.replace(null, *, *) = null <br>
	 * StringUtils.replace(&quot;&quot;, *, *) = &quot;&quot; <br>
	 * StringUtils.replace(&quot;aba&quot;, null, null) = &quot;aba&quot; <br>
	 * StringUtils.replace(&quot;aba&quot;, null, null) = &quot;aba&quot; <br>
	 * StringUtils.replace(&quot;aba&quot;, &quot;a&quot;, null) =
	 * &quot;aba&quot; <br>
	 * StringUtils.replace(&quot;aba&quot;, &quot;a&quot;, &quot;&quot;) =
	 * &quot;b&quot; <br>
	 * StringUtils.replace(&quot;aba&quot;, &quot;a&quot;, &quot;z&quot;) =
	 * &quot;zbz&quot; <br>
	 * 
	 * @param str
	 *            目标字符串，如果为null则返回null
	 * @param searchStr
	 *            匹配字符串，如果为null则不匹配任何值
	 * @param replaceStr
	 *            替换字符串，如果为null则不替换任何字符串
	 * @return 返回全部替换后的字符串
	 */
	public static String replace(String str, char searchChar, String replaceStr) {
		return replace(str, searchChar + "", replaceStr, true);
	}

	/**
	 * 替换字符串，从索引位置开始. <br>
	 * <br>
	 * <b>示例 </b> <br>
	 * StringTools.replace(&quot;abcde f g&quot;,1,&quot;xx&quot;) 返回值：
	 * &quot;axxde f g&quot;
	 * 
	 * @param str
	 *            目标字符串，不能为null
	 * @param beginIndex
	 *            开始替换索引位置
	 * @param replaceStr
	 *            替换字符串，不能为null
	 * @return 返回字符串
	 */
	public static String replace(String str, int beginIndex, String replaceStr) {
		String result = null;
		if (str == null) {
			return null;
		}
		if (replaceStr == null) {
			replaceStr = "";
		}
		result = str.substring(0, beginIndex) + replaceStr
				+ str.substring(beginIndex + replaceStr.length());
		return result;
	}

	/**
	 * 去除字符串中所有空格. <br>
	 * <br>
	 * <b>示例： </b> <br>
	 * StringUtils.absoluteTrim(&quot; ab cd e &quot;) 返回结果为： &quot;abcde&quot;
	 * 
	 * @param str
	 *            带空格的字符串参数
	 * @return 不带空格的字符串
	 */
	public static String absoluteTrim(String str) {
		String result = replace(str, " ", "");
		return result;
	}

	/**
	 * 英文数字排序
	 * 
	 * @param map
	 * @return Map
	 */
	public static Map<String, String> sortEnglishNumberWord(
			Map<String, String> map) {
		Map<String, String> resultMap = new LinkedHashMap<String, String>(0);
		Map<Integer, String> tempMap = new LinkedHashMap<Integer, String>(0);

		Set<String> keys = map.keySet();

		int s = Integer.MAX_VALUE;
		for (String key : keys) {
			if (key.indexOf("One") > -1) {
				tempMap.put(1, key);
			} else if (key.indexOf("Two") > -1) {
				tempMap.put(2, key);
			} else if (key.indexOf("Three") > -1) {
				tempMap.put(3, key);
			} else if (key.indexOf("Four") > -1) {
				tempMap.put(4, key);
			} else if (key.indexOf("Five") > -1) {
				tempMap.put(5, key);
			} else if (key.indexOf("Six") > -1) {
				tempMap.put(6, key);
			} else if (key.indexOf("Seven") > -1) {
				tempMap.put(7, key);
			} else if (key.indexOf("Eight") > -1) {
				tempMap.put(8, key);
			} else if (key.indexOf("Nine") > -1) {
				tempMap.put(9, key);
			} else {
				tempMap.put(s, key);
			}
			s--;
		}

		Set<Integer> keyNum = tempMap.keySet();
		Object[] num_obj = keyNum.toArray();
		Integer[] nums = new Integer[num_obj.length];
		Integer tempInt = 0;
		for (int i = 0; i < num_obj.length; i++) {
			nums[i] = new Integer(num_obj[i].toString());
		}
		for (int i = 0; i < nums.length; i++) {
			for (int j = 0; j < nums.length - i - 1; j++) {
				if (nums[j] > nums[j + 1]) {
					tempInt = nums[j];
					nums[j] = nums[j + 1];
					nums[j + 1] = tempInt;
				}
			}
		}

		for (Integer num : nums) {
			resultMap.put(tempMap.get(num), map.get(tempMap.get(num)));
		}
		return resultMap;
	}

	/**
	 * 去除所有html标签
	 * 
	 * @param content
	 * @return
	 */
	public static String removeHtml(String content) {
		if (null == content)
			return "";
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		try {
			p_html = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(content);
			content = m_html.replaceAll("");
		} catch (Exception e) {
			return "";
		}
		return content;
	}

	/**
	 * 去除所有html标签
	 * 
	 * @param content
	 * @return
	 */
	public static String removeIframe(String content) {
		if (null == content)
			return "";
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		try {
			p_html = Pattern.compile("<iframe[^>]+>", Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(content);
			content = m_html.replaceAll("");
		} catch (Exception e) {
			return "";
		}
		return content;
	}

	/**
	 * 去除样式加载
	 * 
	 * @param content
	 * @return
	 */
	public static String removeStyle(String content) {
		if (null == content)
			return "";
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		try {
			p_html = Pattern
					.compile(
							"<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>",
							Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(content);
			content = m_html.replaceAll("");
		} catch (Exception e) {
			return "";
		}
		return content;
	}

	/**
	 * 去除脚本
	 * 
	 * @param content
	 * @return
	 */
	public static String removeScript(String content) {
		if (null == content)
			return "";
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		try {
			p_html = Pattern
					.compile(
							"<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>",
							Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(content);
			content = m_html.replaceAll("");
		} catch (Exception e) {
			return "";
		}
		return content;
	}

	/**
	 * 去除空格
	 * 
	 * @param content
	 * @return
	 */
	public static String removeSpace(String content) {
		if (null == content)
			return "";
		return content.replaceAll("\\s*(\\r\\n)\\s*", "")
				.replaceAll(">(\\s+)", ">").replaceAll("(\\s+)<", "<");
	}

	/**
	 * 以regex字符为分割符将list拼接成字符串
	 * 
	 * @param list
	 * @param regex
	 * @return
	 */
	public static String ListToStr(List<String> list, String regex) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
			if (i < list.size() - 1)
				sb.append(regex);
		}
		return sb.toString();
	}

	/**
	 * 以regex字符为分割符将list拼接成字符串
	 * 
	 * @param list
	 * @param regex
	 * @return
	 */
	public static String ArrToStr(Object[] strArr, String regex) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strArr.length; i++) {
			sb.append(strArr[i]);
			if (i < strArr.length - 1)
				sb.append(regex);
		}
		return sb.toString();
	}

	/**
	 * 数组转换成List
	 * 
	 * @param objArr
	 * @return
	 */
	public static List<String> arrToList(String[] strArr) {
		List<String> strList = new ArrayList<String>();
		for (String string : strArr) {
			strList.add(string);
		}
		return strList;
	}

	/**
	 * 字符编码
	 * 
	 * @param str
	 * @param enc
	 *            字符类型 “UTF-8”
	 * @return
	 */
	public static String encode(String str, String enc) {
		String enstr = "";
		if (str == null || str.equals(""))
			return enstr;
		try {
			enstr = URLEncoder.encode(str, enc);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return enstr;
	}

	/**
	 * 字符解码
	 * 
	 * @param str
	 * @param enc
	 *            字符类型 “UTF-8”
	 * @return
	 */
	public static String decode(String str, String enc) {
		String enstr = "";
		if (str == null || str.equals(""))
			return enstr;
		try {
			enstr = URLDecoder.decode(str, enc);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return enstr;
	}

	/**
	 * 使用Base64编码方式给字符串编码
	 * @param str
	 * @return
	 */
	public static String encodeBase64(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s = new BASE64Encoder().encode(b);  
        }  
        return s;  
    }  
	
	/**
	 * 使用Base64方式给字符串解码
	 * @param s
	 * @return
	 */
	public static String decoderBase64(String s) {  
        byte[] b = null;  
        String result = null;  
        if (s != null) {  
            BASE64Decoder decoder = new BASE64Decoder();  
            try {  
                b = decoder.decodeBuffer(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }  
}
