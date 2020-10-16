package model;

public class Regular extends User{
	
	public Regular(String username, String password, String phone, String name, String email) {
		super(username, password, phone, name, email);
	}
	
	public Regular(int id, String username, String password, String phone, String name, String email) {
		super(id, username, password, phone, name, email);
	}
	
	@Override
	public Integer getCinemaId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getRole() {
		return "regular";
	}
	
	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}
}
