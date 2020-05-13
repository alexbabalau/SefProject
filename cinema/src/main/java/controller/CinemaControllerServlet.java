package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Admin;
import model.Manager;
import model.Regular;
import service.RequestService;
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
	private RequestService requestService;
	private ServletContext servletContext;
	
	@Override
	public void init() throws ServletException {
		super.init();
		userService = new UserService();

		requestService = new RequestService();
		userService.addUser(new Admin("admin", "admin", "07xxxxxx", "Admin", "admin@admin.ro"));

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
			case "REGISTER": handleRegisterRequest(request, response);
							break;
			case "ACCEPT": handleAcceptRequest(request, response);
							break;
			case "DENY": handleDenyRequest(request, response);
							break;
		}
	}

	private void handleLoginRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = null;
		String username = request.getParameter("username");
		if(userService.areValidCredentials(request.getParameter("username"), request.getParameter("password"))) {
			
			request.getServletContext().setAttribute("username", request.getParameter("username"));
			
			switch(userService.getRole(username)) {
				case "admin": request.setAttribute("manager_list", requestService.getRequests());
							  requestDispatcher = request.getRequestDispatcher("admin-requests.jsp");
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
	
	private void handleRegisterRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = null;
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String role = request.getParameter("role");
		String cinemaName = request.getParameter("cinemaName");

		if (!userService.existUser(username)) {
			switch(role) {
				case "manager" : requestService.addRequest(new Manager(username, password, phone, name, email, cinemaName));
					break;
				case "regular" : userService.addUser(new Regular(username, password, phone, name, email), false);
					break;		
			}
		}
		
		requestDispatcher = request.getRequestDispatcher("login-form.html");
		requestDispatcher.forward(request, response);
	}
	
	private void handleAcceptRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = null;
		
		String username = request.getParameter("username");
		
		requestService.approveRequest(username);
		
		request.setAttribute("manager_list", requestService.getRequests());
		requestDispatcher = request.getRequestDispatcher("admin-requests.jsp");
		requestDispatcher.forward(request, response);
	}
	
	private void handleDenyRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = null;
		
		String username = request.getParameter("username");
		
		requestService.denyRequest(username);
		
		request.setAttribute("manager_list", requestService.getRequests());
		requestDispatcher = request.getRequestDispatcher("admin-requests.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	@Override
	public void destroy() {
		userService.close();
		requestService.close();
	}

	
}
