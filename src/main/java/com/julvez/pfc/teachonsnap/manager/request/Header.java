package com.julvez.pfc.teachonsnap.manager.request;

public enum Header {
	
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
