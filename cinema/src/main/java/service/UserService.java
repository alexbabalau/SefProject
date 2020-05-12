package service;

import dao.UserDao;

public class UserService {
	
	private UserDao userDao;
	
	public UserService() {
		userDao = new UserDao();
	}
	
	public void addUser(String username, String password, String role) {
		userDao.addUser(username, password, role);
	}
	
	public boolean areValidCredentials(String username, String password) {
		String passwordEncrypted = userDao.getPasswordEncrypted(password);
		String userPassword = userDao.getPassword(username);
		return passwordEncrypted.equals(userPassword);
	}
	
	public String getRole(String username) {
		return userDao.getRole(username);
	}
}
