package com.julvez.pfc.teachonsnap.service.media.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.media.MediaFile;
import com.julvez.pfc.teachonsnap.model.media.MediaFileRepositoryPath;
import com.julvez.pfc.teachonsnap.model.media.MediaType;
import com.julvez.pfc.teachonsnap.model.upload.FileMetadata;

public interface MediaFileRepository {

	public List<MediaFile> getLessonMedias(int idLessonMedia);

	public short getMimeTypeID(MediaType mediaType, String fileType);

	public short getDefaultRepositoryID();

	public int saveMediaFile(int idLesson, FileMetadata file, MediaFileRepositoryPath repoPath, short idMediaMimeType);

	public MediaFileRepositoryPath getMediaFileRepositoryPath(short idMediaRepository);
		
}
