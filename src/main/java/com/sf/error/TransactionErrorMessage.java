package com.sf.error;

public enum TransactionErrorMessage {

  FAILED_TO_UPDATE("Failed to update");

  private String businessErrorMessage;
  private String technicalErrorMessage;

  TransactionErrorMessage(String businessErrorMessage, String technicalErrorMessage) {
    this.businessErrorMessage = businessErrorMessage;
    this.technicalErrorMessage = technicalErrorMessage;
  }

  TransactionErrorMessage(String businessErrorMessage) {
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
