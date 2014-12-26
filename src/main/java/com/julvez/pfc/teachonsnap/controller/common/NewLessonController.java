package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;

public class NewLessonController extends CommonController {

	private static final long serialVersionUID = 7608540908435958036L;
	
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/views/newLesson.jsp").forward(request, response);
	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}

}
