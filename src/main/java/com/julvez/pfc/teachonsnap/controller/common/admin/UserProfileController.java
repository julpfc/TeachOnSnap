package com.julvez.pfc.teachonsnap.controller.common.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.common.AdminController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.user.model.User;

public class UserProfileController extends AdminController {

	private static final long serialVersionUID = -6715205860379822910L;

	private StringManager stringManager = StringManagerFactory.getManager();
	
	@Override
	protected void processAdminController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)
			throws ServletException, IOException {
		
		
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		if(params != null && params.length == 1 && stringManager.isNumeric(params[0])){
			int idUser = Integer.parseInt(params[0]);
			
			User profile = userService.getUser(idUser);
			
			if(profile != null){
				
				if(request.getMethod().equals("POST")){
					
				}

				requestManager.setAttribute(request, Attribute.USER_PROFILE, profile);
				
				request.getRequestDispatcher("/WEB-INF/views/preferences.jsp").forward(request, response);
			}
			else{
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}
		else{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}

	}
}
