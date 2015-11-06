package com.julvez.pfc.teachonsnap.service.request;

public interface RequestService {

	public String getHost();
	
	public String getAbsoluteURL(String relativeURL);

	public String getHomeURL();

}
