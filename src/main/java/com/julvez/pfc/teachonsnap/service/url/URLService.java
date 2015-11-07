package com.julvez.pfc.teachonsnap.service.url;


public interface URLService {

	public String getHost();
	
	public String getAbsoluteURL(String relativeURL);

	public String getHomeURL();
	

}
