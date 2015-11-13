package com.julvez.pfc.teachonsnap.manager.file;

import java.io.InputStream;

public interface FileManager {

	boolean copyStream(InputStream inputStream, String path, String fileName);

	String getFileExtension(String fileName);

	boolean delete(String path, String fileName);

	
}
