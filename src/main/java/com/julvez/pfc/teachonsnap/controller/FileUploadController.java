package com.julvez.pfc.teachonsnap.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.controller.model.SessionAttribute;
import com.julvez.pfc.teachonsnap.manager.json.JSONManager;
import com.julvez.pfc.teachonsnap.manager.json.JSONManagerFactory;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManagerFactory;
import com.julvez.pfc.teachonsnap.media.model.MediaType;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.upload.UploadService;
import com.julvez.pfc.teachonsnap.upload.UploadServiceFactory;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Servlet implementation class FileUploadController. 
 * <p>
 * Uploads a media file to a temporary repository at the application's server. 
 * Files can be listed, downloaded or removed.  
 * <p>
 * It's called from editLesson.jsp view.
 * <p>
 * It's called from the view directly or via ajax (responding in JSON format).
 * <p>
 * Mapped in {@link ControllerURI#UPLOAD}
 */
@MultipartConfig
public class FileUploadController extends HttpServlet {
	
	private static final long serialVersionUID = -6384462383602712259L;

	/** Provides the functionality to upload files to the application */
	private UploadService uploadService;
	
	/** Provides {@link HttpServletRequest} and {@link HttpServletResponse} access/manipulation utilities */
	private RequestManager requestManager;
	
	/** JSON manager providing JSON manipulation utilities */
	private JSONManager jsonManager;
	
	/** Log manager providing logging capabilities */
	private LogManager logger;
	
	
    /**
     * Default constructor
     */
    public FileUploadController() {
        this(UploadServiceFactory.getService(),
        	RequestManagerFactory.getManager(),
        	JSONManagerFactory.getManager(),
        	LogManagerFactory.getManager());
    }
            
    /**
     * Constructor requires all parameters not to be null
	 * @param uploadService Provides the functionality to upload files to the application
	 * @param requestManager Provides {@link HttpServletRequest} and {@link HttpServletResponse} access/manipulation utilities
	 * @param jsonManager JSON manager providing JSON manipulation utilities 
	 * @param logger Log manager providing logging capabilities 
	 */
	public FileUploadController(UploadService uploadService,
			RequestManager requestManager, JSONManager jsonManager,
			LogManager logger) {
		super();
		
		if(uploadService == null || requestManager == null || jsonManager == null
				|| logger == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.uploadService = uploadService;
		this.requestManager = requestManager;
		this.jsonManager = jsonManager;
		this.logger = logger;
	}


	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	logger.info("####GET#####"+request.getRequestURI()+"?"+request.getParameterMap()+"#########"+this.getClass().getName());

    	User user = null;
    	
    	//get current visit from HTTP session
		Visit visit = requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class);

		//get user from visit
		if(visit != null) user = visit.getUser();
    	
		//if user is not logged in
    	if(user == null){
    		//Error, user must be logged in to list, download or remove files
    		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    	}
    	else{
    		//User is logged in    		

    		// Get file to download
    		int downloadIndex = requestManager.getNumericParameter(request, Parameter.UPLOAD_DOWNLOAD_INDEX); 
    		
    		//User wants to download a previously uploaded file from the temporary repository
    		if(downloadIndex >= 0){
    			// Get the file of index "f" from the repository
    			FileMetadata tempFile = uploadService.getTemporaryFile(user, downloadIndex);

    			//download file
    			requestManager.downloadFile(response, tempFile.getFileType(), tempFile.getFileName(), tempFile.getContent());
    		}
    		else{
    			//Check the view is asking for the list of files at the temporary repository for this user
    			String list = requestManager.getParameter(request, Parameter.UPLOAD_LIST);
    			
    			//User wants the list of files
    			if (list !=null ){
    				//response in JSON
    				response.setContentType("application/json");
        	 
    				//convert the list of files metadata to JSON
    				String outJSON = jsonManager.object2JSON(uploadService.getTemporaryFiles(user));

    				//write JSON response
    				response.getOutputStream().write(outJSON.getBytes("UTF-8"));             
    			}
    			else{
    				//Get file to remove
    				int removeIndex = requestManager.getNumericParameter(request, Parameter.UPLOAD_REMOVE_INDEX);
    				
    				//User wants to remove a file from the temporary repository
    				if(removeIndex >=0){
    					//remove file
    					uploadService.removeTemporaryFile(user, removeIndex);
    					
    					//response in JSON the list of remaining files
    					response.setContentType("application/json");        	
    					String outJSON = jsonManager.object2JSON(uploadService.getTemporaryFiles(user));
    					response.getOutputStream().write(outJSON.getBytes("UTF-8"));
    				}
    				else{
    					//Bad params
    					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    				}    		
    			}
    		}
    	}   
    	logger.info("####END#####"+request.getRequestURI()+"?"+request.getParameterMap()+"#########"+this.getClass().getName());
    }
	
    @Override  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("####POST#####"+request.getRequestURI()+"?"+request.getParameterMap()+"#########"+this.getClass().getName());

		User user = null;
		
		//get current visit from HTTP session
		Visit visit = requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class);
		//get user from visit
		if(visit!=null) user = visit.getUser();
    	
    	if(user == null){
    		//Error, user must be logged in to upload files
    		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    	}
    	else{
    		//add uploaded file to the user temporary repository
    		uploadService.addTemporaryFiles(user, getUploadFiles(request));
    		
    		//get list of files 
    		List<FileMetadata> files = uploadService.getTemporaryFiles(user);
				        	 
    		//response in JSON the list of files for this user
	        response.setContentType("application/json");	 
	        String outJSON = jsonManager.object2JSON(files);
	        response.getOutputStream().write(outJSON.getBytes("UTF-8"));
    	} 
    	logger.info("####END#####"+request.getRequestURI()+"?"+request.getParameterMap()+"#########"+this.getClass().getName());
	}
	
    
	/**
	 * Get the multipart data in a POST and extracts the list of files' metadata
	 * @param request Request
	 * @return List of files metadata
	 */
	private List<FileMetadata> getUploadFiles(HttpServletRequest request) {
		List<FileMetadata> files = new LinkedList<FileMetadata>();		
	    Collection<Part> parts;
	    
		try {
			//get parts
			parts = request.getParts();
			
			FileMetadata temp = null;
			
			//for each part
			for(Part part:parts){				
				if(part.getContentType() != null){
					//nerw file metadata
					temp = new FileMetadata();
					temp.setFileName(requestManager.getPartFilename(part));
					temp.setFileSize(String.valueOf(part.getSize()));
					temp.setFileType(part.getContentType());
					temp.setContent(part.getInputStream());
					
					//get media type from part content-type
					MediaType mtype = uploadService.getMediaType(part.getContentType());
					temp.setMediaType(mtype);
					
					if(mtype != null){
						//add file metadata
						logger.debug("Upload.Part: "+temp);
						files.add(temp);
					}
				}
			}
		 
		} catch (Throwable t) {			
			logger.error(t, "Error uploading files.");
		}	 
		return files;
	}
	
}
