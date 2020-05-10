package com.sf.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.sf.response.ErrorInfo;
import com.sf.response.FailureResponse;
import com.sf.util.SmartUtil;


@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(SafeException.class)
	@ResponseBody
	public ResponseEntity<FailureResponse> walletExceptionOccured(SafeException exception) {
		HttpStatus httpStatus = exception.getHttpStatus();
		ErrorInfo errorInfo = new ErrorInfo(exception.getMessage(), httpStatus);
		return new ResponseEntity<>(new FailureResponse(errorInfo), httpStatus);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseBody
	public ResponseEntity<FailureResponse>
	                methodArguementTypeMismatchExceptionOccurent(MethodArgumentTypeMismatchException exception) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		String errorMessage = "Failed to convert value:{0} of type:{1} to type:{2} for param:{3}";

		String errorMsg = SmartUtil.formatString(errorMessage, exception.getValue(),
		                exception.getValue().getClass().getSimpleName(), exception.getRequiredType().getSimpleName(),
		                exception.getName());

		ErrorInfo errorInfo = new ErrorInfo(errorMsg, httpStatus);
		return new ResponseEntity<>(new FailureResponse(errorInfo), httpStatus);
	}

	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	public ResponseEntity<FailureResponse> businessExceptionOccured(BusinessException exception) {
		HttpStatus httpStatus = exception.getHttpStatus();
		ErrorInfo errorInfo = new ErrorInfo(exception.getMessage(), httpStatus);
		return new ResponseEntity<>(new FailureResponse(errorInfo), httpStatus);
	}

	@ExceptionHandler(TransactionException.class)
	@ResponseBody
	public ResponseEntity<FailureResponse> transactionExceptionOccured(TransactionException exception) {
		HttpStatus httpStatus = exception.getHttpStatus();
		ErrorInfo errorInfo = new ErrorInfo(exception.getMessage(), httpStatus);
		return new ResponseEntity<>(new FailureResponse(errorInfo), httpStatus);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	public ResponseEntity<FailureResponse>
	                httpMessageNotReadableExceptionOccured(HttpMessageNotReadableException exception) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ErrorInfo errorInfo = new ErrorInfo("Invalid request json body", httpStatus);
		return new ResponseEntity<>(new FailureResponse(errorInfo), httpStatus);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<FailureResponse>
	                handleMethodArgumentNotValidException(MethodArgumentNotValidException error) {
		BindingResult bindingResult = error.getBindingResult();
		List<ObjectError> allErrors = bindingResult.getAllErrors();
		List<ErrorInfo> errors = new ArrayList<>(allErrors.size());
		for (ObjectError objectError : allErrors) {
			String errorMessage = objectError.getObjectName() + objectError.getDefaultMessage();
			if (objectError instanceof FieldError) {
				FieldError fieldError = (FieldError) objectError;
				errorMessage = fieldError.getField() + ":" + objectError.getDefaultMessage();
			}
			errors.add(new ErrorInfo(errorMessage, HttpStatus.BAD_REQUEST));
		}
		return new ResponseEntity<>(new FailureResponse(errors, "Invalid request parameters"), HttpStatus.BAD_REQUEST);
	}

}
