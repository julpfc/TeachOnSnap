package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;

public class ErrorController extends CommonController {

	private static final long serialVersionUID = 5157231483305341228L;

	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//TODO En este ponerle un try/catch para queen caso de que falle vaya al view
		
		int statusCode = requestManager.getAttributeErrorStatusCode(request);
		Throwable exception = requestManager.getAttributeErrorException(request);
		
		//TODO Variables a una enum o lista de constantes
		String textKey = "error.none";
		
		if(exception!=null){
			//TODO Error de exception - En función del tipo de excepción personalizar mensaje
			// Así simplificamos para la vista, menos tipos de mensaje
			
		}
		else if(statusCode>0){
			//TODO Error de status
			//TODO esto sólo se puede hacer con los que tengamos en i18n (enum?)
			textKey = "error.status."+statusCode;
		}
		
		request.setAttribute("textKey", textKey);
		
		//TODO en el properties poner una variable para hacr que los errores sean friendly o pinten la excepción como en el log
		
		request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);

	}

	@Override
	protected boolean isPrivateZone() {		
		return false;
	}

}
