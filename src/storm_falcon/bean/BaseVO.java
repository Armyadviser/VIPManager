package storm_falcon.bean;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.StringJoiner;

import storm_falcon.util.ReflectUtil;

/**
 * @author Storm_Falcon
 * 子类都有no和date，但是反射时顺序会有影响（先子类后父类）
 */
public abstract class BaseVO {
	
	public String no;
	public Date date;
	public int delsign;//存在标识,0删除；1存在
	
	public String toString() {
		Field[] fields = getClass().getFields();
		return Arrays.stream(fields)
			.reduce(new StringJoiner(", ", "{", "}"), 
					(joiner, field) -> {
						String json = ReflectUtil.field2Json(field, this);
						return joiner.add(json);
					}, StringJoiner::merge
			).toString();
	}
	
	public static <T extends BaseVO> T parseJson(String json, Class<T> cls) {
		final T t = ReflectUtil.newInstance(cls);
		
		json = json.substring(1, json.length() - 1);//去掉前后大括号
		Arrays.stream(json.split(", "))
			.map(ReflectUtil::json2Entry)
			.forEach(entry -> ReflectUtil.setFieldValue(entry, t));
		return t;
	}
	
	public abstract String getKeyword();
}
