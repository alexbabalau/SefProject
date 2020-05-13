package service;

import java.util.List;

import dao.RequestDao;
import dao.UserDao;
import model.Manager;

public class RequestService {
	
	private RequestDao requestDao;
	
	private UserDao userDao;
	
	private static RequestService instance;
	
	private RequestService() {
		requestDao = RequestDao.getInstance();
		userDao = UserDao.getInstance();
	}
	
	public static RequestService getInstance() {
		if(instance == null) {
			instance = new RequestService();
		}
		return instance;
	}
	
	public void addRequest(Manager m) {
		requestDao.addRequest(m);
	}
	
	public void approveRequest(String username) {
		Manager approvedManager = requestDao.findRequest(username);
		userDao.addUser(approvedManager, true);
		requestDao.deleteRequest(username);
	}
	
	public void denyRequest(String username) {
		requestDao.deleteRequest(username);
	}
	
	public void close() {
		requestDao.close();
	}
	
	public List<Manager> getRequests(){
		return requestDao.getRequests();
	}
}
