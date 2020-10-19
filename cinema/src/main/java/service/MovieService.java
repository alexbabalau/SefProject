package service;

import dao.BookingDao;
import dao.MovieDao;
import model.Movie;

import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

public class MovieService {
	private MovieDao movieDao;
	private BookingDao bookingDao;
	
	
	private static MovieService instance;
	private DataSource dataSource;
	
	private MovieService(DataSource dataSource) {
		movieDao = MovieDao.getInstance(dataSource);
		bookingDao = BookingDao.getInstance(dataSource);
	}
	
	public static MovieService getInstance(DataSource dataSource) {
		if(instance == null) {
			instance = new MovieService(dataSource);
		}
		return instance;
	}
	
	public void deleteMovie(Integer id, boolean forUpdate) {
		if(!forUpdate)
			bookingDao.deleteByMovieId(id);
		movieDao.deleteMovie(id);
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
		movie.setFreeSeats(movie.getFreeSeats() + addSeats);
		movieDao.updateMovie(id, movie);
	}
	
	public List<Movie> getMoviesFromCinema(Integer cinemaId){
		List<Movie> movies = movieDao.getMovies();
		
		return movies.stream().filter(movie -> Integer.valueOf(movie.getCinemaId()).equals(cinemaId)).collect(Collectors.toList());
	}
	
	public void updateMovie(Integer id, Movie movie) {
		movieDao.updateMovie(id, movie);
	}
	
	public Movie getMovieByTitle(String title) {
		return movieDao.getMovieByTitle(title);
	}
	
	public void close() {
		//movieDao.close();
		instance = null;
	}
}
