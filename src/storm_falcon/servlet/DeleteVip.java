package storm_falcon.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import storm_falcon.bean.VIPManager;

public class DeleteVip extends HttpServlet {

	private static final long serialVersionUID = -242541482915548530L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		
		String no = request.getParameter("no");
		
		VIPManager manager = VIPManager.getInstance();
		manager.delete(no);
		
		manager.flush();
		
		System.out.println(new Date() + " delete vip.");
		System.out.println(no);
		
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
