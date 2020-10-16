package service;

import java.util.List;

import javax.sql.DataSource;

import dao.CinemaDao;
import dao.UserDao;
import model.Cinema;

public class CinemaService {
	private UserDao userDao;
	private CinemaDao cinemaDao; 
	
	private DataSource dataSource;
	private static CinemaService instance;
	
	private CinemaService(DataSource dataSource) {
		userDao = UserDao.getInstance(dataSource);
		cinemaDao = CinemaDao.getInstance(dataSource);
	}
	
	public static CinemaService getInstance(DataSource dataSource) {
		if(instance == null) {
			instance = new CinemaService(dataSource);
		}
		return instance;
	}
	
	public List<Cinema> getCinemas() {
		return cinemaDao.getCinemas();
	}
	
	public void addCinema(Cinema cinema) {
		cinemaDao.addCinema(cinema);
	}
	
	public Integer getCinemaIdByName(String name) {
		return cinemaDao.getCinemaIdByName(name);
	}
	
	public void close() {
		instance = null;
	}
}
