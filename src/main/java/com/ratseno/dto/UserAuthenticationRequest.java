package com.ratseno.dto;

import java.io.Serializable;

public class UserAuthenticationRequest implements Serializable{
	
	private static final long serialVersionUID = -4795378305065643522L;
	
	private String username;
	private String password;

	public UserAuthenticationRequest() {

	}

	public UserAuthenticationRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
