package storm_falcon.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import storm_falcon.bean.Order;
import storm_falcon.bean.OrderManager;
import storm_falcon.bean.VIP;
import storm_falcon.bean.VIPManager;
import storm_falcon.util.Logger;
import storm_falcon.util.NumberHelper;
import storm_falcon.util.ServletUtil;

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
		
		String name = request.getParameter("name");
		String money = request.getParameter("money");
		String vipNo = request.getParameter("vipNo");
		
		//search vip
		VIPManager vMgr = VIPManager.getInstance();
		VIP vip = vMgr.getByNo(vipNo);
		
		//check credit
		double cost = 0;
		try {
			cost = vip.level * Double.parseDouble(money) * 0.1;
			cost = NumberHelper.format(cost);
		} catch (Exception e) {
			ServletUtil.sendToClient(response, "金额错误");
			return;
		}
		if (vip.credit < cost) {
			ServletUtil.sendToClient(response, "余额不足");
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
		vMgr.flush();
		
		Logger.log("Add order.", order.toString());
		
		ServletUtil.sendToClient(response, "消费成功");
	}

}
