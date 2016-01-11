package com.julvez.pfc.teachonsnap.error.model;

/**
 * Describes an error with the type of error and de key for the localized message.
 */
public class ErrorBean {
	private ErrorType type;
	private ErrorMessageKey messageKey;
	
	/**
	 * Constructor
	 * @param type error type from enum
	 * @param messageKey key for the localized message
	 * @see ErrorType
	 * @see ErrorMessageKey
	 */
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
