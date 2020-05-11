package service;

import dao.UserDao;

public class UserService {
	
	private UserDao userDao;
	
	public UserService() {
		userDao = new UserDao();
		userDao.addUser("alex", "alex", "admin");
	}
	
	public boolean areValidCredentials(String username, String password) {
		return username.equals(password);
	}
	
	public String getRole(String username) {
		if(username.equals("admin"))
			return "admin";
		if(username.equals("manager"))
			return "manager";
		return "regular";
	}
}
