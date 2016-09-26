package storm_falcon.bean;

public class Order extends BaseVO {
	
	public String vipNo;
	public String vipName;
	public String name;//服务、商品名
	public double money;
	
	@Override
	public String getKeyword() {
		return no + vipNo + vipName + name;
	}

}
