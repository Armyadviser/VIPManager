package storm_falcon.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import storm_falcon.bean.Order;
import storm_falcon.bean.OrderManager;
import storm_falcon.util.SubPager;

public class SearchOrder extends HttpServlet {

	private static final long serialVersionUID = -3317415806461144749L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		String key = request.getParameter("key");
		int page = Integer.parseInt(request.getParameter("page"));
		int size = Integer.parseInt(request.getParameter("size"));
		
		OrderManager manager = OrderManager.getInstance();
		List<Order> list = manager.search(key);
		int[] nPages = SubPager.getPages(size, list.size());
		list = subList(list, page, size);
		
		PrintWriter out = response.getWriter();
		out.print("{\"page\":" + Arrays.toString(nPages) + ",\"data\":" + list.toString() + "}");
		out.flush();
		out.close();
	}
	
	private List<Order> subList(List<Order> list, int page, int size) {
		int from = (page - 1) * size;
		int to = from + size;
		
		if (to > list.size()) {
			to = list.size();
		}
		return list.subList(from, to);
	}

}
