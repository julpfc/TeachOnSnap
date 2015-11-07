package com.julvez.pfc.teachonsnap.media.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.media.model.MediaFileRepositoryPath;
import com.julvez.pfc.teachonsnap.media.model.MediaType;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;

public interface MediaFileRepository {

	public List<MediaFile> getLessonMedias(int idLessonMedia);

	public short getMimeTypeID(MediaType mediaType, String fileType);

	public short getDefaultRepositoryID();

	public int saveMediaFile(int idLesson, FileMetadata file, MediaFileRepositoryPath repoPath, short idMediaMimeType);

	public MediaFileRepositoryPath getMediaFileRepositoryPath(short idMediaRepository);
		
}
