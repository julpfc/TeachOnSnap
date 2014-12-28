package com.julvez.pfc.teachonsnap.repository.upload.map;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.julvez.pfc.teachonsnap.model.upload.ContentType;
import com.julvez.pfc.teachonsnap.model.upload.FileMetadata;
import com.julvez.pfc.teachonsnap.repository.upload.UploadRepository;

public class UploadRepositoryMap implements UploadRepository {

	private Map<String, Map<ContentType,List<FileMetadata>>> temporaryFileRepository = 
			Collections.synchronizedMap(new HashMap<String, Map<ContentType,List<FileMetadata>>>());

	@Override
	public FileMetadata getTemporaryFile(int idUser, ContentType contentType, int index) {
		FileMetadata file = null;
		Map<ContentType,List<FileMetadata>> userMap = temporaryFileRepository.get(""+idUser);
		
		if(userMap!=null){
			List<FileMetadata> userFiles = userMap.get(contentType);
			if(userFiles!=null && userFiles.size()>index){
				file = userFiles.get(index);
			}			
		}		
		
		return file;
	}

	@Override
	public List<FileMetadata> getTemporaryFiles(int idUser, ContentType contentType) {
		List<FileMetadata> userFiles = null;
		Map<ContentType,List<FileMetadata>> userMap = temporaryFileRepository.get(""+idUser);
		if(userMap!=null)
			userFiles = userMap.get(contentType);
		return userFiles;
	}

	@Override
	public void addTemporaryFiles(int idUser, ContentType contentType, List<FileMetadata> uploadFiles) {
		List<FileMetadata> userFiles = getList(""+idUser,contentType);
		
		userFiles.addAll(uploadFiles);
	}
	
	private List<FileMetadata> getList(String key, ContentType contentType){
		Map<ContentType,List<FileMetadata>> userMap = temporaryFileRepository.get(key);
		
		if(userMap==null){
			synchronized (temporaryFileRepository) {
				userMap = temporaryFileRepository.get(key);
				if(userMap==null){
					temporaryFileRepository.put(key,Collections.synchronizedMap(new HashMap<ContentType,List<FileMetadata>>()));
			
					userMap = temporaryFileRepository.get(key);
					
					for(ContentType ct:ContentType.values()){
						userMap.put(ct,new LinkedList<FileMetadata>());
					}
					System.out.println("UploadRepositoryMap: creada "+key);
					userMap = temporaryFileRepository.get(key);
				}
			}
		}
		
		return userMap.get(contentType);
	}

	@Override
	public void close() {
		synchronized (temporaryFileRepository) {
			for(Map<ContentType,List<FileMetadata>> maps:temporaryFileRepository.values()){
				for(List<FileMetadata> files : maps.values()){				
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
			}
			System.out.println("UploadRepository: Todos los ficheros cerrados.");
		}		
	}

	@Override
	public void removeTemporaryFile(int idUser, ContentType contentType, int index) {
		Map<ContentType,List<FileMetadata>> userMap = temporaryFileRepository.get(""+idUser);
		
		if(userMap!=null){
		
			List<FileMetadata> userFiles = userMap.get(contentType);
		
			if(userFiles!=null && index<userFiles.size()){
				userFiles.remove(index);
			}
		}
	}
	
	
}
