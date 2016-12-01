package storm_falcon.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void log(String title, String msg) {
		System.out.println(df.format(new Date()) + " " + title);
		System.out.println(msg);
		System.out.println();
	}
}
