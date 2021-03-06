package storm_falcon.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import storm_falcon.bean.VIP;
import storm_falcon.bean.VIPManager;
import storm_falcon.util.Logger;
import storm_falcon.util.ServletUtil;

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
		
		try {
			double d = Double.parseDouble(credit);
			if (d < 0) {
				throw new Exception();
			}
			d = Double.parseDouble(level);
			if (d <= 0 || d > 10) {
				throw new Exception();
			}
		} catch (Exception e) {
			ServletUtil.sendToClient(response, "数据错误");
			return;
		}
		
		VIPManager manager = VIPManager.getInstance();

		VIP vip = new VIP();
		vip.no = manager.getNextNo();
		vip.name = name;
		vip.tel = tel;
		vip.date = new Date();
		vip.credit = Double.parseDouble(credit);
		vip.point = 0;
		vip.level = Double.parseDouble(level);
		vip.delsign = 1;
		
		manager.add(vip);
		manager.flush();
		
		Logger.log("Add vip.", vip.toString());
		
		ServletUtil.sendToClient(response, "创建成功");
	}

}
