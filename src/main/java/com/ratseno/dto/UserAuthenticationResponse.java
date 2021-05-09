package com.ratseno.dto;

import java.io.Serializable;

public class UserAuthenticationResponse implements Serializable {

	private static final long serialVersionUID = -5135212427018422503L;

	private final String jwttoken;

	public UserAuthenticationResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
}
