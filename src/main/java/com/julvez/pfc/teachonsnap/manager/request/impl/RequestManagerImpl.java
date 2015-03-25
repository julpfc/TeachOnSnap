package com.julvez.pfc.teachonsnap.manager.request.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.julvez.pfc.teachonsnap.manager.request.Attribute;
import com.julvez.pfc.teachonsnap.manager.request.Header;
import com.julvez.pfc.teachonsnap.manager.request.Parameter;
import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.SessionAttribute;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.error.ErrorBean;
import com.julvez.pfc.teachonsnap.model.error.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.model.error.ErrorType;
import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.media.MediaType;
import com.julvez.pfc.teachonsnap.model.upload.FileMetadata;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.service.upload.UploadService;
import com.julvez.pfc.teachonsnap.service.upload.UploadServiceFactory;

public class RequestManagerImpl implements RequestManager {

	private StringManager stringManager = StringManagerFactory.getManager();
	private UploadService uploadService = UploadServiceFactory.getService();
	
	@Override
	public short getSessionIdLanguage(HttpServletRequest request) {
		short id = -1;
		Short idlang = (Short)request.getSession(true).getAttribute(SessionAttribute.IDLANGUAGE.toString());
		if(idlang!=null) id=idlang.shortValue();
		return id;
	}

	@Override
	public User getSessionUser(HttpServletRequest request) {
		return (User)request.getSession(true).getAttribute(SessionAttribute.USER.toString());
	}

	@Override
	public void setUserSessionLanguage(HttpServletRequest request,
			Language userLang) {
		
		if(userLang!=null){
			short sessionIdLang = getSessionIdLanguage(request);
			
			if(sessionIdLang != userLang.getId()){
				request.getSession(true).setAttribute(SessionAttribute.IDLANGUAGE.toString(), userLang.getId());
			}
		}		
	}

	@Override
	public String[] getControllerParams(HttpServletRequest request) {
		String[] params = null;
		String req = request.getRequestURI().replaceFirst(request.getServletPath()+"/", "");
				
		if(req.contains("/")){
			params = req.split("/");
		}
		else if(!stringManager.isEmpty(req))
			params = new String[]{req};		
		
		return params;
	}

	
	private String getParam(HttpServletRequest request,Parameter parameter) {
		String param = request.getParameter(parameter.toString());
		if(stringManager.isEmpty(param)){
			param = null;
		}
		return param;
	}
	
	@Override
	public String getParamChangeLanguage(HttpServletRequest request) {
		return getParam(request,Parameter.CHANGE_LANGUAGE);		
	}	
	
	@Override
	public String getParamLoginEmail(HttpServletRequest request) {		
		return getParam(request,Parameter.LOGIN_EMAIL);
	}

	@Override
	public String getParamLoginPassword(HttpServletRequest request) {		
		return getParam(request,Parameter.LOGIN_PASSWORD);
	}

	@Override
	public void setUserSession(HttpServletRequest request, User user) {
		request.getSession(true).setAttribute(SessionAttribute.USER.toString(), user);
	}

	@Override
	public void setErrorSession(HttpServletRequest request, ErrorBean error) {
		if(error!=null){
			request.getSession(true).setAttribute(SessionAttribute.ERROR.toString(), error);			
		}			
	}

	@Override
	public ErrorBean getErrorSession(HttpServletRequest request) {
		Object obj = request.getSession(true).getAttribute(SessionAttribute.ERROR.toString());
		if(obj!=null)
			return (ErrorBean)obj;
		else return new ErrorBean(ErrorType.ERR_NONE,ErrorMessageKey.NONE);
	}

	@Override
	public void setLastPage(HttpServletRequest request) {
		request.getSession(true).setAttribute(SessionAttribute.LAST_PAGE.toString(), request.getRequestURI());		
	}

	@Override
	public String getLastPage(HttpServletRequest request) {
		return (String) request.getSession(true).getAttribute(SessionAttribute.LAST_PAGE.toString());				
	}

	@Override
	public boolean getParamLogout(HttpServletRequest request) {
		String param = getParam(request,Parameter.LOGOUT);
		return stringManager.isTrue(param);
	}

	@Override
	public List<FileMetadata> getUploadFiles(HttpServletRequest request) {
		List<FileMetadata> files = new LinkedList<FileMetadata>();
		
	    Collection<Part> parts;
		try {
			parts = request.getParts();
			
			FileMetadata temp = null;
			
			for(Part part:parts){  
				if(part.getContentType() != null){
                
					temp = new FileMetadata();
					temp.setFileName(getFilename(part));
					temp.setFileSize(part.getSize()/1024 +" Kb");
					temp.setFileType(part.getContentType());
					temp.setContent(part.getInputStream());
					temp.setMediaType(MediaType.valueOf(getParam(request, Parameter.LESSON_NEW_FILE_ATTACH).toUpperCase()));
					
					System.out.println("Upload.Part: "+temp);
					files.add(temp);
				}
			}
		 
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ServletException e) {
			
			e.printStackTrace();
		}
	 
		return files;
	}
	
	private String getFilename(Part part) {
		String filename = null;
	        for (String cd : part.getHeader(Header.CONTENT_DISPOSITION.toString()).split(";")) {
	            if (cd.trim().startsWith("filename")) {
	                filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	                filename = filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
	            }
	        }
	        return filename;
	    }

	@Override
	public Lesson getParamNewLesson(Map<String, String[]> parameterMap) {
		Lesson lesson = null;
		
		if(parameterMap.get(Parameter.LESSON_NEW_TITLE.toString())!=null && parameterMap.get(Parameter.LESSON_NEW_TITLE.toString()).length>0){
			String title = parameterMap.get(Parameter.LESSON_NEW_TITLE.toString())[0];
			if(parameterMap.get(Parameter.LESSON_NEW_LANGUAGE.toString())!=null && parameterMap.get(Parameter.LESSON_NEW_LANGUAGE.toString()).length>0){
				String lang = parameterMap.get(Parameter.LESSON_NEW_LANGUAGE.toString())[0];
				short idLanguage = 0;
				
				if(stringManager.isNumeric(lang)){
					idLanguage = Short.valueOf(lang);					
				
					if(idLanguage>0){
						
						lesson = new Lesson();
						lesson.setTitle(title);
						lesson.setIdLanguage(idLanguage);					
						if(parameterMap.get(Parameter.LESSON_NEW_TEXT.toString())!=null && parameterMap.get(Parameter.LESSON_NEW_TEXT.toString()).length>0){
							lesson.setText(parameterMap.get(Parameter.LESSON_NEW_TEXT.toString())[0]);
						}
					}
				} 
			}				
		}
		
		return lesson;
	}

	@Override
	public List<String> getParamNewTags(Map<String, String[]> parameterMap) {
		List<String> tags = null;
	
		if(parameterMap.get(Parameter.LESSON_NEW_TAGS.toString())!=null && parameterMap.get(Parameter.LESSON_NEW_TAGS.toString()).length>0){
			tags = new ArrayList<String>();
			for(String tag:parameterMap.get(Parameter.LESSON_NEW_TAGS.toString())){
				if(!stringManager.isEmpty(tag)){
					tags.add(tag);
				}
			}
		}
		return tags;
	}

	@Override
	public FileMetadata getSubmittedFile(HttpServletRequest request) {
		FileMetadata file = null;
		User user = getSessionUser(request);
		String attach = getParam(request, Parameter.LESSON_NEW_FILE_ATTACH);
		
		if(user!=null && !stringManager.isEmpty(attach)){
			MediaType mediaType = MediaType.valueOf(attach.toUpperCase());
			String index = null;
			
			if(mediaType!=null){
				switch (mediaType) {
				case VIDEO:
					index =  getParam(request, Parameter.LESSON_NEW_VIDEO_INDEX);
					break;
				case AUDIO:
					index =  getParam(request, Parameter.LESSON_NEW_AUDIO_INDEX);
					break;
				}
				
				if(stringManager.isNumeric(index)){
					int mediaIndex = Integer.parseInt(index);
										
					if(mediaIndex>=0){
						file = uploadService.getTemporaryFile(user, mediaType , mediaIndex);
					}
				}
			}
		}		
		
		return file;
	}

	@Override
	public List<String> getParamNewSources(Map<String, String[]> parameterMap) {
		List<String> sources = null;
		
		if(parameterMap.get(Parameter.LESSON_NEW_SOURCES.toString())!=null && parameterMap.get(Parameter.LESSON_NEW_SOURCES.toString()).length>0){
			sources = new ArrayList<String>();
			for(String source:parameterMap.get(Parameter.LESSON_NEW_SOURCES.toString())){
				if(!stringManager.isEmpty(source)){
					sources.add(source);
				}
			}
		}
		return sources;
	}

	@Override
	public List<String> getParamNewMoreInfos(Map<String, String[]> parameterMap) {
		List<String> moreInfos = null;
		
		if(parameterMap.get(Parameter.LESSON_NEW_MOREINFOS.toString())!=null && parameterMap.get(Parameter.LESSON_NEW_MOREINFOS.toString()).length>0){
			moreInfos = new ArrayList<String>();
			for(String moreInfo:parameterMap.get(Parameter.LESSON_NEW_MOREINFOS.toString())){
				if(!stringManager.isEmpty(moreInfo)){
					moreInfos.add(moreInfo);
				}
			}
		}
		return moreInfos;
	}

	@Override
	public String getParamComment(HttpServletRequest request) {
		return getParam(request,Parameter.LESSON_COMMENT);
	}

	@Override
	public int getParamCommentID(HttpServletRequest request) {
		int commentID = -1;
		String param = getParam(request, Parameter.LESSON_COMMENTID);
		if(stringManager.isNumeric(param)){
			commentID = Integer.parseInt(param);			
		}
		return commentID;
	}

	@Override
	public boolean getParamEditComment(HttpServletRequest request) {
		String param = getParam(request,Parameter.LESSON_COMMENT_EDIT);
		return stringManager.isTrue(param);
	}

	@Override
	public String getParamBanComment(HttpServletRequest request) {
		return getParam(request,Parameter.LESSON_COMMENT_BAN);
	}

	@Override
	public int getAttributeErrorStatusCode(HttpServletRequest request) {
		int statusCode = -1;
		Object sc = request.getAttribute(Attribute.INT_ERROR_STATUS_CODE.toString());
		if(sc != null){
			statusCode = (int)sc;		
		}
		return statusCode;
	}

	@Override
	public Throwable getAttributeErrorException(HttpServletRequest request) {
		Throwable t = null;
		Object exception = request.getAttribute(Attribute.THROWABLE_ERROR_EXCEPTION.toString());
		if(exception != null){
			t = (Throwable)exception;		
		}
		return t;
	}

	@Override
	public String getRequestLanguage(HttpServletRequest request) {
		String lang = null;
		Locale locale = request.getLocale();
		if(locale!=null && !stringManager.isEmpty(locale.getLanguage())){
			lang = locale.getLanguage();
		}
		return lang;
	}

	@Override
	public String getParamPublishLessonTest(HttpServletRequest request) {
		return getParam(request,Parameter.LESSON_TEST_PUBLISH);
	}

	@Override
	public String getParamJSON(HttpServletRequest request) {
		return getParam(request,Parameter.JSON);
	}

	@Override
	public String getParamExport(HttpServletRequest request) {
		return getParam(request, Parameter.EXPORT);
	}


}
