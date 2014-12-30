package com.julvez.pfc.teachonsnap.service.media;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.media.MediaFile;
import com.julvez.pfc.teachonsnap.model.upload.FileMetadata;

public interface MediaFileService {
	
	public List<MediaFile> getLessonMedias(int idLessonMedia);

	public MediaFile saveMediaFile(Lesson lesson, FileMetadata file);

		
	
}
