package com.julvez.pfc.teachonsnap.upload.repository;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;

public class UploadRepositoryMap implements UploadRepository {

	private Map<String, List<FileMetadata>> temporaryFileRepository = 
			Collections.synchronizedMap(new HashMap<String, List<FileMetadata>>());

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
	
	private List<FileMetadata> getList(String key){
		List<FileMetadata> userFiles = temporaryFileRepository.get(key);
		
		if(userFiles==null){
			synchronized (temporaryFileRepository) {
				userFiles = temporaryFileRepository.get(key);
				if(userFiles==null){
					temporaryFileRepository.put(key,Collections.synchronizedList(new LinkedList<FileMetadata>()));
			
					userFiles = temporaryFileRepository.get(key);
					
					System.out.println("UploadRepositoryMap: creada "+key);
				}
			}
		}
		
		return userFiles;
	}

	@Override
	public void close() {
		synchronized (temporaryFileRepository) {
			for(List<FileMetadata> files : temporaryFileRepository.values()){				
				for(FileMetadata file:files){ 
					try {
						file.getContent().close();
						System.out.println("UploadRepositoryMap: Cerrando fichero "+file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}			
			System.out.println("UploadRepository: Todos los ficheros cerrados.");
		}		
	}

	@Override
	public void removeTemporaryFile(int idUser, int index) {
		List<FileMetadata> userFiles = temporaryFileRepository.get(""+idUser);
		
		if(userFiles!=null && index<userFiles.size()){
			userFiles.remove(index);
		}
	}

	@Override
	public void removeTemporaryFiles(int idUser) {
		List<FileMetadata> files = temporaryFileRepository.get(""+idUser);
		
		if(files!=null){
			for(FileMetadata file:files){ 
				try {
					file.getContent().close();
					System.out.println("UploadRepositoryMap: Cerrando fichero "+file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
					
			files.clear(); 
			System.out.println("UploadRepositoryMap: Ficheros cerrados del usuario "+idUser);
		}
		
	}
	
	
}
