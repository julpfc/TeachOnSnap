package com.julvez.pfc.teachonsnap.controller.model;

/**
 * Enumeration with types of error from the application
 *
 */
public enum ErrorType {

	/**	OK, there is no error in the last action */
	ERR_NONE,
	
	/**	Login related error, wrong password, unregistered email, etc */
	ERR_LOGIN, 
	
	/**	There was a problem in a saving action */
	ERR_SAVE, 
	
	/**	There was a duplicate error in a saving action */
	ERR_SAVE_DUPLICATE,
	
	/**	There was a problem in a removing action */
	ERR_REMOVE, 
	
	/**	Some of the input values are not valid and the action cannot be performed */
	ERR_INVALID_INPUT, 
	
	/**	The action cannot be performed beacuse the user is banned */
	ERR_BANNED, 
	
	/**	THere was a problem in a sending action */
	ERR_SEND;
	
	@Override
	public String toString() {
		return super.toString();
	}
}
