package storm_falcon.bean;

import java.util.function.BiFunction;

public class OrderManager extends BaseManager<Order> {

	private OrderManager() {
		super("order.data");
	}
	
	private static OrderManager mInstance = null;
	public static OrderManager getInstance() {
		if (mInstance == null) {
			mInstance = new OrderManager();
		}
		return mInstance;
	}

	@Override
	protected BiFunction<Integer, String, Order> fileParser() {
		return (lineNum, line) -> BaseVO.parseJson(line, Order.class);
	}

}
