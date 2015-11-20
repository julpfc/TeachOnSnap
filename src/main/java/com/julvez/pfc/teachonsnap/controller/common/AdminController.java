package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.user.model.User;

public abstract class AdminController extends CommonController {

	private static final long serialVersionUID = -6482638863947050167L;
	
	@Override
	protected final void processController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user) throws ServletException, IOException {
		
		if(user != null && user.isAdmin()){
			processAdminController(request, response, visit, user);
		}
		else{
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}	
	}

	@Override
	protected final boolean isPrivateZone() {
		return true;
	}

	
	protected abstract void processAdminController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user) 
		throws ServletException, IOException ;
}
