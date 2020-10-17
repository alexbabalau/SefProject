package service;

import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import dao.BookingDao;
import dao.CinemaDao;
import dao.MovieDao;
import dao.UserDao;
import model.Booking;
import model.Movie;
import model.MovieAndBooking;
import model.MovieBookingAndUser;

public class BookingService {
	private BookingDao bookingDao;
	private MovieService movieService;
	private UserDao userDao;
	private CinemaDao cinemaDao;
	private static BookingService instance;
	private DataSource dataSource;
	
	private BookingService(DataSource dataSource) {
		bookingDao = BookingDao.getInstance(dataSource);
		movieService = MovieService.getInstance(dataSource);
		userDao = UserDao.getInstance(dataSource);
		cinemaDao = CinemaDao.getInstance(dataSource);
	}
	
	public static BookingService getInstance(DataSource dataSource) {
		if(instance == null) {
			instance = new BookingService(dataSource);
		}
		return instance;
	}
	
	public void addBooking(Booking booking) throws NotEnoughSeatsException{
		Movie movie = movieService.findMovie(booking.getMovieId());
		
		if(movie.getFreeSeats() < booking.getSelectedSeats())
			throw new NotEnoughSeatsException();
		
		movieService.addSeats(movie.getId(), -booking.getSelectedSeats());
		
		bookingDao.addBooking(booking);
		
	}
	
	public void deleteBooking(Integer id) {
		Booking booking = bookingDao.findBooking(id);
		
		Movie movie = movieService.findMovie(booking.getMovieId());
		
		movieService.addSeats(movie.getId(), booking.getSelectedSeats());
		
		bookingDao.deleteBooking(id);
	}
	
	
	
	public List<Booking> getBookings(){
		return bookingDao.getBookings();
	}
	
	public List<Booking> getBookingsFromUsername(String username) {
		List<Booking> bookings = getBookings();
		return bookings.stream().filter(booking -> booking.getUsername().equals(username)).collect(Collectors.toList());
	}
	
	
	public void close() {
		instance = null;
	}

	public void deleteByMovieId(Integer id) {
		bookingDao.deleteByMovieId(id);
		
	}
	
	public List<MovieAndBooking> getMovieAndBooking(List<Booking> bookings){
		return bookings.stream().map(booking -> new MovieAndBooking(movieService.findMovie(booking.getMovieId()), booking)).collect(Collectors.toList());
	}
	
	public List<Booking> getBookingsFromCinema(String cinema){
		List<Booking> bookings = getBookings();
		Integer cinemaId = cinemaDao.getCinemaIdByName(cinema);
		
		return bookings.stream().filter(booking -> Integer.valueOf(movieService.findMovie(booking.getMovieId()).getCinemaId()).equals(cinemaId)).collect(Collectors.toList());
	}
	
	public List<MovieBookingAndUser> getMovieBookingAndUser(List<Booking> bookings){
		return bookings.stream().map(booking -> new MovieBookingAndUser(movieService.findMovie(booking.getMovieId()), booking, userDao.getUser(booking.getUsername()))).collect(Collectors.toList());
	}
}
