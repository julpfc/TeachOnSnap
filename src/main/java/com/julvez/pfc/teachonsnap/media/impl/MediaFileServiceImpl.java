package com.julvez.pfc.teachonsnap.media.impl;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.media.MediaFileService;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.media.model.MediaFileRepositoryPath;
import com.julvez.pfc.teachonsnap.media.model.MediaPropertyName;
import com.julvez.pfc.teachonsnap.media.model.MediaType;
import com.julvez.pfc.teachonsnap.media.repository.MediaFileRepository;
import com.julvez.pfc.teachonsnap.media.repository.MediaFileRepositoryFactory;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;

public class MediaFileServiceImpl implements MediaFileService {

	private MediaFileRepository mediaFileRepository = MediaFileRepositoryFactory.getRepository();
	
	private PropertyManager properties = PropertyManagerFactory.getManager();
	private StringManager stringManager = StringManagerFactory.getManager();
	
	@Override
	public List<MediaFile> getLessonMedias(int idLessonMedia) {
		List<MediaFile> medias = mediaFileRepository.getLessonMedias(idLessonMedia);		
		return medias;
	}

	@Override
	public int saveMediaFile(Lesson lesson, FileMetadata file) {
		int idMediaFile = -1;

		int maxFileSize = properties.getNumericProperty(MediaPropertyName.MEDIAFILE_MAX_SIZE);

		if(lesson!=null && file!=null && stringManager.isNumeric(file.getFileSize())){
			int fileSize = Integer.parseInt(file.getFileSize());
			if(fileSize > 0 && fileSize <= maxFileSize){
				short idMediaRepository = mediaFileRepository.getDefaultRepositoryID();
				MediaFileRepositoryPath repoPath = mediaFileRepository.getMediaFileRepositoryPath(idMediaRepository);
				short idMediaMimeType = mediaFileRepository.getMimeTypeID(file.getMediaType(),file.getFileType());
				
				if(idMediaMimeType == -1){
					idMediaMimeType = mediaFileRepository.createMimeTypeID(file.getMediaType(), file.getFileType(), file.getFileName());
				}
				
				idMediaFile = mediaFileRepository.saveMediaFile(lesson.getId(),file, repoPath, idMediaMimeType);
			}
		}
		return idMediaFile;
	}

	@Override
	public List<String> getAcceptedFileTypes() {
		List<String> mediaTypes = new ArrayList<String>();
		
		for(MediaType media:MediaType.values()){
			mediaTypes.add(media.toString().toLowerCase());
		}
		
		return mediaTypes;
	}

	@Override
	public Lesson removeMediaFiles(Lesson lesson) {
		Lesson modLesson = null;
		
		if(lesson != null && lesson.getId() > 0 && lesson.getIdLessonMedia() > 0){
			
			ArrayList<MediaFile> medias = (ArrayList<MediaFile>) getLessonMedias(lesson.getIdLessonMedia());
			
			if(medias != null && medias.size() > 0){
				short idMediaRepository = mediaFileRepository.getDefaultRepositoryID();
				MediaFileRepositoryPath repoPath = mediaFileRepository.getMediaFileRepositoryPath(idMediaRepository);
				boolean success = mediaFileRepository.removeMediaFiles(lesson.getId(), medias, repoPath);
				
				if(success){
					lesson.setIdLessonMedia(-1);
					lesson.setMediaType(null);
					modLesson = lesson;				
				}
			}
		}		
		return modLesson;
	}

}
