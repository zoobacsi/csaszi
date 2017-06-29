package hu.csaszi.login.servlets;

import com.sun.istack.internal.logging.Logger;

import hu.csaszi.login.model.User;
import hu.csaszi.login.utils.LoginValidation;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet(name = "Login", urlPatterns = "/login")
public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(Login.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String login = request.getParameter("login");
		String pass = request.getParameter("pass");

		User user = LoginValidation.loginValidate(login, pass);

		if (user != null) {

			// RequestDispatcher rd = request.getRequestDispatcher("welcome");
			// rd.forward(request, response);
			request.setAttribute("error", "");
			request.setAttribute("user", user);
			
			//ServletContext context = getServletContext();
			RequestDispatcher rd = request.getRequestDispatcher("/");
			rd.forward(request, response);

			// response.sendRedirect("welcomes");
		} else {

			//request.setCharacterEncoding("UTF-8");
			request.setAttribute("error", "login");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");

			rd.include(request, response);

		}

	}

}
