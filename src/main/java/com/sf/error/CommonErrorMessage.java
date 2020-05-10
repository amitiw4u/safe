package com.sf.error;

public enum CommonErrorMessage {

	INVALID_STORE("Invalid store ID", ""),

	INVALID_USER("Invalid user - user is empty/wrong email id", "");
	
	private String businessErrorMessage;
	private String technicalErrorMessage;

	CommonErrorMessage(String businessErrorMessage, String technicalErrorMessage) {
		this.businessErrorMessage = businessErrorMessage;
		this.technicalErrorMessage = technicalErrorMessage;
	}

	CommonErrorMessage(String businessErrorMessage) {
		this.businessErrorMessage = businessErrorMessage;
		this.technicalErrorMessage = businessErrorMessage;
	}

	/**
	 * @return businessErrorMessage
	 */
	public String getBusinessErrorMessage() {
		return businessErrorMessage;
	}

	/**
	 * @return technicalErrorMessage
	 */
	public String getTechnicalErrorMessage() {
		return technicalErrorMessage;
	}

	public void setTechnicalErrorMessage(String technicalErrorMessage) {
		this.technicalErrorMessage = technicalErrorMessage;
	}
}
