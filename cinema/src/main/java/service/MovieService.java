package service;

import dao.MovieDao;
import model.Movie;

import java.util.List;

public class MovieService {
	private MovieDao movieDao;
	
	public MovieService() {
		movieDao = MovieDao.getInstance();
	}
	
	public void deleteMovie(Integer id) {
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

}
