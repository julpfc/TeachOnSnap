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
	
	private UploadService uploadService = UploadServiceFactory.getService();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
    	
		User user = null;
		Visit visit = requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class);
		if(visit!=null) user = visit.getUser();
    	
    	if(user == null){
    		//No hay session
    		response.setStatus(403);
    	}
    	else{
    		//TODO Controlar mejor que no venga una '/' detrás o que no sea uno de los tipos definidos (crear una enumeración)
    		MediaType contentType = MediaType.valueOf(request.getRequestURI().replaceFirst(request.getServletPath()+"/", "").toUpperCase());
    		System.out.println(request.getRequestURI()+"?"+request.getParameterMap());
    		
    		// 1. Get f from URL upload?f="?"
    		//TODO hacerlo con el requestmanager
    		String value = request.getParameter("f");
    		//TODO Cuidado con el indice outofbounds
 
    		if(value!=null){
	         // 2. Get the file of index "f" from the list "files"
	         FileMetadata tempFile = uploadService.getTemporaryFile(user,contentType,Integer.parseInt(value));
	 
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
	         }catch (IOException e) {
	                e.printStackTrace();
	         }
         }
         else if (request.getParameter("l")!=null){
        	 response.setContentType("application/json");
        	 
        	 String outJSON = jsonManager.object2JSON(uploadService.getTemporaryFiles(user,contentType));

        	 response.getOutputStream().write(outJSON.getBytes("UTF-8"));
             
             
         }
         else if (request.getParameter("r")!=null){
        	 String index = request.getParameter("r");
        	 uploadService.removeTemporaryFile(user,contentType,Integer.parseInt(index));
        	 response.setContentType("application/json");
        	
        	 String outJSON = jsonManager.object2JSON(uploadService.getTemporaryFiles(user,contentType));

        	 response.getOutputStream().write(outJSON.getBytes("UTF-8"));
         }
    		
    	}
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user = null;
		Visit visit = requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class);
		if(visit!=null) user = visit.getUser();
    	
    	if(user == null){
    		//No hay session
    		response.setStatus(403);
    	}
    	else{
    		//TODO Controlar mejor que no venga una '/' detrás o que no sea uno de los tipos definidos (crear una enumeración)
    		MediaType contentType = MediaType.valueOf(request.getRequestURI().replaceFirst(request.getServletPath()+"/", "").toUpperCase());

    		uploadService.addTemporaryFiles(user,contentType,getUploadFiles(request));
    		
    		List<FileMetadata> files = uploadService.getTemporaryFiles(user,contentType);
			
			
			//TODO Cuidado con que haya muchos files en memoria (remove?)
	        	 
	        // 2. Set response type to json
	        response.setContentType("application/json");
	 
	        String outJSON = jsonManager.object2JSON(files);

	        response.getOutputStream().write(outJSON.getBytes("UTF-8"));
    	}
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
					temp.setFileSize(part.getSize()/1024 +" Kb");
					temp.setFileType(part.getContentType());
					temp.setContent(part.getInputStream());
					temp.setMediaType(MediaType.valueOf(requestManager.getParameter(request, Parameter.LESSON_NEW_FILE_ATTACH).toUpperCase()));
					
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
	
}
