package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Admin;
import model.Manager;
import model.Movie;
import model.Regular;
import service.BookingService;
import service.MovieService;
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
	private MovieService movieService;
	private BookingService bookingService;
	private ServletContext servletContext;
	
	@Override
	public void init() throws ServletException {
		super.init();
		userService = UserService.getInstance();
		movieService = MovieService.getInstance();
		bookingService = BookingService.getInstance();
		
		requestService = RequestService.getInstance();
		userService.addUser(new Admin("admin", "admin", "07xxxxxx", "Admin", "admin@admin.ro"), false);
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
			case "ADDMOVIE" : handleAddMovieRequest(request, response);
								break;
			case "UPDATE-MOVIE" : handleUpdateMovieRequest(request, response);
							break;
			case "DELETE-MOVIE" : handleDeleteMovieRequest(request, response);
							break;
			case "SEE-MOVIES" : handleSeeMoviesRequest(request, response);
								break;
			case "BOOK" : handleBookRequest(request, response);
						break;
			case "BOOK_MOVIE" : handleBookMovieRequest(request, response);
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
				case "manager" : request.setAttribute("movie_list", movieService.getMovies());
								requestDispatcher = request.getRequestDispatcher("manager-movies.jsp");
								break;
				default:request.setAttribute("manager_list", userService.getManagers()); 
						requestDispatcher = request.getRequestDispatcher("select-cinema.jsp");
				
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
	
	private void handleAddMovieRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = null;
		
		String title = request.getParameter("title");
		int freeSeats = Integer.parseInt(request.getParameter("freeSeats"));
		int startHour = Integer.parseInt(request.getParameter("startHour"));
		int endHour = Integer.parseInt(request.getParameter("endHour"));
		double price = Double.parseDouble(request.getParameter("price"));
		
		String username = (String) (request.getServletContext().getAttribute("username"));
		
		String cinema = userService.getCinema(username);

		movieService.addMovie(new Movie(title, startHour, endHour, freeSeats, price, cinema));
		
		request.setAttribute("movie_list", movieService.getMovies());
		requestDispatcher = request.getRequestDispatcher("manager-movies.jsp");
		
		requestDispatcher.forward(request, response);
	}
	
	private void handleUpdateMovieRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = null;
		String id = request.getParameter("id");
		
		Movie movie = movieService.findMovie(Integer.parseInt(id));
		
		request.setAttribute("movie", movie);
		
		movieService.deleteMovie(Integer.parseInt(id));
		
		requestDispatcher = request.getRequestDispatcher("movie-update-form.jsp");
		
		requestDispatcher.forward(request, response);
	}
	
	private void handleDeleteMovieRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = null;
		String id = request.getParameter("id");
		
		movieService.deleteMovie(Integer.parseInt(id));
		
		request.setAttribute("movie_list", movieService.getMovies());
		requestDispatcher = request.getRequestDispatcher("manager-movies.jsp");
		
		requestDispatcher.forward(request, response);
	}

	private void handleSeeMoviesRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = null;
		String cinema = request.getParameter("cinema");
		
		request.setAttribute("movie_list", movieService.getMoviesFromCinema(cinema));
		requestDispatcher = request.getRequestDispatcher("select-movie.jsp");
		
		requestDispatcher.forward(request, response);
	}
	
	private void handleBookMoviesRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = null;
		int id = Integer.parseInt(request.getParameter("id")));
		
		request.setAttribute("id", id);
		requestDispatcher = request.getRequestDispatcher("booking-form.jsp");
		
		requestDispatcher.forward(request, response);
	}
	
	private void handleBookRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = null;
		int id = Integer.parseInt(request.getParameter("id")));
		int places = Integer.parseInt(request.getParameter("selectedPlaces")));
		
		Movie movie = movieService.findMovie(id);
		String username = (String) (request.getServletContext().getAttribute("username"));
		
		try {
			bookingService.addBooking(new Booking(username, id, places));
			
			request.setAttribute("manager_list", userService.getManagers());
			requestDispatcher = request.getRequestDispatcher("select-cinema.jsp");
		}
		
		catch (NotEnoughSeatsException e) {
			request.setAttribute("id", id);
			requestDispatcher = request.getRequestDispatcher("booking-form.jsp");
		}
		
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
		movieService.close();
		bookingService.close();
	}

	
}
