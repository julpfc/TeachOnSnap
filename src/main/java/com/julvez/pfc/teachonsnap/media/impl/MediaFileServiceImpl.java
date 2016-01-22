package com.julvez.pfc.teachonsnap.media.impl;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.media.MediaFileService;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.media.model.MediaFileRepositoryPath;
import com.julvez.pfc.teachonsnap.media.model.MediaMessageKey;
import com.julvez.pfc.teachonsnap.media.model.MediaPropertyName;
import com.julvez.pfc.teachonsnap.media.model.MediaType;
import com.julvez.pfc.teachonsnap.media.repository.MediaFileRepository;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.text.TextService;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Implementation of the MediaFileService interface, uses an internal {@link MediaFileRepository} 
 * to access/modify the lesson's media files related data.
 */
public class MediaFileServiceImpl implements MediaFileService {

	/** Repository than provides data access/modification */
	private MediaFileRepository mediaFileRepository;
	
	/** Property manager providing access to properties files */
	private PropertyManager properties;
	
	/** String manager providing string manipulation utilities */
	private StringManager stringManager;
	
	/** Log manager providing logging capabilities */
	private LogManager logger;
	
	/** Provides the functionality to work with application's URLs */
	private URLService urlService;
	
	/** Provides the functionality to work with application's users */
	private UserService userService;
	
	/** Provides the functionality to work with localized texts */
	private TextService textService;
	
	/** Provides the functionality to work with notifications */
	private NotifyService notifyService;
	
		
	/**
	 * Constructor requires all parameters not to be null
	 * @param mediaFileRepository Repository than provides data access/modification
	 * @param properties Property manager providing access to properties files
	 * @param stringManager String manager providing string manipulation utilities
	 * @param logger Log manager providing logging capabilities
	 * @param urlService Provides the functionality to work with application's URLs
	 * @param userService Provides the functionality to work with application's users
	 * @param textService Provides the functionality to work with localized texts
	 * @param notifyService Provides the functionality to work with notifications
	 */
	public MediaFileServiceImpl(MediaFileRepository mediaFileRepository,
			PropertyManager properties, StringManager stringManager,
			LogManager logger, URLService urlService, UserService userService,
			TextService textService, NotifyService notifyService) {
		
		if(mediaFileRepository == null || logger == null || stringManager == null 
			|| properties == null || urlService == null || userService == null 
			|| textService == null || notifyService == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		
		this.mediaFileRepository = mediaFileRepository;
		this.properties = properties;
		this.stringManager = stringManager;
		this.logger = logger;
		this.urlService = urlService;
		this.userService = userService;
		this.textService = textService;
		this.notifyService = notifyService;
	}

	@Override
	public List<MediaFile> getLessonMedias(int idLessonMedia) {
		//get list of Media files from repo
		List<MediaFile> medias = mediaFileRepository.getLessonMedias(idLessonMedia);	
		
		if(medias != null){
			//get absolute URL to media file
			for(MediaFile media:medias){
				media.setURLs(urlService.getMediaFileURL());
			}
		}
		return medias;
	}

	@Override
	public int saveMediaFile(Lesson lesson, FileMetadata file) {
		int idMediaFile = -1;

		//get max media file size allowed
		long maxFileSize = properties.getNumericProperty(MediaPropertyName.MEDIAFILE_MAX_SIZE);
		
		if(lesson!=null && file!=null && stringManager.isNumeric(file.getFileSize())){
			long fileSize = Integer.parseInt(file.getFileSize());
			//if file size is allowed
			if(fileSize > 0 && fileSize <= maxFileSize){
				short idMediaRepository = mediaFileRepository.getDefaultRepositoryID();
				//if quota is not exceeded
				if(!isRepositoryFull(idMediaRepository, fileSize) && !isAuthorQuotaExceeded(lesson.getAuthor(), fileSize)){
					//get path and mime type
					MediaFileRepositoryPath repoPath = mediaFileRepository.getMediaFileRepositoryPath(idMediaRepository);
					short idMediaMimeType = mediaFileRepository.getMimeTypeID(file.getMediaType(),file.getFileType());
					
					if(idMediaMimeType == -1){
						idMediaMimeType = mediaFileRepository.createMimeTypeID(file.getMediaType(), file.getFileType(), file.getFileName());
					}
					//save
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
			long quotaUsed = 0;

			//get max general quota per user
			long maxQuota = properties.getNumericProperty(MediaPropertyName.MAX_USER_REPOSITORY_SIZE);
			//get exceptional quota for this user
			long exceptionQuota = properties.getNumericProperty(MediaPropertyName.MAX_USER_REPOSITORY_SIZE_EXCEPTION , String.valueOf(author.getId()));
			
			//if there is exceptional quota, consider it as max quota
			if(exceptionQuota >= 0){
				maxQuota = exceptionQuota;
			}
			
			//if quota is limited
			if(maxQuota >= 0){
				//get used user quota
				quotaUsed = mediaFileRepository.getAuthorQuotaUsed(author.getId());				
			
				//check if exceeded
				exceeded = maxQuota < (quotaUsed + newFileSize);
			}
			else{
				//there is no max quota defined
				exceeded = false;
			}
			
			if(exceeded){
				//Notify if quota is exceeded
				notifyQuotaExceeded(author, maxQuota, quotaUsed, newFileSize);
				logger.error("Max user quota exceeded for " + author + " maxQuota=" + maxQuota + " quotaUsed=" + quotaUsed + " newFileSize=" + newFileSize);
			}
		}		
		
		return exceeded;
	}

	@Override
	public boolean isRepositoryFull(short idMediaRepository, long newFileSize) {
		boolean full = true;
		
		if(idMediaRepository > 0){
			//get max repository size
			long maxRepoSize = properties.getNumericProperty(MediaPropertyName.MAX_REPOSITORY_SIZE);
			
			long repoSizeUsed = 0;
			
			//if repository size is defined
			if(maxRepoSize >= 0){
				//get used repository size
				repoSizeUsed = mediaFileRepository.getRepositorySize(idMediaRepository);				
			
				//check if new file will exceed size
				full = maxRepoSize < (repoSizeUsed + newFileSize);
			}
			else{
				//there is no limit defined for repository
				full = false;
			}
			
			if(full){
				//Notify if repository is full
				notifyRepositoryFull(idMediaRepository, maxRepoSize, repoSizeUsed, newFileSize);
				logger.error("Max repository size exceeded " + idMediaRepository + " maxRepoSize=" + maxRepoSize + " repoSizeUsed=" + repoSizeUsed + " newFileSize=" + newFileSize);
			}
		}
		return full;
	}

	@Override
	public List<String> getAcceptedFileTypes() {
		List<String> mediaTypes = new ArrayList<String>();

		//get accepted media types from enum
		for(MediaType media:MediaType.values()){
			mediaTypes.add(media.toString().toLowerCase());
		}
		
		return mediaTypes;
	}

	@Override
	public Lesson removeMediaFiles(Lesson lesson) {
		Lesson modLesson = null;
		
		if(lesson != null && lesson.getId() > 0 && lesson.getIdLessonMedia() > 0){
			//get medias from repo
			ArrayList<MediaFile> medias = (ArrayList<MediaFile>) getLessonMedias(lesson.getIdLessonMedia());
			
			if(medias != null && medias.size() > 0){
				//get path
				short idMediaRepository = mediaFileRepository.getDefaultRepositoryID();
				MediaFileRepositoryPath repoPath = mediaFileRepository.getMediaFileRepositoryPath(idMediaRepository);
				//remove medias from lesson
				boolean success = mediaFileRepository.removeMediaFiles(lesson.getId(), medias, repoPath);
				
				//if success update lesson
				if(success){
					lesson.setIdLessonMedia(-1);
					lesson.setMediaType(null);
					modLesson = lesson;				
				}
			}
		}		
		return modLesson;
	}
	
	
	/**
	 * Notifies all administrators media repository is full
	 * @param idMediaRepository Repository that is full
	 * @param maxRepoSize Max repository size defined
	 * @param repoSizeUsed Used repository size
	 * @param newFileSize New file size than fills up the repository
	 */
	private void notifyRepositoryFull(short idMediaRepository, long maxRepoSize, long repoSizeUsed, long newFileSize) {		
		if(idMediaRepository > 0){
			//if repository is full
			if(maxRepoSize < (repoSizeUsed + newFileSize)){
				//get list of adminsitrators 
				List<User> admins = userService.getAdmins();
				MediaFileRepositoryPath repoPath = mediaFileRepository.getMediaFileRepositoryPath(idMediaRepository);

				if(admins != null && repoPath != null){
					//notify all administrators
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
	
	/**
	 * Notifies author his user quota is exceeded
	 * @param author which quota is exceeded
	 * @param maxQuota Max quota for this author
	 * @param quotaUsed Current used quota
	 * @param newFileSize New file size than exceeds his quota
	 */
	private void notifyQuotaExceeded(User author, long maxQuota, long quotaUsed, long newFileSize) {		
		if(author != null){
			//notify author
			String subject = textService.getLocalizedText(author.getLanguage(),MediaMessageKey.USER_QUOTA_EXCEEDED_SUBJECT);
			String message = textService.getLocalizedText(author.getLanguage(),MediaMessageKey.USER_QUOTA_EXCEEDED_MESSAGE, 
					String.valueOf(maxQuota), String.valueOf(quotaUsed), String.valueOf(newFileSize));
						
			notifyService.info(author, subject, message);						
		}
	}

}
