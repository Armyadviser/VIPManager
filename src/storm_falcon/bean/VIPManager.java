package storm_falcon.bean;

import java.util.function.BiFunction;

public class VIPManager extends BaseManager<VIP> {

	private VIPManager() {
		super("vip.data");
	}
	
	private static VIPManager mInstance = null;
	public static VIPManager getInstance() {
		if (mInstance == null) {
			mInstance = new VIPManager();
		}
		return mInstance;
	}
	
	@Override
	protected BiFunction<Integer, String, VIP> fileParser() {
		return (lineNum, line) -> BaseVO.parseJson(line, VIP.class);
	}
}
