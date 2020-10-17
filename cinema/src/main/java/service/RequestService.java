package service;

import java.util.List;

import javax.sql.DataSource;

import dao.RequestDao;
import dao.UserDao;
import model.Manager;

public class RequestService {
	
	private RequestDao requestDao;
	
	private UserDao userDao;
	
	private static RequestService instance;
	private DataSource dataSource;
	
	private RequestService(DataSource dataSource) {
		this.dataSource = dataSource;
		requestDao = RequestDao.getInstance(dataSource);
		userDao = UserDao.getInstance(dataSource);
	}
	
	public static RequestService getInstance(DataSource dataSource) {
		if(instance == null) {
			instance = new RequestService(dataSource);
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
		//requestDao.close();
		instance = null;
	}
	
	public List<Manager> getRequests(){
		return requestDao.getRequests();
	}
}
