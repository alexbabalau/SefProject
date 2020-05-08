package service;

import dao.UserDao;

public class UserService {
	
	private UserDao userDao;
	
	public UserService() {
		userDao = new UserDao();
	}
	
	public boolean areValidCredentials(String username, String password) {
		return username.equals(password);
	}
}
