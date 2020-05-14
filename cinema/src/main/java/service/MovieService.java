package service;

import dao.BookingDao;
import dao.MovieDao;
import model.Movie;

import java.util.List;
import java.util.stream.Collectors;

public class MovieService {
	private MovieDao movieDao;
	private BookingDao bookingDao;
	
	
	private static MovieService instance;
	
	private MovieService() {
		movieDao = MovieDao.getInstance();
		bookingDao = BookingDao.getInstance();
	}
	
	public static MovieService getInstance() {
		if(instance == null) {
			instance = new MovieService();
		}
		return instance;
	}
	
	public void deleteMovie(Integer id) {
		movieDao.deleteMovie(id);
		bookingDao.deleteByMovieId(id);
	}
	
	public List<Movie> getMovies(){
		return movieDao.getMovies();
	}
	
	public void addMovie(Movie movie) {
		movieDao.addMovie(movie);
	}
	
	public Movie findMovie(Integer id) {
		return movieDao.findMovie(id);
	}
	
	public void addSeats(Integer id, int addSeats) {
		Movie movie = movieDao.findMovie(id);
		movieDao.deleteMovie(id);
		movie.setFreeSeats(movie.getFreeSeats() + addSeats);
		movieDao.addMovie(movie);
	}
	
	public List<Movie> getMoviesFromCinema(String cinema){
		List<Movie> movies = movieDao.getMovies();
		
		return movies.stream().filter(movie -> movie.getCinema().equals(cinema)).collect(Collectors.toList());
	}
	
	public void close() {
		movieDao.close();
	}
}
