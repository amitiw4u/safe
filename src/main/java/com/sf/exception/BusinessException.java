package com.sf.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends SafeException {

	private static final long serialVersionUID = -4937990840950030838L;

	public BusinessException(String message) {
		super(message, HttpStatus.OK);
	}

	public BusinessException(String businessErrorMessage, HttpStatus code) {
		super(businessErrorMessage, code);
	}

}
