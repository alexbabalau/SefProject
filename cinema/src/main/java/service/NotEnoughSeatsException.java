package service;

public class NotEnoughSeatsException extends Exception{
	public NotEnoughSeatsException(String message) {
		super(message);
	}
	public NotEnoughSeatsException() {
		
	}
}
