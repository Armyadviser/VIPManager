package storm_falcon.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import storm_falcon.bean.Order;
import storm_falcon.bean.OrderManager;
import storm_falcon.bean.VIP;
import storm_falcon.bean.VIPManager;

public class AddOrder extends HttpServlet {

	private static final long serialVersionUID = -7756618105101546047L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		PrintWriter out = response.getWriter();
		
		String name = request.getParameter("name");
		String money = request.getParameter("money");
		String vipNo = request.getParameter("vipNo");
		
		//search vip
		VIPManager vMgr = VIPManager.getInstance();
		VIP vip = vMgr.getByNo(vipNo);
		
		//check credit
		double cost = vip.level * Double.parseDouble(money) * 0.1;
		if (vip.credit < cost) {
			out.print("余额不足");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			out.close();
			return;
		}
		
		OrderManager oMgr = OrderManager.getInstance();

		//new order
		Order order = new Order();
		order.date = new Date();
		order.money = cost;
		order.vipNo = vipNo;
		order.vipName = vip.name;
		order.name = name;
		order.delsign = 1;
		order.no = oMgr.getNextNo();

		oMgr.add(order);
		oMgr.flush();
		
		//minus money add point
		VIP newVip = (VIP) vip.clone();
		newVip.credit = vip.credit - cost;
		newVip.point = (int) (vip.point + cost);
		vMgr.modifyByNo(vip.no, newVip);
		
		System.out.println(new Date() + " add order.");
		System.out.println(order);
	}

}
