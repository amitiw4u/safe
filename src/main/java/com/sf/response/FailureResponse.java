package com.sf.response;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class FailureResponse {

	private final boolean success = false;

	private String message = "";

	public static final FailureResponse WITH_NO_CONTENT = new FailureResponse();

	private List<ErrorInfo> errors;

	private Object requestContent;

	public FailureResponse(List<ErrorInfo> errors) {
		this.errors = errors;
	}

	public FailureResponse(ErrorInfo error) {
		this(Collections.singletonList(error));
	}

	public FailureResponse(List<ErrorInfo> errors, String message) {
		this(errors);
		this.message = message;
	}

	public FailureResponse(String message) {
		this.message = message;
	}

	private FailureResponse() {

	}
}
