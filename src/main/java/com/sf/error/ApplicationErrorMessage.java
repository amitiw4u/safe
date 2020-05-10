package com.sf.error;

public enum ApplicationErrorMessage {

	INVALID_CREDENTIALS("Authentication Failed", "Authentication Failed:{0}"),

	INACTIVE_USER("User is inactive"),

	REQUEST_URL_NOT_FOUND("Request url:{0} is not available"),

	SOMETHING_WENT_WRONG("Something went wrong!"),

	INVALID_SESSION_TOKEN("Session token is invalid/expired.", "Details could not be found for sessionToken:{0}"),

	MISSING_SESSION_TOKEN("Session token is required for accessing apis.", "Mission session token in request headers"),

	INVALID_REQUEST_BODY("Invalid request json body");

	protected String businessErrorMessage;
	protected String technicalErrorMessage;

	ApplicationErrorMessage(String businessErrorMessage, String technicalErrorMessage) {
		this.businessErrorMessage = businessErrorMessage;
		this.technicalErrorMessage = technicalErrorMessage;
	}

	ApplicationErrorMessage(String businessErrorMessage) {
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
