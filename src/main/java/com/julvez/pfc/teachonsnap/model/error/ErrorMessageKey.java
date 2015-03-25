package com.julvez.pfc.teachonsnap.model.error;

public enum ErrorMessageKey {
	
	NONE(null),
	SAVE_NOCHANGES("save.nochanges");
		 		
	private final String key;
 
	private ErrorMessageKey(String messageKey) {
		key = messageKey;
	}

	@Override
	public String toString() {
		return key;
	}
}
