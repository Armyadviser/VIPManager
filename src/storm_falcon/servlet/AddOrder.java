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
		String vipName = request.getParameter("vipName");
		
		OrderManager manager = OrderManager.getInstance();

		Order order = new Order();
		order.date = new Date();
		order.money = Double.parseDouble(money);
		order.vipNo = vipNo;
		order.vipName = vipName;
		order.name = name;
		order.delsign = 1;
		order.no = manager.getNextNo();

		manager.add(order);
		manager.flush();

		// TODO response
		PrintWriter out = response.getWriter();
		out.print(this.getClass());
		out.flush();
		out.close();
	}

}
