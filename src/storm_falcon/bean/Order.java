package storm_falcon.bean;

public class Order extends BaseVO {
	
	private static final long serialVersionUID = -4078721370937663206L;
	
	public String vipNo;
	public String vipName;
	public String name;//服务、商品名
	public double money;
	
	@Override
	public String getKeyword() {
		return no + vipNo + vipName + name;
	}

}
