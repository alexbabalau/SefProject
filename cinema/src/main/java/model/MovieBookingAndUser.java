package model;

public class MovieBookingAndUser {
	private Movie movie;
	private Booking booking;
	private User user;
	
	public MovieBookingAndUser(Movie movie, Booking booking, User user) {
		this.movie = movie;
		this.booking = booking;
		this.user = user;
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
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
