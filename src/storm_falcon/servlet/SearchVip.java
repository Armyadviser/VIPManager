package storm_falcon.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import storm_falcon.bean.VIP;
import storm_falcon.bean.VIPManager;
import storm_falcon.util.SubPager;

public class SearchVip extends HttpServlet {

	private static final long serialVersionUID = 5535621393620539679L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json");
		
		String key = request.getParameter("key");
		int page = Integer.parseInt(request.getParameter("page"));
		int size = Integer.parseInt(request.getParameter("size"));
		
		List<VIP> list = search(key);
		int[] nPages = SubPager.getPages(size, list.size());
		list = SubPager.subList(list, page, size);
		
		PrintWriter out = response.getWriter();
		String res = "{\"page\":" + Arrays.toString(nPages) + ",\"data\":" + list.toString() + "}";
		out.print(res);
		out.flush();
		out.close();
	}
	
	private List<VIP> search(String key) {
		VIPManager manager = VIPManager.getInstance();
		List<VIP> list = manager.search(key);
		list = list.stream()
			.filter(vip -> vip.delsign == 1)
			.collect(Collectors.toList());
		return list;
	}
}
