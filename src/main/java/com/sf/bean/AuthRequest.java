/**
 * 
 */
package com.sf.bean;

public class AuthRequest {

	private String userName;
	private String password;
	private boolean isEncodingRequired = true;
	
	/**
	 * @return the isEncodingRequired
	 */
	public boolean getIsEncodingRequired() {
		return isEncodingRequired;
	}

	/**
	 * @param isEncodingRequired the isEncodingRequired to set
	 */
	public void setIsEncodingRequired(boolean isEncodingRequired) {
		this.isEncodingRequired = isEncodingRequired;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "AuthRequest [userName=" + userName + ", password=" + password + ", isEncodingRequired="
				+ isEncodingRequired + "]";
	}

}
