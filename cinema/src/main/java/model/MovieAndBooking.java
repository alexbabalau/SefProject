package model;

public class MovieAndBooking {
	private Movie movie;
	private Booking booking;
	
	
	
	public MovieAndBooking(Movie movie, Booking booking) {
		this.movie = movie;
		this.booking = booking;
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public Booking getBooking() {
		return booking;
	}
	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	
	
}
