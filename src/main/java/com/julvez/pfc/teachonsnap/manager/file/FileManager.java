package com.julvez.pfc.teachonsnap.manager.file;

import java.io.InputStream;

public interface FileManager {

	boolean copyStream(InputStream inputStream, String path, String fileName);

}
