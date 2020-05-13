package DAO;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.MovieDao;
import model.Movie;

public class SelectMovieByIdTest {
	
	private MovieDao movieDao;
	
	@BeforeEach
	public void setup() {
		movieDao = MovieDao.getInstance();
	}
	
	
	@Test
	public void selectMovieByIdTest() {
		Movie m1 = new Movie("movie1", 7, 8, 100, 30.0, "Shopping city");
		Movie m2 = new Movie("movie2", 10, 11, 50, 20.0, "Iulius mall");
		Movie m3 = new Movie("movie2", 7, 9, 200, 35.0, "Iulius mall");

		movieDao.addMovie(m1);
		movieDao.addMovie(m2);
		movieDao.addMovie(m3);
		
		assertEquals(m2, movieDao.findMovie(m2.getId()));
		
		
	}
	
}
