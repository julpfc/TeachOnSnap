package com.julvez.pfc.teachonsnap.upload.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;

public class UploadRepositoryMap implements UploadRepository {

	/** Log manager providing logging capabilities */
	private LogManager logger;
	
	/**
	 * Map storing references to user's upload repositories
	 */
	private Map<String, List<FileMetadata>> temporaryFileRepository; 
			
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param logger Log manager providing logging capabilities
	 */
	public UploadRepositoryMap(LogManager logger) {
		if(logger == null){
			throw new IllegalArgumentException("Parameters cannot be null.");	
		}
		this.logger = logger;
		this.temporaryFileRepository = Collections.synchronizedMap(new HashMap<String, List<FileMetadata>>());
	}

	@Override
	public FileMetadata getTemporaryFile(int idUser, int index) {
		FileMetadata file = null;
		
		List<FileMetadata> userFiles = temporaryFileRepository.get(""+idUser);
		
		if(userFiles!=null && userFiles.size()>index){
			file = userFiles.get(index);
		}
		return file;
	}

	@Override
	public List<FileMetadata> getTemporaryFiles(int idUser) {
		return temporaryFileRepository.get(""+idUser);
	}

	@Override
	public void addTemporaryFiles(int idUser, List<FileMetadata> uploadFiles) {
		List<FileMetadata> userFiles = getList(""+idUser);
		
		userFiles.addAll(uploadFiles);
	}
	
	
	@Override
	public void close() {
		synchronized (temporaryFileRepository) {
			for(List<FileMetadata> files : temporaryFileRepository.values()){				
				for(FileMetadata file:files){ 
					closeFile(file);
				}
			}			
			logger.info("All files closed.");
		}		
	}

	@Override
	public void removeTemporaryFile(int idUser, int index) {
		List<FileMetadata> userFiles = temporaryFileRepository.get(""+idUser);
		
		if(userFiles!=null && index < userFiles.size()){
			FileMetadata file = userFiles.remove(index);
			closeFile(file);
		}
	}

	@Override
	public void removeTemporaryFiles(int idUser) {
		List<FileMetadata> files = temporaryFileRepository.get(""+idUser);
		
		if(files!=null){
			for(FileMetadata file:files){ 
				closeFile(file);
			}
					
			files.clear(); 
			logger.info("All user's files closed: "+idUser);
		}
		
	}
	
	/**
	 * Closes a file's input stream
	 * @param file 's metadata
	 */
	private void closeFile(FileMetadata file){
		try {
			file.getContent().close();
			logger.debug("Closing file "+file);
		} catch (Throwable t) {
			logger.error(t, "Error closing file: " + file);
		}
	}	

	/**
	 * Returns user's upload repository, if not exists it's created
	 * @param key User's id
	 * @return User's upload files list
	 */
	private List<FileMetadata> getList(String key){
		List<FileMetadata> userFiles = temporaryFileRepository.get(key);
		
		if(userFiles==null){
			synchronized (temporaryFileRepository) {
				userFiles = temporaryFileRepository.get(key);
				if(userFiles==null){
					temporaryFileRepository.put(key,Collections.synchronizedList(new LinkedList<FileMetadata>()));
			
					userFiles = temporaryFileRepository.get(key);
					
					logger.debug("New repository created: "+key);
				}
			}
		}
		
		return userFiles;
	}
}
