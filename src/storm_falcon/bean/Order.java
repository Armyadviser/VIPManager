package storm_falcon.bean;

public class Order extends BaseVO {
	
	public String vipNo;
	public String vipName;
	public String product;
	public double money;
	
	@Override
	public String getKeyword() {
		return no + vipNo + vipName + product;
	}

}
