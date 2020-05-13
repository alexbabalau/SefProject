package booking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Booking;
import model.Movie;
import service.BookingService;
import service.MovieService;
import service.NotEnoughSeatsException;

public class AddBookingTest {
	
	private BookingService bookingService;
	private MovieService movieService;
	
	@BeforeEach
	public void setup() {
		movieService = MovieService.getInstance();
		bookingService = BookingService.getInstance();
	}
	
	@Test
	public void addBookingTestNoThrowing() {
		Movie m1 = new Movie("movie1", 7, 8, 100, 30.0, "Shopping city");
		Movie m2 = new Movie("movie2", 10, 11, 50, 20.0, "Iulius mall");
		Movie m3 = new Movie("movie2", 7, 9, 200, 35.0, "Iulius mall");

		movieService.addMovie(m1);
		movieService.addMovie(m2);
		movieService.addMovie(m3);
		
		Booking booking = new Booking("alex", m1.getId(), 10);
		
		try{
			bookingService.addBooking(booking);
		}
		catch(NotEnoughSeatsException e) {
			
		}
		assertEquals(90, movieService.findMovie(m1.getId()).getFreeSeats());
	}
	
	@Test
	public void addBookingTestThrowing() {
		Movie m1 = new Movie("movie1", 7, 8, 100, 30.0, "Shopping city");
		Movie m2 = new Movie("movie2", 10, 11, 50, 20.0, "Iulius mall");
		Movie m3 = new Movie("movie2", 7, 9, 200, 35.0, "Iulius mall");

		movieService.addMovie(m1);
		movieService.addMovie(m2);
		movieService.addMovie(m3);
		
		Booking booking = new Booking("alex", m1.getId(), 101);
		
		assertThrows(NotEnoughSeatsException.class, () -> bookingService.addBooking(booking));
		
	}
	
}
