package model;

public class Booking {
	private int movieId;
	
	private String username;
	
	private int selectedSeats;
	
	private int id;
	
	private static int nbObjects;

	public Booking(String username, int movieId, int selectedSeats) {
		nbObjects++;
		this.id = nbObjects;
		this.username = username;
		this.movieId = movieId;
		this.selectedSeats = selectedSeats;
	}

	public Booking(int id, String username, int movieId, int selectedSeats) {
		this.username = username;
		this.id = id;
		this.movieId = movieId;
		this.selectedSeats = selectedSeats;
	}
	
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getMovieId() {
		return movieId;
	}
	
	

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public int getSelectedSeats() {
		return selectedSeats;
	}

	public void setSelectedSeats(int selectedSeats) {
		this.selectedSeats = selectedSeats;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Booking other = (Booking) obj;
		if (id != other.id)
			return false;
		if (movieId != other.movieId)
			return false;
		if (selectedSeats != other.selectedSeats)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Booking [movieId=" + movieId + ", username=" + username + ", selectedSeats=" + selectedSeats + ", id="
				+ id + "]";
	}
	
	
	
}
