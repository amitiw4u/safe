package com.sf.error;

public enum ErrorMessage {

  SOMETHING_WENT_WRONG("Something went wrong!"),

  INVALID_DATE_FILTERS("Invalid Date Filters! ", "");

  private String businessErrorMessage;
  private String technicalErrorMessage;

  ErrorMessage(String businessErrorMessage, String technicalErrorMessage) {
    this.businessErrorMessage = businessErrorMessage;
    this.technicalErrorMessage = technicalErrorMessage;
  }

  ErrorMessage(String businessErrorMessage) {
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
