package com.julvez.pfc.teachonsnap.manager.string;

public interface StringManager {

	public boolean isEmpty(String string);

	public boolean isTrue(String string);
	
	public String generateURIname(String source);

	public String getKey(Object... objects);
}
