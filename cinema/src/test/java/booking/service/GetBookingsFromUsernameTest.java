package booking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Booking;
import model.Movie;
import service.BookingService;
import service.MovieService;
import service.NotEnoughSeatsException;

public class GetBookingsFromUsernameTest {
	private BookingService bookingService;
	private MovieService movieService;
	
	@BeforeEach
	public void setup() {
		movieService = MovieService.getInstance();
		bookingService = BookingService.getInstance();
	}
	
	@Test
	public void getBookingsTest() throws NotEnoughSeatsException {
		Movie m1 = new Movie("movie1", 7, 8, 100, 30.0, "Shopping city");
		Movie m2 = new Movie("movie2", 10, 11, 50, 20.0, "Iulius mall");
		Movie m3 = new Movie("movie2", 7, 9, 200, 35.0, "Iulius mall");

		movieService.addMovie(m1);
		movieService.addMovie(m2);
		movieService.addMovie(m3);
		
		Booking booking1 = new Booking("alex", m1.getId(), 10);
		Booking booking2 = new Booking("alex", m2.getId(), 10);
		Booking booking3 = new Booking("daria", m3.getId(), 10);
		Booking booking4 = new Booking("alex", m1.getId(), 20);
		
		List<Booking> bookings = new ArrayList<>();
		
		bookings.add(booking1);
		bookings.add(booking2);
		bookings.add(booking4);
		
		bookingService.addBooking(booking1);
		bookingService.addBooking(booking2);
		bookingService.addBooking(booking3);
		bookingService.addBooking(booking4);
		
		assertEquals(bookings, bookingService.getBookingsFromUsername("alex"));
		
	}
}