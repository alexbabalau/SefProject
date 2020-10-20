package dao;

import java.io.File;
import java.io.FileReader;
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
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.Booking;
import model.Manager;
import model.Request;
import model.User;
import service.UserService;

public class RequestDao {
	private static RequestDao instance;
	private UserService userService;
	private DataSource dataSource;
	
	private RequestDao(DataSource dataSource) {
		this.dataSource = dataSource;
		userService = UserService.getInstance(dataSource);
	}
	
	public static RequestDao getInstance(DataSource dataSource) {
		if(instance == null) {
			instance = new RequestDao(dataSource);
		}
		return instance;
	}
	
	public List<Request> getRequests() {
		List<Request> requests = new ArrayList<>();
		
		Connection myConnection = null;
		Statement myStatement = null;
		ResultSet myResult = null;
		
		try {
			Request tempRequest = null;
			
			myConnection = dataSource.getConnection();
			myStatement = myConnection.createStatement();
			
			String selectQuery = "select * from request";
			
			myResult = myStatement.executeQuery(selectQuery);
			
			while(myResult.next()) {
				int id = myResult.getInt("id");
				String username = myResult.getString("username");
				String password = myResult.getString("password");
				String phone = myResult.getString("phone");
				String name = myResult.getString("name");
				String email = myResult.getString("email");
				String cinemaName = myResult.getString("cinema_name");
				
				Manager tempManager = new Manager(username, password, phone, name, email);
				tempRequest = new Request(id, tempManager, cinemaName);
				
				requests.add(tempRequest);
			}
			
			return requests;
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		finally {
			close(myConnection, myStatement, myResult);
		}
		
		return requests;
	}
	
	public void addRequest(Request request) {
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		
		try {
			myConnection = dataSource.getConnection();
			
			String insertQuery = "insert into request"
								+ "(username, password, name, email, phone, cinema_name)"
								+ "values(?, ?, ?, ?, ?, ?)";

			myStatement = myConnection.prepareStatement(insertQuery);

			myStatement.setString(1, request.getManager().getUsername());
			myStatement.setString(2, request.getManager().getPassword());
			myStatement.setString(3, request.getManager().getName());
			myStatement.setString(4, request.getManager().getEmail());
			myStatement.setString(5, request.getManager().getPhone());
			myStatement.setString(6, request.getCinemaName());

			myStatement.execute();
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		finally {
			close(myConnection, myStatement, null);
		}
	}
	
	public void deleteRequest(String username) {
		
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		
		try {
			myConnection = dataSource.getConnection();
			
			String deleteQuery = "delete from request where id=?";
			
			myStatement = myConnection.prepareStatement(deleteQuery);
			
			Request toBeDeletedRequest = findRequest(username);
			Integer id = toBeDeletedRequest.getId();
			
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
	
	public Request findRequest(String username) {
		List<Request> requests;
		
		try {
			requests = getRequests();
			
			Iterator<Request> iterator = requests.iterator();
			
			while(iterator.hasNext()) {
				Request tempRequest = iterator.next();
				if(tempRequest.getManager().getUsername().equals(username))
					return tempRequest;
			}
			
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
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