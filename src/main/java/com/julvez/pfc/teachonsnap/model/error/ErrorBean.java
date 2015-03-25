package com.julvez.pfc.teachonsnap.model.error;

public class ErrorBean {
	ErrorType type;
	ErrorMessageKey messageKey;

	public ErrorBean(ErrorType type, ErrorMessageKey messageKey) {
		this.type = type;
		this.messageKey = messageKey;
	}
	public ErrorType getType() {
		return type;
	}
	
	public String getMessageKey() {
		return messageKey.toString();
	}	
	
	@Override
	public String toString() {
		return "Error [type=" + type + ", messageKey=" + messageKey + "]";
	}
	
}
