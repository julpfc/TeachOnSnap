package com.julvez.pfc.teachonsnap.media.impl;

import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.media.MediaFileService;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.media.model.MediaFileRepositoryPath;
import com.julvez.pfc.teachonsnap.media.repository.MediaFileRepository;
import com.julvez.pfc.teachonsnap.media.repository.MediaFileRepositoryFactory;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;

public class MediaFileServiceImpl implements MediaFileService {

	private MediaFileRepository mediaFileRepository = MediaFileRepositoryFactory.getRepository();
	
	@Override
	public List<MediaFile> getLessonMedias(int idLessonMedia) {
		List<MediaFile> medias = mediaFileRepository.getLessonMedias(idLessonMedia);		
		return medias;
	}

	@Override
	public MediaFile saveMediaFile(Lesson lesson, FileMetadata file) {
		MediaFile mediaFile = null;

		//TODO Controlar tamaño del fichero (máximo por properties)
		if(lesson!=null && file!=null){
			short idMediaRepository = mediaFileRepository.getDefaultRepositoryID();
			MediaFileRepositoryPath repoPath = mediaFileRepository.getMediaFileRepositoryPath(idMediaRepository);
			short idMediaMimeType = mediaFileRepository.getMimeTypeID(file.getMediaType(),file.getFileType());
			
			if(idMediaMimeType>0){
				int idMediaFile = mediaFileRepository.saveMediaFile(lesson.getId(),file, repoPath, idMediaMimeType);
				
				if (idMediaFile>0){
					//TODO Si creamos este método habría que cambiar el de la lista para que fuese de ids
					mediaFile = new MediaFile(); 
					//TODO Sin acabar
							//mediaFileRepository.getMediaFile(idMediaFile);
				}			
			}
		}
		return mediaFile;
	}

}
