package service;

import dao.PasswordUtils;
import dao.UserDao;
import model.Manager;
import model.User;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {
	
	private UserDao userDao;
	
	private static UserService instance;
	
	private UserService() {
		userDao = UserDao.getInstance();
	}
	
	public static UserService getInstance() {
		if(instance == null) {
			instance = new UserService();
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
		return userDao.selectByUsername(username).getRole();
	}
	
	public String getCinema(String username) {
		return userDao.selectByUsername(username).getCinema();
	}
	
	public List<Manager> getManagers(){
		List<User> userList = userDao.getUsers();
		return userList.stream().filter(user -> user instanceof Manager).map(user -> (Manager)user).collect(Collectors.toList());
	}
	
	public void close() {
		userDao.close();
	}
}
