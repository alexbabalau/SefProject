package service;

import dao.UserDao;
import model.User;

public class UserService {
	
	private UserDao userDao;
	
	public UserService() {
		userDao = UserDao.getInstance();
	}
	
	public boolean existUser(String username) {
		return userDao.existUser(username);
	}
	
	public void addUser(User user) {
		userDao.addUser(user);
	}
	
	public boolean areValidCredentials(String username, String password) {
		String passwordEncrypted = userDao.getPasswordEncrypted(password);
		String userPassword = userDao.getPassword(username);
		return passwordEncrypted.equals(userPassword);
	}
	
	public String getRole(String username) {
		return userDao.selectByUsername(username).getRole();
	}
	
	public void close() {
		userDao.close();
	}
}
