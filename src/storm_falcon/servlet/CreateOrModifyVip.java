package storm_falcon.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import storm_falcon.bean.VIP;
import storm_falcon.bean.VIPManager;

public class CreateOrModifyVip extends HttpServlet {

	private static final long serialVersionUID = 5058600489726432378L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		String name = request.getParameter("name");
		String tel = request.getParameter("tel");
		String credit = request.getParameter("credit");
		String level = request.getParameter("level");
		
		VIPManager manager = VIPManager.getInstance();

		VIP vip = new VIP();
		vip.no = manager.getNextNo();
		vip.name = name;
		vip.tel = tel;
		vip.date = new Date();
		vip.credit = Double.parseDouble(credit);
		vip.point = 0;
		vip.level = Integer.parseInt(level);
		vip.delsign = 1;
		
		manager.add(vip);
		manager.flush();
		
		System.out.println(new Date() + " add vip.");
		System.out.println(vip);
		
		response.sendRedirect("info.jsp");
	}

}
