package com.julvez.pfc.teachonsnap.ut.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.controller.FileUploadController;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.controller.model.SessionAttribute;
import com.julvez.pfc.teachonsnap.manager.json.JSONManager;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.upload.UploadService;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;
import com.julvez.pfc.teachonsnap.user.model.User;

public class FileUploadControllerTest extends ControllerTest<FileUploadController> {

	@Mock
	private UploadService uploadService;
	
	@Mock
	private JSONManager jsonManager;
	
	private int index = 0;
	private int invalidIndex = -1;
	
	@Override
	protected FileUploadController getController() {
		return new FileUploadController(uploadService, requestManager, jsonManager, logger);
	}
	
	@Test
	public void testDoGetHttpServletRequestHttpServletResponse() {
		when(request.getMethod()).thenReturn("GET");
		
		try {
			test.service(request, response);
			verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		User user = mock(User.class);
				
		Visit visit = mock(Visit.class);
		when(visit.getUser()).thenReturn(user);
		
		when(requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class)).thenReturn(visit);
		
		when(requestManager.getNumericParameter(request, Parameter.UPLOAD_DOWNLOAD_INDEX)).thenReturn(index);
		FileMetadata file = mock(FileMetadata.class);
		when(file.getFileType()).thenReturn(EMPTY_STRING);
		when(file.getFileName()).thenReturn(EMPTY_STRING);

		when(uploadService.getTemporaryFile(user, index)).thenReturn(file);
		
		try {			
			test.service(request, response);
			verify(requestManager).downloadFile(eq(response), anyString(), anyString(), any(InputStream.class));			
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		when(requestManager.getNumericParameter(request, Parameter.UPLOAD_DOWNLOAD_INDEX)).thenReturn(invalidIndex);
		
		when(requestManager.getNumericParameter(request, Parameter.UPLOAD_REMOVE_INDEX)).thenReturn(invalidIndex);
		try {			
			test.service(request, response);
			verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);						
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		when(requestManager.getNumericParameter(request, Parameter.UPLOAD_REMOVE_INDEX)).thenReturn(index);
		try {			
			ServletOutputStream out = mock(ServletOutputStream.class);
			when(response.getOutputStream()).thenReturn(out);
			when(jsonManager.object2JSON(anyObject())).thenReturn(EMPTY_STRING);
			test.service(request, response);
			verify(response).setContentType("application/json");
			verify(out).write(any(byte[].class));
			verify(uploadService).removeTemporaryFile(user, index);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		when(requestManager.getParameter(request, Parameter.UPLOAD_LIST)).thenReturn(EMPTY_STRING);
		try {			
			ServletOutputStream out = mock(ServletOutputStream.class);
			when(response.getOutputStream()).thenReturn(out);
			when(jsonManager.object2JSON(anyObject())).thenReturn(EMPTY_STRING);
			test.service(request, response);
			verify(response, times(2)).setContentType("application/json");
			verify(out).write(any(byte[].class));
			verify(uploadService, times(2)).getTemporaryFiles(user);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Test
	public void testDoPostHttpServletRequestHttpServletResponse() {
		when(request.getMethod()).thenReturn("POST");
		
		try {
			test.service(request, response);
			verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		User user = mock(User.class);
		when(user.isAuthor()).thenReturn(true);
		
		Visit visit = mock(Visit.class);
		when(visit.getUser()).thenReturn(user);
		
		when(requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class)).thenReturn(visit);
		
		try {
			ServletOutputStream out = mock(ServletOutputStream.class);
			when(response.getOutputStream()).thenReturn(out);
			when(jsonManager.object2JSON(anyObject())).thenReturn(EMPTY_STRING);
			test.service(request, response);
			verify(response).setContentType("application/json");
			verify(out).write(any(byte[].class));
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
