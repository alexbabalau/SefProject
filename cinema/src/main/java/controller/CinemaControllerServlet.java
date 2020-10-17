package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.Admin;
import model.Booking;
import model.Manager;
import model.Movie;
import model.MovieAndBooking;
import model.Regular;
import service.BookingService;
import service.CinemaService;
import service.MovieService;
import service.NotEnoughSeatsException;
import service.RequestService;
import service.UserService;

/**
 * Servlet implementation class CinemaControllerServlet
 */
public class CinemaControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DataSource dataSource;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	private UserService userService;
	private RequestService requestService;
	private MovieService movieService;
	private BookingService bookingService;
	private ServletContext servletContext;
	private CinemaService cinemaService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		Context initContext;
		try {
			initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			dataSource = (DataSource)envContext.lookup("jdbc/cinema_tracker");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userService = UserService.getInstance(dataSource);
		movieService = MovieService.getInstance(dataSource);
		//bookingService = BookingService.getInstance();
		cinemaService = CinemaService.getInstance(dataSource);
		
		userService.addUserTest();
		
		requestService = RequestService.getInstance(dataSource);
		userService.addUser(new Admin("admin", "admin", "07xxxxxx", "Admin", "admin@admin.ro"), false);
		
		/*userService.addUser(new Manager("manager1", "manager1", "07xxxxx", "Manager1", "manager1@manager1.ro", "CineDaria"), false);
		userService.addUser(new Manager("manager2", "manager2", "07xxxxx", "Manager2", "manager2@manager2.ro", "CineMax"), false);
		userService.addUser(new Regular("daria", "daria", "07xxxxxx", "Daria", "daria@daria.ro"), false);*/
		
		/*movieService.addMovie(new Movie("Movie1", 7, 8, 100, 30.0, "CineDaria"));
		movieService.addMovie(new Movie("Movie2", 7, 8, 120, 30.0, "CineDaria"));
		movieService.addMovie(new Movie("Movie3", 7, 8, 10, 30.0, "CineMax"));
		movieService.addMovie(new Movie("Movie4", 7, 8, 1200, 30.0, "CineDaria"));
		movieService.addMovie(new Movie("Movie5", 7, 8, 120, 30.0, "CineMax"));*/
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
		String username = (String)getServletContext().getAttribute("username");
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Last-Modified", (new Date()).getTime() );
		
		
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
			case "ADDMOVIEID": handleAddMovieIdRequest(request, response);
								break;
			case "UPDATE-MOVIE" : handleUpdateMovieRequest(request, response);
							break;
			case "DELETE-MOVIE" : handleDeleteMovieRequest(request, response);
							break;
			case "SEE-MOVIES" : handleSeeMoviesRequest(request, response);
								break;
			case "BOOK" : handleBookRequest(request, response);
						break;
			case "BOOK-MOVIE" : handleBookMovieRequest(request, response);
								break;
			case "SEE-BOOKINGS" : handleSeeBookingsRequest(request, response);
								break;
			case "DELETE-BOOKING" : handleDeleteBookingRequest(request, response);
									break;
			case "MANAGER-BOOKING":handleManagerBookingRequest(request, response);
									break;
			case "LOGOUT": handleLogoutRequest(request, response);
							break;
			case "SEE-CINEMAS" : handleSeeCinemasRequest(request, response);
								break;
			case "HOME" : handleHomeRequest(request, response);
						break;
		}
	}

	private void handleLogoutRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = (String) getServletContext().getAttribute("username");
		RequestDispatcher requestDispatcher = null;
		
		getServletContext().setAttribute("username", null);
		
		username = (String) getServletContext().getAttribute("username");
		
		requestDispatcher = request.getRequestDispatcher("login-form.html");
		requestDispatcher.forward(request, response);
	}

	private void handleManagerBookingRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String) getServletContext().getAttribute("username");
		String role = userService.getRole(username);
		RequestDispatcher requestDispatcher = null;
		
		
		Integer cinemaId = userService.getCinemaId(username);
		String cinema = cinemaService.getCinemaName(cinemaId);
		
		List<Booking> bookingsFromCinema = bookingService.getBookingsFromCinema(cinema);
		
		
		request.setAttribute("movie_booking_and_user_list", bookingService.getMovieBookingAndUser(bookingsFromCinema));
		
		requestDispatcher = request.getRequestDispatcher("manager-bookings.jsp");
		
		requestDispatcher.forward(request, response);
	}

	private void handleLoginRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String) getServletContext().getAttribute("username");
		
		RequestDispatcher requestDispatcher = null;
		
		username = request.getParameter("username");
		if(userService.areValidCredentials(request.getParameter("username"), request.getParameter("password"))) {
			
			getServletContext().setAttribute("username", request.getParameter("username"));
			
			switch(userService.getRole(username)) {
				case "admin": request.setAttribute("manager_list", requestService.getRequests());
							  requestDispatcher = request.getRequestDispatcher("admin-requests.jsp");
							  break;
				case "manager" : request.setAttribute("movie_list", movieService.getMoviesFromCinema(userService.getCinemaId(username)));
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
	
	private void handleSeeCinemasRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String) getServletContext().getAttribute("username");
		RequestDispatcher requestDispatcher = null;
		
		request.setAttribute("manager_list", userService.getManagers()); 
		requestDispatcher = request.getRequestDispatcher("select-cinema.jsp");
				
		requestDispatcher.forward(request, response);
		
	}
	
	private void handleRegisterRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String) getServletContext().getAttribute("username");
		RequestDispatcher requestDispatcher = null;
		
		username = request.getParameter("username");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String role = request.getParameter("role");
		String cinemaName = request.getParameter("cinemaName");

		if (!userService.existUser(username)) {
			switch(role) {
				case "manager" : requestService.addRequest(new Manager(username, password, phone, name, email));
					break;
				case "regular" : userService.addUser(new Regular(username, password, phone, name, email), false);
					break;		
			}
		}
		
		requestDispatcher = request.getRequestDispatcher("login-form.html");
		requestDispatcher.forward(request, response);
	}
	
	private void handleAcceptRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String) getServletContext().getAttribute("username");
		String role = userService.getRole(username);
		RequestDispatcher requestDispatcher = null;
		
		username = request.getParameter("username");
		
		requestService.approveRequest(username);
		
		request.setAttribute("manager_list", requestService.getRequests());
		requestDispatcher = request.getRequestDispatcher("admin-requests.jsp");
		
		requestDispatcher.forward(request, response);
	}
	
	private void handleDenyRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String) getServletContext().getAttribute("username");
		String role = userService.getRole(username);
		RequestDispatcher requestDispatcher = null;
		
		username = request.getParameter("username");
		
		requestService.denyRequest(username);
		
		request.setAttribute("manager_list", requestService.getRequests());
		requestDispatcher = request.getRequestDispatcher("admin-requests.jsp");
		
		requestDispatcher.forward(request, response);
	}
	
	private void handleAddMovieRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String) getServletContext().getAttribute("username");
		String role = userService.getRole(username);
		RequestDispatcher requestDispatcher = null;
		
		String title = request.getParameter("title");
		int freeSeats = Integer.parseInt(request.getParameter("freeSeats"));
		int startHour = Integer.parseInt(request.getParameter("startHour"));
		int endHour = Integer.parseInt(request.getParameter("endHour"));
		double price = Double.parseDouble(request.getParameter("price"));
		
		username = (String) (getServletContext().getAttribute("username"));
		
		int cinema = userService.getCinemaId(username);

		movieService.addMovie(new Movie(title, startHour, endHour, freeSeats, price, cinema));
		
		request.setAttribute("movie_list", movieService.getMoviesFromCinema(userService.getCinemaId(username)));
		requestDispatcher = request.getRequestDispatcher("manager-movies.jsp");
		
		requestDispatcher.forward(request, response);
	}
	
	private void handleAddMovieIdRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String) getServletContext().getAttribute("username");
		String role = userService.getRole(username);
		RequestDispatcher requestDispatcher = null;
		
		String title = request.getParameter("title");
		int freeSeats = Integer.parseInt(request.getParameter("freeSeats"));
		int startHour = Integer.parseInt(request.getParameter("startHour"));
		int endHour = Integer.parseInt(request.getParameter("endHour"));
		double price = Double.parseDouble(request.getParameter("price"));
		int id = Integer.parseInt(request.getParameter("id"));
		
		username = (String) (getServletContext().getAttribute("username"));
		
		int cinemaId = userService.getCinemaId(username);

		movieService.updateMovie(id, new Movie(title, startHour, endHour, freeSeats, price, cinemaId));
		
		request.setAttribute("movie_list", movieService.getMoviesFromCinema(userService.getCinemaId(username)));
		requestDispatcher = request.getRequestDispatcher("manager-movies.jsp");
		
		requestDispatcher.forward(request, response);
	}
	
	private void handleUpdateMovieRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String) getServletContext().getAttribute("username");
		String role = userService.getRole(username);
		RequestDispatcher requestDispatcher = null;
	
		String id = request.getParameter("id");
		
		Movie movie = movieService.findMovie(Integer.parseInt(id));
		
		request.setAttribute("movie", movie);
		
		movieService.deleteMovie(Integer.parseInt(id), true);
		
		requestDispatcher = request.getRequestDispatcher("movie-update-form.jsp");
		
		requestDispatcher.forward(request, response);
	}
	
	private void handleDeleteMovieRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String) getServletContext().getAttribute("username");
		String role = userService.getRole(username);
		RequestDispatcher requestDispatcher = null;
		
		String id = request.getParameter("id");
		
		movieService.deleteMovie(Integer.parseInt(id), false);
		
		username = (String) (getServletContext().getAttribute("username"));
		
		Integer cinemaId = userService.getCinemaId(username);

		
		request.setAttribute("movie_list", movieService.getMoviesFromCinema(cinemaId));
		requestDispatcher = request.getRequestDispatcher("manager-movies.jsp");
		
		requestDispatcher.forward(request, response);
	}

	private void handleSeeMoviesRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String) getServletContext().getAttribute("username");
		String role = userService.getRole(username);
		RequestDispatcher requestDispatcher = null;
		
		String cinemaName = request.getParameter("cinema");
		Integer cinemaId = cinemaService.getCinemaIdByName(cinemaName);
		
		request.setAttribute("movie_list", movieService.getMoviesFromCinema(cinemaId));
		requestDispatcher = request.getRequestDispatcher("select-movie.jsp");
		
		requestDispatcher.forward(request, response);
	}
	
	private void handleBookMovieRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String) getServletContext().getAttribute("username");
		String role = userService.getRole(username);
		RequestDispatcher requestDispatcher = null;
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		request.setAttribute("id", id);
		requestDispatcher = request.getRequestDispatcher("booking-form.jsp");
		
		requestDispatcher.forward(request, response);
	}
	
	private void handleBookRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String) getServletContext().getAttribute("username");
		String role = userService.getRole(username);
		RequestDispatcher requestDispatcher = null;
		
		int id = Integer.parseInt(request.getParameter("id"));
		int places = Integer.parseInt(request.getParameter("selectedSeats"));
		
		Movie movie = movieService.findMovie(id);
		username = (String) (getServletContext().getAttribute("username"));
		
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
	
	private void handleSeeBookingsRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = null;
		
		String username = (String) (request.getServletContext().getAttribute("username"));
		
		List<Booking> bookings = bookingService.getBookingsFromUsername(username);
		
		List<MovieAndBooking> movieBooking = bookingService.getMovieAndBooking(bookings);
		
		request.setAttribute("booking_list", movieBooking);
		requestDispatcher = request.getRequestDispatcher("user-bookings.jsp");
		
		requestDispatcher.forward(request, response);
	}
	
	private void handleDeleteBookingRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = null;
		String id = request.getParameter("id");
		
		bookingService.deleteBooking(Integer.parseInt(id));
		
		String username = (String) (request.getServletContext().getAttribute("username"));
		
		List<Booking> bookings = bookingService.getBookingsFromUsername(username);

		List<MovieAndBooking> movieBooking = bookingService.getMovieAndBooking(bookings);
		
		
		request.setAttribute("booking_list", movieBooking);
		requestDispatcher = request.getRequestDispatcher("user-bookings.jsp");
		
		requestDispatcher.forward(request, response);
	}
	
	private void handleHomeRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = null;
		
		String username = (String) (request.getServletContext().getAttribute("username"));
		
		switch(userService.getRole(username)) {
			case "admin": request.setAttribute("manager_list", requestService.getRequests());
					  	requestDispatcher = request.getRequestDispatcher("admin-requests.jsp");
					  	break;
			case "manager" : request.setAttribute("movie_list", movieService.getMoviesFromCinema(userService.getCinemaId(username)));
							requestDispatcher = request.getRequestDispatcher("manager-movies.jsp");
							break;
			default:request.setAttribute("manager_list", userService.getManagers()); 
					requestDispatcher = request.getRequestDispatcher("select-cinema.jsp");
		
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
