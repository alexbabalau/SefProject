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
	
	public List<Manager> getRequests() {
		List<Manager> requests = new ArrayList<>();
		
		Connection myConnection = null;
		Statement myStatement = null;
		ResultSet myResult = null;
		
		try {
			Manager tempRequest = null;
			
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
				
				tempRequest = new Manager(id, username, password, phone, name, email);
				
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
	
	public void addRequest(Manager request) {
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		
		try {
			myConnection = dataSource.getConnection();
			
			String insertQuery = "insert into request"
								+ "(username, password, name, email, phone)"
								+ "values(?, ?, ?, ?, ?)";

			myStatement = myConnection.prepareStatement(insertQuery);

			myStatement.setString(1, request.getUsername());
			myStatement.setString(2, request.getPassword());
			myStatement.setString(3, request.getName());
			myStatement.setString(4, request.getEmail());
			myStatement.setString(5, request.getPhone());

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
			
			String deleteQuery = "delete from request where id_request=?";
			
			myStatement = myConnection.prepareStatement(deleteQuery);
			
			Integer id = userService.getCinemaId(username);
			
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
	
	public Manager findRequest(String username) {
		List<Manager> requests;
		
		try {
			requests = getRequests();
			
			Iterator<Manager> iterator = requests.iterator();
			
			while(iterator.hasNext()) {
				Manager tempRequest = iterator.next();
				if(tempRequest.getUsername().equals(username))
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
/*
	private String path = "requests.json";
	private File myFile;
	private static RequestDao instance;
	private RequestDao() {
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
	
	public static RequestDao getInstance() {
		if(instance == null) {
			instance = new RequestDao();
		}
		return instance;
	}
	
	public void close() {
		myFile.delete();
		instance = null;
	}
	
	public void addRequest(Manager manager) {
		JSONObject obj = new JSONObject();
		obj.put("username", manager.getUsername());
		obj.put("password", PasswordUtils.getPasswordEncrypted(manager.getPassword()));
		obj.put("phone", manager.getPhone());
		obj.put("email", manager.getEmail());
		obj.put("name", manager.getName());
		obj.put("cinema", manager.getCinema());
		JSONArray requests = JSONUtils.getEntriesJSON(path);
		
		requests.add(obj);
		
		JSONUtils.persistEntries(requests, path);
	}
	
	private Manager getManagerFromJSON(JSONObject obj) {
		return new Manager((String)obj.get("username"), (String)obj.get("password"), (String)obj.get("phone"), (String)obj.get("name"), (String)obj.get("email"), (String)obj.get("cinema"));
	}
	
	public Manager findRequest(String username) {
		JSONArray requests = JSONUtils.getEntriesJSON(path);
		for(int i = 0; i < requests.size(); i++) {
			JSONObject obj = (JSONObject) requests.get(i);
			if(username.equals((String)obj.get("username"))) {
				return getManagerFromJSON(obj);
			}
		}
		return null;
	}
	
	public List<Manager> getRequests(){
		List<Manager> requestList = new ArrayList<>();
		
		JSONArray requests = JSONUtils.getEntriesJSON(path);
		
		for(int i = 0; i < requests.size(); i++) {
			requestList.add(getManagerFromJSON((JSONObject)requests.get(i)));
		}
		
		return requestList;
	}
	
	public void deleteRequest(String username) {
		JSONArray requests = JSONUtils.getEntriesJSON(path);
		requests.removeIf(obj -> username.equals((String)((JSONObject)obj).get("username")));
		JSONUtils.persistEntries(requests, path);
	}
*/

