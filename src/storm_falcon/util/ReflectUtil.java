package storm_falcon.util;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class ReflectUtil {
	
	private static final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private ReflectUtil() {}
	
	/**
	 * 获取成员变量值
	 * @param field
	 * @param obj
	 * @return
	 */
	public static Object getFieldValue(Field field, Object obj) {
		try {
			return field.get(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将一个对象的成员变量转换成map键值对
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> parseFields2Map(Object obj) {
		return Arrays.stream(obj.getClass().getDeclaredFields())
			.reduce(new HashMap<>(), 
					(map, field) -> {
						String name = field.getName();
						Object value = getFieldValue(field, obj);
						map.put(name, value);
						return map;
					}, (map, map1) -> {
						map.putAll(map1);
						return map;
					});
	}
	
	/**
	 * 将成员变量转成JSON形式
	 * name="abc"; -> "name":"abc"
	 * @param field
	 * @param obj
	 * @return
	 */
	public static String field2Json(Field field, Object obj) {
		Object value = getFieldValue(field, obj);
		String sValue;
		if (value == null) {
			sValue = "null";
		} else if (value instanceof String) {
			sValue = "\"" + value + "\"";
		} else if (value instanceof Date) {
			sValue = "\"" + formatter.format(value) + "\"";
		} else {
			sValue = value.toString();
		}
		return "\"" + field.getName() + "\":" + sValue; 
	}
	
	/**
	 * 将JSON形式字符串转成Entry
	 * "name":"abc" -> key="name"; value="abc"
	 * @param json
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Entry<String, T> json2Entry(String json) {
		int pos = json.indexOf(':');
		String key = json.substring(0, pos);
		String value = json.substring(pos + 1);
		key = key.replaceAll("\"", "");
		
		if (value.charAt(0) == '\"') {//非数字
			value = value.replaceAll("\"", "");
			try {//Date
				Date date = formatter.parse(value);
				return new SimpleEntry(key, date);
			} catch (Exception e) {//String
				return new SimpleEntry(key, value);
			}
		}
		
		if (value.indexOf('.') != -1) {
			double dValue = Double.parseDouble(value);
			return new SimpleEntry(key, dValue);
		}
		
		int nValue = Integer.parseInt(value);
		return new SimpleEntry(key, nValue);
	}
	
	/**
	 * 将Entry对应的成员变量赋值
	 * @param entry
	 * @param t
	 * @return
	 */
	public static <T> T setFieldValue(Entry<String, Object> entry, T t) {
		try {
			Field field = t.getClass().getField(entry.getKey());
			field.set(t, entry.getValue());
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * T t = new T;
	 * @param cls
	 * @return
	 */
	public static <T> T newInstance(Class<T> cls) {
		try {
			return cls.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
