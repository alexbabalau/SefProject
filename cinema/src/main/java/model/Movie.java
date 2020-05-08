package model;


public class Movie {
	
	private String title;
	
	private int startHour;
	
	private int endHour;
	
	private int freeSeats;
	
	private double price;

	public Movie(String title, int startHour, int endHour, int freeSeats, double price) {
		this.title = title;
		this.startHour = startHour;
		this.endHour = endHour;
		this.freeSeats = freeSeats;
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	public int getFreeSeats() {
		return freeSeats;
	}

	public void setFreeSeats(int freeSeats) {
		this.freeSeats = freeSeats;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
