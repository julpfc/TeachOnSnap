package com.julvez.pfc.teachonsnap.manager.request;

public enum Header {
	
	ACCEPT_LANG("accept-language"),
	CONTENT_DISPOSITION("content-disposition");
		 		
	private final String realName;
 
	private Header(String httpHeaderName) {
		realName = httpHeaderName;
	}

	@Override
	public String toString() {
		return realName;
	}
}
