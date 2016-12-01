package storm_falcon.util;

import java.text.DecimalFormat;

public class NumberHelper {

	private static DecimalFormat df = new DecimalFormat("#.##");
	
	public static synchronized double format(double num) {
		return Double.parseDouble(df.format(num));
	}
}
