package dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.Booking;
import model.Manager;
import model.Movie;

public class BookingDao {
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
	}
}
