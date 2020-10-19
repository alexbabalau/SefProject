package dao;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.Admin;
import model.Manager;
import model.Movie;
import model.Regular;
import model.User;


public class MovieDao {
	private static MovieDao instance;
	private DataSource dataSource;
	
	private MovieDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public static MovieDao getInstance(DataSource dataSource) {
		if(instance == null) {
			instance = new MovieDao(dataSource);
		}
		return instance;
	}
	
	public List<Movie> getMovies() {
		List<Movie> movies = new ArrayList<Movie>();
		
		Connection myConnection = null;
		Statement myStatement = null;
		ResultSet myResult = null;
		
		try {
			Movie tempMovie = null;
			
			myConnection = dataSource.getConnection();
			myStatement = myConnection.createStatement();
			
			String selectQuery = "select * from movie";
			
			myResult = myStatement.executeQuery(selectQuery);
			
			while(myResult.next()) {
				int id = myResult.getInt("id_movie");
				String title = myResult.getString("title");
				int startHour = myResult.getInt("start_hour");
				int endHour = myResult.getInt("end_hour");
				int freeSeats = myResult.getInt("free_seats");
				double price = myResult.getDouble("price");
				int idCinema = myResult.getInt("id_cinema");
				
				tempMovie = new Movie(id, title, startHour, endHour, freeSeats, price, idCinema);
				
				movies.add(tempMovie);
			}
			
			return movies;
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		finally {
			close(myConnection, myStatement, myResult);
		}
		
		return movies;
	}
	
	public void addMovie(Movie movie) {
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		
		try {
			myConnection = dataSource.getConnection();
			
			String insertQuery = "insert into movie"
								+ "(id_cinema, title, start_hour, end_hour, price, free_seats)"
								+ "values(?, ?, ?, ?, ?, ?)";
			
			myStatement = myConnection.prepareStatement(insertQuery);
			
			myStatement.setInt(1, movie.getCinemaId());
			myStatement.setString(2, movie.getTitle());
			myStatement.setInt(3, movie.getStartHour());
			myStatement.setInt(4, movie.getEndHour());
			myStatement.setDouble(5, movie.getPrice());
			myStatement.setInt(6, movie.getFreeSeats());
			
			myStatement.execute();
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		finally {
			close(myConnection, myStatement, null);
		}
	}
	
	public void deleteMovie(Integer id) {
		
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		
		try {
			myConnection = dataSource.getConnection();
			
			String deleteQuery = "delete from movie where id_movie=?";
			
			myStatement = myConnection.prepareStatement(deleteQuery);
			
			myStatement.setInt(1, id);
			
			myStatement.execute();
			
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		finally {
			close(myConnection, myStatement, null);
		}
		
	}
	
	public void updateMovie(Integer id, Movie movie) {
		
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		
		try {
			myConnection = dataSource.getConnection();
			
			String updateQuery = "update movie " 
								+ "set id_cinema=?, title=?, start_hour=?, end_hour=?, price=?, free_seats=? "
								+ "where id_movie=?";
			
			myStatement = myConnection.prepareStatement(updateQuery);
			
			myStatement.setInt(1, movie.getCinemaId());
			myStatement.setString(2, movie.getTitle());
			myStatement.setInt(3, movie.getStartHour());
			myStatement.setInt(4, movie.getEndHour());
			myStatement.setDouble(5, movie.getPrice());
			myStatement.setInt(6, movie.getFreeSeats());
			myStatement.setInt(7, id);
			
			System.out.println(myStatement.toString());
			
			myStatement.execute();
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		finally {
			close(myConnection, myStatement, null);
		}
	}
	
	public Movie getMovieByTitle(String title) {
		Movie searchedMovie = null;
		
		List<Movie> allMovies = getMovies();
		
		Iterator<Movie> iterator = allMovies.iterator();
		
		while(iterator.hasNext()) {
			Movie tempMovie = iterator.next();
			String tempTitle = tempMovie.getTitle();
			
			if(title.equals(tempTitle)) {
				searchedMovie = tempMovie;
				break;
			}	
		}
		
		return searchedMovie;
	}
	
	public Movie findMovie(Integer id) {
		Movie searchedMovie = null;
		
		List<Movie> allMovies = getMovies();
		
		Iterator<Movie> iterator = allMovies.iterator();
		
		while(iterator.hasNext()) {
			Movie tempMovie = iterator.next();
			Integer tempId = tempMovie.getId();
			
			if(id.equals(tempId)) {
				searchedMovie = tempMovie;
				break;
			}	
		}
		
		return searchedMovie;
	}
	
	/**
	 * closes the connections
	 * @param myConnection
	 * @param myStatement
	 * @param myResult
	 */
	private void close(Connection myConnection, Statement myStatement, ResultSet myResult) {
		try {
			if(myResult != null)
				myResult.close();
			if(myStatement != null)
				myStatement.close();
			if(myConnection != null)
				myConnection.close();
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
	}
	
}

/*
 private String path = "movies.json";
	private File myFile;
	
	private static MovieDao instance;
	private DataSource dataSource;
	
	private MovieDao(DataSource dataSource) {
		this.dataSource = dataSource;
		String currentDir = System.getProperty("user.home");
		path = currentDir + System.getProperty("file.separator") + path;
		myFile = new File(path);
		if(myFile.exists())
			myFile.delete();
		try {
			myFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static MovieDao getInstance(DataSource dataSource) {
		if(instance == null) {
			instance = new MovieDao(dataSource);
		}
		return instance;
	}
	
	public void addMovie(Movie movie) {
		JSONObject obj = new JSONObject();
		
		obj.put("title", movie.getTitle());
		obj.put("startHour", Integer.valueOf(movie.getStartHour()));
		obj.put("endHour", Integer.valueOf(movie.getEndHour()));
		obj.put("freeSeats", Integer.valueOf(movie.getFreeSeats()));
		obj.put("price", Double.valueOf(movie.getPrice()));
		obj.put("cinema", movie.getCinema());
		obj.put("id", Integer.valueOf(movie.getId()));
		JSONArray movies = JSONUtils.getEntriesJSON(path);
		
		movies.add(obj);
		
		JSONUtils.persistEntries(movies, path);
		
	}
	
	private Movie getMovieFromJSON(JSONObject obj) {
		return new Movie(((Long)obj.get("id")).intValue(), (String)obj.get("title"), ((Long)obj.get("startHour")).intValue(), ((Long)obj.get("endHour")).intValue(), ((Long)obj.get("freeSeats")).intValue(), (Double)obj.get("price"), (String)obj.get("cinema"));
	}
	
	
	public List<Movie> getMovies(){
		List<Movie> movieList = new ArrayList<>();
		
		JSONArray movies = JSONUtils.getEntriesJSON(path);
		for(int i = 0; i < movies.size(); i++) {
			
			movieList.add(getMovieFromJSON((JSONObject)movies.get(i)));	
		}
		
		
		return movieList;
	}
	
	public void deleteMovie(Integer id) {
		JSONArray movies = JSONUtils.getEntriesJSON(path);
		movies.removeIf(obj -> id.equals(Integer.valueOf(((Long)((JSONObject)obj).get("id")).intValue())));
		JSONUtils.persistEntries(movies, path);
	}
	
	public Movie findMovie(Integer id) {
		JSONArray movies = JSONUtils.getEntriesJSON(path);
		for(int i = 0; i < movies.size(); i++) {
			JSONObject obj = (JSONObject) movies.get(i);
			if(id.equals(Integer.valueOf(((Long)obj.get("id")).intValue()))) {
				return getMovieFromJSON(obj);
			}
		}
		return null;
	}
	
	public void close() {
		myFile.delete();
		instance = null;
	} 
 
 */