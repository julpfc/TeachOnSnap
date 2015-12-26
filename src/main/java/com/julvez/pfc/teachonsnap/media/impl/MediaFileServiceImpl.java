package com.julvez.pfc.teachonsnap.media.impl;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.media.MediaFileService;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.media.model.MediaFileRepositoryPath;
import com.julvez.pfc.teachonsnap.media.model.MediaMessageKey;
import com.julvez.pfc.teachonsnap.media.model.MediaPropertyName;
import com.julvez.pfc.teachonsnap.media.model.MediaType;
import com.julvez.pfc.teachonsnap.media.repository.MediaFileRepository;
import com.julvez.pfc.teachonsnap.media.repository.MediaFileRepositoryFactory;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.notify.NotifyServiceFactory;
import com.julvez.pfc.teachonsnap.text.TextService;
import com.julvez.pfc.teachonsnap.text.TextServiceFactory;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;

public class MediaFileServiceImpl implements MediaFileService {

	private MediaFileRepository mediaFileRepository = MediaFileRepositoryFactory.getRepository();
	
	private PropertyManager properties = PropertyManagerFactory.getManager();
	private StringManager stringManager = StringManagerFactory.getManager();
	private LogManager logger = LogManagerFactory.getManager();
	
	private URLService urlService = URLServiceFactory.getService();
	private UserService userService = UserServiceFactory.getService();
	private TextService textService = TextServiceFactory.getService();
	private NotifyService notifyService = NotifyServiceFactory.getService();
	
	@Override
	public List<MediaFile> getLessonMedias(int idLessonMedia) {
		List<MediaFile> medias = mediaFileRepository.getLessonMedias(idLessonMedia);	
		
		if(medias != null){
			for(MediaFile media:medias){
				media.setURLs(urlService.getMediaFileURL());
			}
		}
		return medias;
	}

	@Override
	public int saveMediaFile(Lesson lesson, FileMetadata file) {
		int idMediaFile = -1;

		long maxFileSize = properties.getNumericProperty(MediaPropertyName.MEDIAFILE_MAX_SIZE);
		
		if(lesson!=null && file!=null && stringManager.isNumeric(file.getFileSize())){
			long fileSize = Integer.parseInt(file.getFileSize());
			if(fileSize > 0 && fileSize <= maxFileSize){
				short idMediaRepository = mediaFileRepository.getDefaultRepositoryID();
				if(!isRepositoryFull(idMediaRepository, fileSize) && !isAuthorQuotaExceeded(lesson.getAuthor(), fileSize)){ 
					MediaFileRepositoryPath repoPath = mediaFileRepository.getMediaFileRepositoryPath(idMediaRepository);
					short idMediaMimeType = mediaFileRepository.getMimeTypeID(file.getMediaType(),file.getFileType());
					
					if(idMediaMimeType == -1){
						idMediaMimeType = mediaFileRepository.createMimeTypeID(file.getMediaType(), file.getFileType(), file.getFileName());
					}
					
					idMediaFile = mediaFileRepository.saveMediaFile(lesson.getId(), file, repoPath, idMediaMimeType);
				}				
			}
		}
		return idMediaFile;
	}

	@Override
	public boolean isAuthorQuotaExceeded(User author, long newFileSize) {
		boolean exceeded = true;
		
		if(author != null && newFileSize > 0){
			long maxQuota = properties.getNumericProperty(MediaPropertyName.MAX_USER_REPOSITORY_SIZE);
			long exceptionQuota = properties.getNumericProperty(MediaPropertyName.MAX_USER_REPOSITORY_SIZE_EXCEPTION , String.valueOf(author.getId()));
			
			if(exceptionQuota >= 0){
				maxQuota = exceptionQuota;
			}
			
			long quotaUsed = 0;
			if(maxQuota >= 0){
				quotaUsed = mediaFileRepository.getAuthorQuotaUsed(author.getId());				
			
				exceeded = maxQuota < (quotaUsed + newFileSize);
			}
			else{
				exceeded = false;
			}
			
			if(exceeded){
				notifyQuotaExceeded(author, maxQuota, quotaUsed, newFileSize);
				logger.error("Se superó la cuota del usuario " + author + " maxQuota=" + maxQuota + " quotaUsed=" + quotaUsed + " newFileSize=" + newFileSize);
			}
		}		
		
		return exceeded;
	}

	@Override
	public boolean isRepositoryFull(short idMediaRepository, long newFileSize) {
		boolean full = true;
		
		if(idMediaRepository > 0){
			long maxRepoSize = properties.getNumericProperty(MediaPropertyName.MAX_REPOSITORY_SIZE);
			
			long repoSizeUsed = 0;
			if(maxRepoSize >= 0){
				repoSizeUsed = mediaFileRepository.getRepositorySize(idMediaRepository);				
			
				full = maxRepoSize < (repoSizeUsed + newFileSize);
			}
			else{
				full = false;
			}
			
			if(full){
				notifyRepositoryFull(idMediaRepository, maxRepoSize, repoSizeUsed, newFileSize);
				logger.error("Se superó el tamaño máximo del repositorio " + idMediaRepository + " maxRepoSize=" + maxRepoSize + " repoSizeUsed=" + repoSizeUsed + " newFileSize=" + newFileSize);
			}
		}
		return full;
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
	
	
	private void notifyRepositoryFull(short idMediaRepository, long maxRepoSize, long repoSizeUsed, long newFileSize) {
		
		if(idMediaRepository > 0){
			if(maxRepoSize < (repoSizeUsed + newFileSize)){
				List<User> admins = userService.getAdmins();
				MediaFileRepositoryPath repoPath = mediaFileRepository.getMediaFileRepositoryPath(idMediaRepository);

				if(admins != null && repoPath != null){										
					for(User admin:admins){
						String subject = textService.getLocalizedText(admin.getLanguage(),MediaMessageKey.REPOSITORY_FULL_SUBJECT, String.valueOf(repoPath.getId()));
						String message = textService.getLocalizedText(admin.getLanguage(),MediaMessageKey.REPOSITORY_FULL_MESSAGE, 
								String.valueOf(repoPath.getId()),repoPath.getURI(), String.valueOf(maxRepoSize), String.valueOf(repoSizeUsed), String.valueOf(newFileSize));
						
						notifyService.info(admin, subject, message);
					}	
				}
			}			
		}
	}
	
	private void notifyQuotaExceeded(User author, long maxQuota, long quotaUsed, long newFileSize) {
		
		if(author != null){

			String subject = textService.getLocalizedText(author.getLanguage(),MediaMessageKey.USER_QUOTA_EXCEEDED_SUBJECT);
			String message = textService.getLocalizedText(author.getLanguage(),MediaMessageKey.USER_QUOTA_EXCEEDED_MESSAGE, 
					String.valueOf(maxQuota), String.valueOf(quotaUsed), String.valueOf(newFileSize));
						
			notifyService.info(author, subject, message);						
		}
	}

}
