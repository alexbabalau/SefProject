package model;

public class Request {
	private int id;
	private Manager manager;
	private String cinemaName;
	
	public Request(Manager manager, String cinemaName) {
		this.manager = manager;
		this.cinemaName = cinemaName;
	}
	
	public Request(int id, Manager manager, String cinemaName) {
		this.id = id;
		this.manager = manager;
		this.cinemaName = cinemaName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public String getCinemaName() {
		return cinemaName;
	}

	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}
	
}
