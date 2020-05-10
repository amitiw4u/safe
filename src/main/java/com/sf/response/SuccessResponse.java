package com.sf.response;


import com.sf.config.Constants;

import lombok.Data;

@Data
public class SuccessResponse {

	private final boolean success = true;

	private String message = Constants.EMPTY_STRING;

	public static final SuccessResponse WITH_NO_CONTENT = new SuccessResponse();

	private Object content;

	public SuccessResponse(Object content, String msg) {
		this(content);
		this.message = msg;
	}

	public SuccessResponse(Object content) {
		this.content = content;
	}

	public SuccessResponse(String msg) {
		this.message = msg;
	}

	public SuccessResponse() {
	}

	public void setContent(Object content) {
		this.content = content;
	}

}
