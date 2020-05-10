package com.sf.bean;

import java.io.Serializable;

public class AuthResponse implements Serializable {

	private static final long serialVersionUID = 8250903109802273637L;

	private String name;
	private String email;
	private int type;
	private String sessionToken;
	private String userName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "AuthResponse [name=" + name + ", email=" + email + ", type=" + type + ", sessionToken=" + sessionToken
				+ ", userName=" + userName + "]";
	}

}
