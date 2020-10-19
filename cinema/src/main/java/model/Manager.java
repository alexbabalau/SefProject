package model;

import service.CinemaService;

public class Manager extends User{
	
	public Manager(String username, String password, String phone, String name, String email) {
		super(username, password, phone, name, email);
	}
	
	public Manager(int id, String username, String password, String phone, String name, String email) {
		super(id, username, password, phone, name, email);
	}
	
	@Override
	public Integer getCinemaId() {
		return 1;
	}
	
	@Override
	public String toString() {
		return "Manager [cinema=" + 1 + ", getCinema()=" + getCinemaId() + ", getRole()=" + getRole()
				+ ", getUsername()=" + getUsername() + ", getPassword()=" + getPassword() + ", getEmail()=" + getEmail()
				+ ", getPhone()=" + getPhone() + ", getName()=" + getName() + "]";
	}

	@Override
	public String getRole() {
		return "m";
	}
	
	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

}
