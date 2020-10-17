package dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

import model.Booking;
import model.Manager;
import model.Movie;
import model.User;
import service.UserService;

public class BookingDao {
	private static BookingDao instance;
	private DataSource dataSource;
	private UserService userService;
	
	private BookingDao(DataSource dataSource) {
		this.dataSource = dataSource;
		userService = UserService.getInstance(dataSource);
	}
	
	public static BookingDao getInstance(DataSource dataSource) {
		if(instance == null) {
			instance = new BookingDao(dataSource);
		}
		return instance;
	}
	
	public List<Booking> getBookings() {
		List<Booking> bookings = new ArrayList<Booking>();
		
		Connection myConnection = null;
		Statement myStatement = null;
		ResultSet myResult = null;
		
		try {
			Booking tempBooking = null;
			
			myConnection = dataSource.getConnection();
			myStatement = myConnection.createStatement();
			
			String selectQuery = "select * from booking";
			
			myResult = myStatement.executeQuery(selectQuery);
			
			while(myResult.next()) {
				int idBooking = myResult.getInt("id_booking");
				int idManager = myResult.getInt("id_user");
				int idMovie = myResult.getInt("id_movie");
				int selectedSeats = myResult.getInt("selected_seats");
				
				String username = userService.getUsernameById(idManager);
				
				tempBooking = new Booking(idBooking, username, idMovie, selectedSeats);
				
				bookings.add(tempBooking);
			}
			
			return bookings;
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		finally {
			close(myConnection, myStatement, myResult);
		}
		
		return bookings;
	}
	
	public void addBooking(Booking booking) {
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		
		try {
			myConnection = dataSource.getConnection();
			
			String insertQuery = "insert into booking"
								+ "(id_user, id_movie, selected_seats)"
								+ "values(?, ?, ?)";
			
			myStatement = myConnection.prepareStatement(insertQuery);
			
			String username = booking.getUsername();
			Integer idUser = userService.getUserId(username);
			
			myStatement.setInt(1, idUser);
			myStatement.setInt(2, booking.getMovieId());
			myStatement.setInt(3, booking.getSelectedSeats());
			
			myStatement.execute();
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		finally {
			close(myConnection, myStatement, null);
		}
	}
	
	public void deleteBooking(Integer id) {
		
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		
		try {
			myConnection = dataSource.getConnection();
			
			String deleteQuery = "delete from booking where id_booking=?";
			
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
	
	public Booking findBooking(Integer id) {
		List<Booking> bookings;
		
		try {
			bookings = getBookings();
			
			Iterator<Booking> iterator = bookings.iterator();
			
			while(iterator.hasNext()) {
				Booking tempBooking = iterator.next();
				Integer tempId = tempBooking.getId();
				
				if(tempId.equals(id))
					return tempBooking;
			}
			
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public void deleteByMovieId(Integer id) {
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		
		try {
			myConnection = dataSource.getConnection();
			
			String deleteQuery = "delete from booking where id_movie=?";
			
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
 private String path = "bookings.json";
	private File myFile;
	private static BookingDao instance;
	private BookingDao() {
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
	
	public static BookingDao getInstance() {
		if(instance == null) {
			instance = new BookingDao();
		}
		return instance;
	}
	
	
	
	public void addBooking(Booking booking) {
		JSONObject obj = new JSONObject();
		
		obj.put("id", booking.getId());
		obj.put("username", booking.getUsername());
		obj.put("movieId", booking.getMovieId());
		obj.put("selectedSeats", booking.getSelectedSeats());
	
		JSONArray bookings = JSONUtils.getEntriesJSON(path);
		
		bookings.add(obj);
		
		JSONUtils.persistEntries(bookings, path);
		
	}
	
	public List<Booking> getBookings(){
		List<Booking> bookingList = new ArrayList<>();
		
		JSONArray bookings = JSONUtils.getEntriesJSON(path);
		for(int i = 0; i < bookings.size(); i++) {
			
			bookingList.add(getBookingFromJSON((JSONObject)bookings.get(i)));	
		}
		
		
		return bookingList;
	}
	
	public void deleteBooking(Integer id) {
		JSONArray bookings = JSONUtils.getEntriesJSON(path);
		bookings.removeIf(obj -> id.equals(Integer.valueOf(((Long)((JSONObject)obj).get("id")).intValue())));
		JSONUtils.persistEntries(bookings, path);
	}
	
	private Booking getBookingFromJSON(JSONObject obj) {
		return new Booking(((Long)obj.get("id")).intValue(), (String)obj.get("username"), ((Long)obj.get("movieId")).intValue(), ((Long)obj.get("selectedSeats")).intValue());
	}
	
	public Booking findBooking(Integer id) {
		JSONArray bookings = JSONUtils.getEntriesJSON(path);
		for(int i = 0; i < bookings.size(); i++) {
			JSONObject obj = (JSONObject) bookings.get(i);
			if(id.equals(Integer.valueOf(((Long)obj.get("id")).intValue()))) {
				return getBookingFromJSON(obj);
			}
		}
		return null;
	}
	
	public void close() {
		myFile.delete();
		instance = null;
	}

	public void deleteByMovieId(Integer id) {
		JSONArray bookings = JSONUtils.getEntriesJSON(path);
		bookings.removeIf(obj -> id.equals(Integer.valueOf(((Long)((JSONObject)obj).get("movieId")).intValue())));
		JSONUtils.persistEntries(bookings, path);
		
	}
 */
