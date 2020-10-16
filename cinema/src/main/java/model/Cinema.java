package model;

public class Cinema {
	private int id_cinema;
	private int id_manager;
	private String name;
	
	public Cinema(int id_manager, String name) {
		super();
		this.id_manager = id_manager;
		this.name = name;
	}

	public Cinema(int id_cinema, int id_manager, String name) {
		super();
		this.id_cinema = id_cinema;
		this.id_manager = id_manager;
		this.name = name;
	}

	public int getIdCinema() {
		return id_cinema;
	}

	public void setIdCinema(int id_cinema) {
		this.id_cinema = id_cinema;
	}

	public int getIdManager() {
		return id_manager;
	}

	public void setIdManager(int id_manager) {
		this.id_manager = id_manager;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
