package dao;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import model.User;
import model.Admin;
import model.Cinema;
import model.Manager;
import model.Regular;

public class UserDao {
	public static UserDao instance = null;
	
	private DataSource dataSource;
	private CinemaDao cinemaDao;
	
	private UserDao(DataSource theDataSource) {
		dataSource = theDataSource;
		cinemaDao = CinemaDao.getInstance(theDataSource);
	}
	
	public static UserDao getInstance(DataSource theDataSource) {
		if(instance == null) {
			instance = new UserDao(theDataSource);
		}
		return instance;
	}
	
	/**
	 * returns all the users form DB 
	 * 
	 * @return List<User>
	 * @throws Exception
	 */
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		
		Connection myConnection = null;
		Statement myStatement = null;
		ResultSet myResult = null;
		
		try {
			User tempUser = null;
			
			myConnection = dataSource.getConnection();
			myStatement = myConnection.createStatement();
			
			String selectQuery = "select * from user";
			
			myResult = myStatement.executeQuery(selectQuery);
			
			while(myResult.next()) {
				int id = myResult.getInt("id");
				String username = myResult.getString("username");
				String password = myResult.getString("password");
				String phone = myResult.getString("phone");
				String name = myResult.getString("name");
				String email = myResult.getString("email");
				String role = myResult.getString("role");
				
				if("m".equals(role))
					tempUser = new Manager(id, username, password, phone, name, email);
				if("r".equals(role))
					tempUser = new Regular(id, username, password, phone, name, email);
				if("a".equals(role))
					tempUser = new Admin(id, username, password, phone, name, email);
				
				users.add(tempUser);
			}
			
			return users;
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		finally {
			close(myConnection, myStatement, myResult);
		}
		
		return users;
	}
	
	/**
	 * adds a user into DB
	 * @param user
	 * @param isEncrypted
	 */
	public void addUser(User user, boolean isEncrypted){
		String password = user.getPassword();
		
		if(!isEncrypted)
			password = PasswordUtils.getPasswordEncrypted(password);
		//System.out.println(user.getUsername() + " " + password);
		
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		
		try {
			myConnection = dataSource.getConnection();
			
			String insertQuery = "insert into user"
								+ "(username, password, name, email, phone, role)"
								+ "values(?, ?, ?, ?, ?, ?)";
			
			myStatement = myConnection.prepareStatement(insertQuery);
			
			String username = user.getUsername();
			
			myStatement.setString(1, username);
			myStatement.setString(2, password);
			myStatement.setString(3, user.getName());
			myStatement.setString(4, user.getEmail());
			myStatement.setString(5, user.getPhone());
			
			String role = user.getRole();
			
			myStatement.setString(6, role);
			
			myStatement.execute();
			
			/*if("m".equals(role)) {
				// add into cinema
				Cinema newCinema = new Cinema(getUserId(username), "name");
				cinemaDao.addCinema(newCinema);
				
			}
			*/
			
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
		finally {
			close(myConnection, myStatement, null);
		}
	}
	/**
	 * gets a user searched by username
	 * @param username
	 * @return User
	 */
	public User getUser(String username) {
		List<User> users;
		
		try {
			users = getUsers();
			
			Iterator<User> iterator = users.iterator();
			
			while(iterator.hasNext()) {
				User tempUser = iterator.next();
				if(tempUser.getUsername().equals(username))
					return tempUser;
			}
			
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public User getUserById(Integer id) {
		List<User> users;
		
		try {
			users = getUsers();
			
			Iterator<User> iterator = users.iterator();
			
			while(iterator.hasNext()) {
				User tempUser = iterator.next();
				Integer tempId = tempUser.getId();
				
				if(tempId.equals(id))
					return tempUser;
			}
			
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * searches for a user by username and returns true if exists of false otherwise
	 * @param username
	 * @return boolean
	 */
	public boolean existUser(String username) {
		User searchedUser = getUser(username);
		
		if(searchedUser != null)
			return true;
		
		return false;
	}
	
	public String getRole(String username) {
		
		User user = getUser(username);
		
		if(user == null)
			return null;
		
		return user.getRole();
	}
	
	public String getPassword(String username) {
		
		User user = getUser(username);
		
		if(user == null)
			return null;
		
		return user.getPassword();
	}
	
	public Integer getUserId(String username) {
		User user = getUser(username);
		
		return user.getUserId();
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