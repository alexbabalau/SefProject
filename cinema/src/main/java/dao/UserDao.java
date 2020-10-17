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
			myStatement.setString(2, user.getPassword());
			myStatement.setString(3, user.getName());
			myStatement.setString(4, user.getEmail());
			myStatement.setString(5, user.getPhone());
			
			String role = user.getRole();
			
			myStatement.setString(6, role);
			
			myStatement.execute();
			
			if("m".equals(role)) {
				// add into cinema
				Cinema newCinema = new Cinema(getUserId(username), "name");
				cinemaDao.addCinema(newCinema);
			}
			
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

/*public class UserDao {
	
	private String path = "users5.json";
	private File myFile;
	public static UserDao instance = null;
	private UserDao() {
		String currentDir = System.getProperty("user.home");
		path = currentDir + System.getProperty("file.separator") + path;
		myFile = new File(path);
		if(myFile.exists())
			myFile.delete();
		System.out.println(myFile.getAbsolutePath());
		try {
			myFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static UserDao getInstance() {
		if(instance == null) {
			instance = new UserDao();
		}
		return instance;
	}
	
	
	public void addUser(User user, boolean isEncrypted){
		String password = user.getPassword();
		if(!isEncrypted)
			password = PasswordUtils.getPasswordEncrypted(password);
		System.out.println(user.getUsername() + " " + password);
		JSONObject obj = new JSONObject();
		obj.put("username", user.getUsername());
		obj.put("password", password);
		obj.put("role", user.getRole());
		obj.put("phone", user.getPhone());
		obj.put("email", user.getEmail());
		obj.put("name", user.getName());
		obj.put("cinema", user.getCinema());
		JSONArray users = JSONUtils.getEntriesJSON(path);
		
		users.add(obj);
		
		JSONUtils.persistEntries(users, path);
	}
	
	public boolean existUser(String username) {
		JSONArray users = JSONUtils.getEntriesJSON(path);
		Iterator<JSONObject> iterator = users.iterator();
		
		while(iterator.hasNext()) {
			JSONObject next = iterator.next();
			
			if(next.get("username").equals(username)) {
				return true;
			}
		}
		return false;
	}
		
	
	private User getUserFromJSON(JSONObject user) {
		if(((String)user.get("role")).equals("admin")) {
			return new Admin((String)user.get("username"), (String)user.get("password"), (String)user.get("phone"), (String)user.get("name"), (String)user.get("email"));
		}
		if(((String)user.get("role")).equals("manager")) {
			return new Manager((String)user.get("username"), (String)user.get("password"), (String)user.get("phone"), (String)user.get("name"), (String)user.get("email"), (String)user.get("cinema"));
		}
		if(((String)user.get("role")).equals("regular")) {
			return new Regular((String)user.get("username"), (String)user.get("password"), (String)user.get("phone"), (String)user.get("name"), (String)user.get("email"));
		}
		return null;
	}
	
	public User selectByUsername(String username) {
		JSONObject user = getUser(username);
		
		
		return getUserFromJSON(user);
	}
	
	public List<User> getUsers(){
		List<User> userList = new ArrayList<>();
		
		JSONArray users = JSONUtils.getEntriesJSON(path);
		
		for(int i = 0; i < users.size(); i++) {
			userList.add(getUserFromJSON((JSONObject)users.get(i)));
		}
		
		return userList;
	}
	
	
	private JSONObject getUser(String username) {
		JSONArray users = JSONUtils.getEntriesJSON(path);
		Iterator<JSONObject> iterator = users.iterator();
		while(iterator.hasNext()) {
			JSONObject obj = iterator.next();
			if(((String)obj.get("username")).equals(username)){
				return obj;
			}
		}
		return null;
	}
	
	public String getRole(String username) {
		
		JSONObject obj = getUser(username);
		
		if(obj == null)
			return null;
		
		return (String)obj.get("role");
	}
	
	public String getPassword(String username) {
		
		JSONObject obj = getUser(username);
		
		if(obj == null)
			return null;
		
		return (String)obj.get("password");
	}
	
	public void close() {
		myFile.delete();
		instance = null;
	}
	
}*/
