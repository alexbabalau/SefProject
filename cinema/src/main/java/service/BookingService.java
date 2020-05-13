package service;

import java.util.List;
import java.util.stream.Collectors;

import dao.BookingDao;
import dao.MovieDao;
import model.Booking;
import model.Movie;

public class BookingService {
	private BookingDao bookingDao;
	private MovieService movieService;
	private static BookingService instance;
	
	private BookingService() {
		bookingDao = BookingDao.getInstance();
		movieService = MovieService.getInstance();
	}
	
	public static BookingService getInstance() {
		if(instance == null) {
			instance = new BookingService();
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
		bookingDao.close();
	}

	public void deleteByMovieId(Integer id) {
		bookingDao.deleteByMovieId(id);
		
	}
}
