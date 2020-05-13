package model;

public class Admin extends User{
	public Admin(String username, String password, String phone, String name, String email) {
		super(username, password, phone, name, email);
	}
	
	
	
	@Override
	public String getCinema() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getRole() {
		return "admin";
	}
}
