package com.sf.response;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ErrorInfo {

	private String errorMessage;

	private HttpStatus httpStatus;

	@JsonIgnore
	private Exception e;

	public ErrorInfo(String errorMessage, HttpStatus httpStatus) {
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
	}

}
