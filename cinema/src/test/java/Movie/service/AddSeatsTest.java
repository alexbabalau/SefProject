package Movie.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Movie;
import service.MovieService;

public class AddSeatsTest {
	
	private MovieService movieService;
	
	@BeforeEach
	public void setup() {
		movieService = MovieService.getInstance();
	}
	
	@Test
	public void addSeatsTest() {
		
		int add = 5;
		
		int seats = 0;
		
		Movie m1 = new Movie("movie1", 7, 8, 100, 30.0, "Shopping city");
		Movie m2 = new Movie("movie2", 10, 11, 50, 20.0, "Iulius mall");
		Movie m3 = new Movie("movie2", 7, 9, 200, 35.0, "Iulius mall");
		
		movieService.addMovie(m1);
		movieService.addMovie(m2);
		movieService.addMovie(m3);
		
		seats = m2.getFreeSeats() + add;
		
		movieService.addSeats(m2.getId(), add);
		
		assertEquals(movieService.findMovie(m2.getId()).getFreeSeats(), seats);
		
	}
	
	@AfterEach
	public void destroy() {
		movieService.close();
	}
}
