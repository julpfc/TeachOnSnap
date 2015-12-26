package com.julvez.pfc.teachonsnap.error.model;

public enum ErrorType {

	ERR_NONE,
	ERR_LOGIN, 
	ERR_SAVE, 
	ERR_SAVE_DUPLICATE,
	ERR_REMOVE, 
	ERR_INVALID_INPUT, 
	ERR_BANNED, 
	ERR_SEND;
	
	@Override
	public String toString() {
		return super.toString();
	}
	

}
