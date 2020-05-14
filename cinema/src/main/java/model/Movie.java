package model;


public class Movie {
	
	private int id;
	
	private String title;
	
	private int startHour;
	
	private int endHour;
	
	private int freeSeats;
	
	private double price;
	
	private String cinema;

	private static int nbObjects;
	public Movie(String title, int startHour, int endHour, int freeSeats, double price, String cinema) {
		
		nbObjects++;
		this.id = nbObjects;
		this.title = title;
		this.startHour = startHour;
		this.endHour = endHour;
		this.freeSeats = freeSeats;
		this.price = price;
		this.cinema = cinema;
	}
	
	public Movie(int id, String title, int startHour, int endHour, int freeSeats, double price, String cinema) {
		
		this.id = id;
		this.title = title;
		this.startHour = startHour;
		this.endHour = endHour;
		this.freeSeats = freeSeats;
		this.price = price;
		this.cinema = cinema;
	}
	
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (cinema == null) {
			if (other.cinema != null)
				return false;
		} else if (!cinema.equals(other.cinema))
			return false;
		if (endHour != other.endHour)
			return false;
		if (freeSeats != other.freeSeats)
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (startHour != other.startHour)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", startHour=" + startHour + ", endHour=" + endHour + ", freeSeats="
				+ freeSeats + ", price=" + price + ", cinema=" + cinema + "]";
	}

	public String getCinema() {
		return cinema;
	}

	public void setCinema(String cinema) {
		this.cinema = cinema;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
