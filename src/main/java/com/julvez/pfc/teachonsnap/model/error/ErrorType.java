package com.julvez.pfc.teachonsnap.model.error;

public enum ErrorType {

	ERR_NONE,
	ERR_LOGIN, 
	ERR_SAVE, 
	ERR_SAVE_DUPLICATE,
	ERR_REMOVE, 
	ERR_INVALID_INPUT;
	
	@Override
	public String toString() {
		return super.toString();
	}
	

}
