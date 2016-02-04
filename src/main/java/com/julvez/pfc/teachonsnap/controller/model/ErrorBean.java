package com.julvez.pfc.teachonsnap.controller.model;

/**
 * Describes an error with the type of error and de key for the localized message.
 */
public class ErrorBean {
	/** Error type from enum */
	private ErrorType type;
	
	/** Key for the localized message */
	private ErrorMessageKey messageKey;
		
	public ErrorBean(ErrorType type, ErrorMessageKey messageKey) {
		this.type = type;
		this.messageKey = messageKey;
	}

	/**
	 * @return Error type
	 * @see ErrorType
	 */
	public ErrorType getType() {
		return type;
	}
	
	/**
	 * @return String key for the localized message associated to this error
	 */
	public String getMessageKey() {
		return messageKey.toString();
	}	
	
	@Override
	public String toString() {
		return "Error [type=" + type + ", messageKey=" + messageKey + "]";
	}
	
}
