package storm_falcon.bean;

import java.util.Date;

public class VIP extends BaseVO {
	
	private static final long serialVersionUID = 5406781341056804301L;
	
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
		
		System.out.println(vip.clone());
		
	}
}
