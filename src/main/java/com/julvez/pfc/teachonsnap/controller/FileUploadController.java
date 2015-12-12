package com.julvez.pfc.teachonsnap.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Servlet implementation class FileUploadController
 */
@MultipartConfig
public class FileUploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private RequestManager requestManager = RequestManagerFactory.getManager();
	private JSONManager jsonManager = JSONManagerFactory.getManager();
	private LogManager logger = LogManagerFactory.getManager();
	
	private UploadService uploadService = UploadServiceFactory.getService();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	logger.addPrefix(this.getClass().getSimpleName());
		User user = null;
		Visit visit = requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class);
		if(visit!=null) user = visit.getUser();
    	
    	if(user == null){
    		//No hay session
    		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    	}
    	else{
    		logger.info("####GET#####"+request.getRequestURI()+"?"+request.getParameterMap()+"#########"+this.getClass().getName());
    		    		
    		// 1. Get f from URL upload?f="?"
    		int downloadIndex = requestManager.getNumericParameter(request, Parameter.UPLOAD_DOWNLOAD_INDEX); 
    		
    		if(downloadIndex >= 0){
    			// 2. Get the file of index "f" from the list "files"
    			FileMetadata tempFile = uploadService.getTemporaryFile(user, downloadIndex);
	 
    			try {        
    				// 3. Set the response content type = file content type
    				// 4. Set header Content-disposition
    				requestManager.setFileMetadataHeaders(response, tempFile.getFileType(), tempFile.getFileName());
	 
    				// 5. Copy file inputstream to response outputstream
    				InputStream input = tempFile.getContent();
	                OutputStream output = response.getOutputStream();
	                byte[] buffer = new byte[1024*10];
	 
	                for (int length = 0; (length = input.read(buffer)) > 0;) {
	                    output.write(buffer, 0, length);
	                }
	 
	                output.close();
	                input.close();
    			}
    			catch (Throwable t) {
    				logger.error(t, "Error descargando fichero: " + tempFile);    				
    			}
    		}
    		else{
    			String list = requestManager.getParameter(request, Parameter.UPLOAD_LIST);
    			
    			if (list !=null ){
    				response.setContentType("application/json");
        	 
    				String outJSON = jsonManager.object2JSON(uploadService.getTemporaryFiles(user));

    				response.getOutputStream().write(outJSON.getBytes("UTF-8"));             
    			}
    			else{
    				int removeIndex = requestManager.getNumericParameter(request, Parameter.UPLOAD_REMOVE_INDEX);
    				
    				if(removeIndex >=0){
    					uploadService.removeTemporaryFile(user, removeIndex);
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
    	logger.removePrefix();
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.addPrefix(this.getClass().getSimpleName());
				
		User user = null;
		Visit visit = requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class);
		if(visit!=null) user = visit.getUser();
    	
    	if(user == null){
    		//No hay session
    		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    	}
    	else{
    		logger.info("####POST#####"+request.getRequestURI()+"?"+request.getParameterMap()+"#########"+this.getClass().getName());
    		uploadService.addTemporaryFiles(user, getUploadFiles(request));
    		
    		List<FileMetadata> files = uploadService.getTemporaryFiles(user);
			
			//TODO Cuidado con que haya muchos files en memoria (remove?)
	        	 
	        // 2. Set response type to json
	        response.setContentType("application/json");
	 
	        String outJSON = jsonManager.object2JSON(files);

	        response.getOutputStream().write(outJSON.getBytes("UTF-8"));
    	}
    	logger.removePrefix();
	}
	
	private List<FileMetadata> getUploadFiles(HttpServletRequest request) {
		List<FileMetadata> files = new LinkedList<FileMetadata>();
		
	    Collection<Part> parts;
		try {
			parts = request.getParts();
			
			FileMetadata temp = null;
			
			for(Part part:parts){  
				if(part.getContentType() != null){
                
					temp = new FileMetadata();
					temp.setFileName(requestManager.getPartFilename(part));
					temp.setFileSize(String.valueOf(part.getSize()));
					temp.setFileType(part.getContentType());
					temp.setContent(part.getInputStream());
					
					MediaType mtype = uploadService.getMediaType(part.getContentType());
					temp.setMediaType(mtype);
					
					if(mtype != null){						
						logger.info("Upload.Part: "+temp);
						files.add(temp);
					}
				}
			}
		 
		} catch (Throwable t) {			
			logger.error(t, "Error subiendo fichero:");
		}
	 
		return files;
	}
	
}
