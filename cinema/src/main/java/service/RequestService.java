package service;

import java.util.List;

import javax.sql.DataSource;

import dao.RequestDao;
import dao.UserDao;
import model.Cinema;
import model.Manager;
import model.Request;

public class RequestService {
	
	private RequestDao requestDao;
	private UserDao userDao;
	
	private static RequestService instance;
	private UserService userService;
	private CinemaService cinemaService;
	private DataSource dataSource;
	
	private RequestService(DataSource dataSource) {
		this.dataSource = dataSource;
		requestDao = RequestDao.getInstance(dataSource);
		userDao = UserDao.getInstance(dataSource);
		cinemaService = CinemaService.getInstance(dataSource);
		userService = UserService.getInstance(dataSource);
	}
	
	public static RequestService getInstance(DataSource dataSource) {
		if(instance == null) {
			instance = new RequestService(dataSource);
		}
		return instance;
	}
	
	public void addRequest(Manager m, String cinemaName) {
		requestDao.addRequest(new Request(m, cinemaName));
	}
	
	public void approveRequest(String username) {
		Request approvedRequest = requestDao.findRequest(username);
		Manager approvedManager = approvedRequest.getManager();
		userDao.addUser(approvedManager, false);
		Integer idManager = userService.getUserId(username);
		Cinema approvedCinema = new Cinema(idManager, approvedRequest.getCinemaName());
		cinemaService.addCinema(approvedCinema);
		requestDao.deleteRequest(username);
	}
	
	public void denyRequest(String username) {
		requestDao.deleteRequest(username);
	}
	
	public void close() {
		//requestDao.close();
		instance = null;
	}
	
	public List<Request> getRequests(){
		return requestDao.getRequests();
	}
}
