package com.julvez.pfc.teachonsnap.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManagerFactory;
import com.julvez.pfc.teachonsnap.model.upload.ContentType;
import com.julvez.pfc.teachonsnap.model.upload.FileMetadata;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.service.upload.UploadService;
import com.julvez.pfc.teachonsnap.service.upload.UploadServiceFactory;

/**
 * Servlet implementation class FileUploadController
 */
@MultipartConfig
public class FileUploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private RequestManager requestManager = RequestManagerFactory.getManager();
	
	private UploadService uploadService = UploadServiceFactory.getService();
	
//	private static List<FileMetadata> files = new LinkedList<FileMetadata>();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
    	
    	User user = requestManager.getSessionUser(request);
    	
    	if(user == null){
    		//No hay session
    		response.setStatus(403);
    	}
    	else{
    		//TODO Controlar mejor que no venga una '/' detrás o que no sea uno de los tipos definidos (crear una enumeración)
    		ContentType contentType = ContentType.valueOf(request.getRequestURI().replaceFirst(request.getServletPath()+"/", "").toUpperCase());
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
	                 response.setContentType(tempFile.getFileType());
	 
	                 // 4. Set header Content-disposition
	                 response.setHeader("Content-disposition", "attachment; filename=\""+tempFile.getFileName()+"\"");
	 
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
        	 
             // 3. Convert List<FileMetadata> into JSON format
             ObjectMapper mapper = new ObjectMapper();
      
             // 4. Send resutl to client
             mapper.writeValue(response.getOutputStream(), uploadService.getTemporaryFiles(user,contentType));
         }
         else if (request.getParameter("r")!=null){
        	 String index = request.getParameter("r");
        	 uploadService.removeTemporaryFile(user,contentType,Integer.parseInt(index));
        	 response.setContentType("application/json");
        	 
             // 3. Convert List<FileMetadata> into JSON format
             ObjectMapper mapper = new ObjectMapper();
      
             // 4. Send resutl to client
             mapper.writeValue(response.getOutputStream(), uploadService.getTemporaryFiles(user,contentType));
         }
    		
    	}
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user = requestManager.getSessionUser(request);
    	
    	if(user == null){
    		//No hay session
    		response.setStatus(403);
    	}
    	else{
    		//TODO Controlar mejor que no venga una '/' detrás o que no sea uno de los tipos definidos (crear una enumeración)
    		ContentType contentType = ContentType.valueOf(request.getRequestURI().replaceFirst(request.getServletPath()+"/", "").toUpperCase());

    		uploadService.addTemporaryFiles(user,contentType,requestManager.getUploadFiles(request));
    		
    		List<FileMetadata> files = uploadService.getTemporaryFiles(user,contentType);
			
			
			//TODO Cuidado con que haya muchos files en memoria (remove?)
	        	 
	        // 2. Set response type to json
	        response.setContentType("application/json");
	 
	        // 3. Convert List<FileMetadata> into JSON format
	        ObjectMapper mapper = new ObjectMapper();
	 
	        // 4. Send resutl to client
	        mapper.writeValue(response.getOutputStream(), files);
    	}
	}
	
}
