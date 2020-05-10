package com.sf.exception;

import org.springframework.http.HttpStatus;

public class TransactionException extends SafeException {

	private static final long serialVersionUID = -2060112722013676645L;

	public TransactionException(Exception e, HttpStatus code) {
		super(e, code);
	}

	public TransactionException(String message, HttpStatus code) {
		super(message, code);
	}

}
