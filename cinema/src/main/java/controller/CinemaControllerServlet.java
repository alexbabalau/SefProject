package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.UserService;

/**
 * Servlet implementation class CinemaControllerServlet
 */
public class CinemaControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	private UserService userService;
	private ServletContext servletContext;
	
	@Override
	public void init() throws ServletException {
		super.init();
		userService = new UserService();
		
	}
	
    public CinemaControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getParameter("command");
		String username = (String)request.getServletContext().getAttribute("username");
		switch(command) {
			case "LOGIN": handleLoginRequest(request, response);
						  break;
		}
	}

	private void handleLoginRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = null;
		String username = request.getParameter("username");
		if(userService.areValidCredentials(request.getParameter("username"), request.getParameter("password"))) {
			
			request.getServletContext().setAttribute("username", request.getParameter("username"));
			
			switch(userService.getRole(username)) {
				case "admin": requestDispatcher = request.getRequestDispatcher("admin-requests.jsp");
							  break;
				case "manager" :requestDispatcher = request.getRequestDispatcher("manager-movies.jsp");
								break;
				default: requestDispatcher = request.getRequestDispatcher("select-cinema.jsp");
				
			}
		}
			
		else
			requestDispatcher = request.getRequestDispatcher("login-form.html");
		requestDispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
}
