package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import model.Cinema;
import model.Movie;

public class CinemaDao {
	
	private DataSource dataSource;
	private static CinemaDao instance = null;
	
	private CinemaDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public static CinemaDao getInstance(DataSource dataSource) {
		if(instance == null) {
			instance = new CinemaDao(dataSource);
		}
		return instance;
	}
	
	public List<Cinema> getCinemas() {
		List<Cinema> cinemas = new ArrayList<Cinema>();
		
		Connection myConnection = null;
		Statement myStatement = null;
		ResultSet myResult = null;
		
		try {
			Cinema tempCinema = null;
			
			myConnection = dataSource.getConnection();
			myStatement = myConnection.createStatement();
			
			String selectQuery = "select * from cinema";
			
			myResult = myStatement.executeQuery(selectQuery);
			
			while(myResult.next()) {
				int id = myResult.getInt("id_cinema");
				String name = myResult.getString("name");
				int id_manager = myResult.getInt("id_manager");
				
				tempCinema = new Cinema(id, id_manager, name);
				
				cinemas.add(tempCinema);
			}
			
			return cinemas;
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		finally {
			close(myConnection, myStatement, myResult);
		}
		
		return cinemas;
	}
	
	public void addCinema(Cinema cinema) {
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		
		try {
			myConnection = dataSource.getConnection();
			
			String insertQuery = "insert into cinema"
								+ "(id_cinema, id_manager, name)"
								+ "values(?, ?, ?)";
			
			myStatement = myConnection.prepareStatement(insertQuery);
			
			myStatement.setInt(1, cinema.getIdCinema());
			myStatement.setInt(2, cinema.getIdManager());
			myStatement.setString(3, cinema.getName());
			
			myStatement.execute();
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		finally {
			close(myConnection, myStatement, null);
		}
	}
	
	public Cinema getCinema(Integer id) {
		List<Cinema> allCinemas = getCinemas();
		
		Iterator<Cinema> iterator = allCinemas.iterator();
		
		while(iterator.hasNext()) {
			Cinema cinema = iterator.next();
			Integer tempId = cinema.getIdCinema();
			
			if(tempId.equals(id))
				return cinema;
		}
		
		return null;
	}
	
	public Integer getCinemaIdByName(String name) {
		List<Cinema> allCinemas = getCinemas();
		Integer id = null;
		Iterator<Cinema> iterator = allCinemas.iterator();
		
		while(iterator.hasNext()) {
			Cinema tempCinema = iterator.next();
			
			if(name.equals(tempCinema.getName())) {
				id = tempCinema.getIdCinema();
				break;
			}	
		}
		
		return id;
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
