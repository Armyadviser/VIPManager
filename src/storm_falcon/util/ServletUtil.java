package storm_falcon.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ServletUtil {

	public static void sendToClient(HttpServletResponse response, String msg) throws IOException {
		PrintWriter out = response.getWriter();
		out.print(msg);
		out.flush();
		out.close();
	}
}
