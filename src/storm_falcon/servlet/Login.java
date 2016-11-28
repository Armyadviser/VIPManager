package storm_falcon.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import storm_falcon.bean.OrderManager;
import storm_falcon.bean.VIPManager;
import storm_falcon.file.FileReader;
import storm_falcon.util.MD5;

public class Login extends HttpServlet {

	private static final long serialVersionUID = -7740129234194118797L;

	private String password;

	@Override
	public void init() throws ServletException {
		String initDataFile = getServletContext().getRealPath("") + "json/initialize.json";
		password = FileReader.lines(initDataFile, "utf-8")
			.filter(line -> line.contains("password"))
			.map(line -> line.substring(0, line.length() - 1))
			.map(line -> line.substring(line.lastIndexOf('\"') + 1))
			.findFirst().get();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		
		String pwd = request.getParameter("password");
		pwd = MD5.encode(pwd);
		
		if (pwd.equals(password)) {
			VIPManager.getInstance().reload();
			OrderManager.getInstance().reload();
			request.getSession().setAttribute("pwd", pwd);
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	}
}
