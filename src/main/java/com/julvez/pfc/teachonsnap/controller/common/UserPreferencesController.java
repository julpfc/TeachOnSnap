package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;

public class UserPreferencesController extends CommonController {

	private static final long serialVersionUID = -6482638863947050167L;

	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getMethod().equals("POST")){
			
		}
		else{
			request.getRequestDispatcher("/WEB-INF/views/preferences.jsp").forward(request, response);
		}

	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}

}
