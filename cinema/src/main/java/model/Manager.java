package model;

public class Manager extends User{
	
	private String cinema;
	
	public Manager(String username, String password, String phone, String name, String email, String cinema) {
		super(username, password, phone, name, email);
		this.cinema = cinema;
	}
	
	@Override
	public String getCinema() {
		// TODO Auto-generated method stub
		return cinema;
	}
	
	
	
	@Override
	public String getRole() {
		return "manager";
	}

}
