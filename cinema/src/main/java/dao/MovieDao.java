package dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.Manager;
import model.Movie;

public class MovieDao {
	private String path = "movies.json";
	private File myFile;
	private static MovieDao instance;
	private MovieDao() {
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
	
	public static MovieDao getInstance() {
		if(instance == null) {
			instance = new MovieDao();
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
}
