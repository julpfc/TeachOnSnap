package com.julvez.pfc.teachonsnap.media;

import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;
import com.julvez.pfc.teachonsnap.user.model.User;

public interface MediaFileService {
	
	public List<MediaFile> getLessonMedias(int idLessonMedia);

	public int saveMediaFile(Lesson lesson, FileMetadata file);

	public List<String> getAcceptedFileTypes();

	public Lesson removeMediaFiles(Lesson lesson);

	public boolean isAuthorQuotaExceeded(User author, long newFileSize);

	public boolean isRepositoryFull(short idMediaRepository, long newFileSize);		
	
}
