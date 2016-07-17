package storm_falcon.bean;

import java.util.Date;

public class VIP extends BaseVO {
	
	public String name = null;//姓名
	public String tel = null;//电话
	public double credit;//余额
	public int point;//积分
	public int level;//等级

	@Override
	public String getKeyword() {
		return no + name + tel;
	}
	
	public static void main(String[] args) {
		VIP vip = new VIP();
		vip.no = "0001";
		vip.name = "张三";
		vip.tel = "13023842718";
		vip.credit = 43;
		vip.date = new Date();
		vip.point = 20;
		vip.level = 3;
		vip.delsign = 1;
		
		System.out.println(vip.toString());
		
		String json = "{\"name\":\"张三\", \"tel\":\"13023842718\", \"credit\":43.0, \"point\":20, \"level\":3, \"no\":\"0001\", \"date\":\"2016-07-08 17:29:23\", \"delsign\":1}";
		Order order = Order.parseJson(json, Order.class);
		System.out.println(order);
	}
}
