package com.sf.exception;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import com.sf.error.ErrorMessage;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SafeException extends RuntimeException {

  private static final long serialVersionUID = 2154881530455922530L;

  protected HttpStatus httpStatus;

  protected String message;

  protected Exception e;

  public SafeException(String message, HttpStatus code, Exception e) {
    super();
    this.httpStatus = code == null ? HttpStatus.OK : code;
    this.e = e;
    this.message = message;
    if (StringUtils.isEmpty(this.message)) {
      this.message = e != null ? e.getMessage()
          : ErrorMessage.SOMETHING_WENT_WRONG.getBusinessErrorMessage();
    }
  }

  public SafeException(Exception e) {
    this(e.getMessage(), e);
  }

  public SafeException(String message) {
    this(message, null, null);
  }

  public SafeException(String message, HttpStatus code) {
    this(message, code, null);
  }

  public SafeException(String message, Exception e) {
    this(message, null, e);
  }

  public SafeException(Exception e, HttpStatus code) {
    this(e.getMessage(), code, e);
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Exception getE() {
    return e;
  }

  public void setE(Exception e) {
    this.e = e;
  }
}
