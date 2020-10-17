package model;

public class Admin extends User{
	public Admin(String username, String password, String phone, String name, String email) {
		super(username, password, phone, name, email);
	}

	public Admin(int id, String username, String password, String phone, String name, String email) {
		super(id, username, password, phone, name, email);
	}
	
	@Override
	public Integer getCinemaId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getRole() {
		return "a";
	}
	
	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}
}
