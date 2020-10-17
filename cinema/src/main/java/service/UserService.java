package service;

import dao.PasswordUtils;
import dao.UserDao;
import model.Manager;
import model.Regular;
import model.User;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

public class UserService {
	
	private UserDao userDao;
	
	private static UserService instance;
	private DataSource dataSource;
	
	private UserService(DataSource theDataSource) {
		dataSource = theDataSource;
		userDao = UserDao.getInstance(theDataSource);
	}
	
	public static UserService getInstance(DataSource theDataSource) {
		if(instance == null) {
			instance = new UserService(theDataSource);
		}
		return instance;
	}
	
	public boolean existUser(String username) {
		return userDao.existUser(username);
	}
	
	public void addUser(User user, boolean isEncrypted) {
		userDao.addUser(user, isEncrypted);
	}
	
	public boolean areValidCredentials(String username, String password) {
		String passwordEncrypted = PasswordUtils.getPasswordEncrypted(password);
		String userPassword = userDao.getPassword(username);
		return passwordEncrypted.equals(userPassword);
	}
	
	public String getRole(String username) {
		return userDao.getUser(username).getRole();
	}
	
	public Integer getCinemaId(String username) {
		return userDao.getUser(username).getCinemaId();
	}
	
	public List<Manager> getManagers(){
		List<User> userList = userDao.getUsers();
		return userList.stream().filter(user -> user instanceof Manager).map(user -> (Manager)user).collect(Collectors.toList());
	}
	
	public User findUser(String username) {
		return userDao.getUser(username);
	}
	
	public Integer getUserId(String username) {
		User user = userDao.getUser(username);
		
		return user.getId();
	}
	
	public String getUsernameById(Integer id) {
		User user = userDao.getUserById(id);
		
		return user.getUsername();
	}
	
	public void close() {
		//userDao.close();
		instance = null;
	}
	//--------------------------------------------------------------------------------------
	public void addUserTest() {
		User tempUser = new Regular("daria", "daria", "daria", "daria@yahoo.com", "07x3");
		
		this.addUser(tempUser, false);
		
		User fromDBUser = userDao.getUser("daria");
		
		System.out.println(fromDBUser.equals(tempUser));
	}
}
