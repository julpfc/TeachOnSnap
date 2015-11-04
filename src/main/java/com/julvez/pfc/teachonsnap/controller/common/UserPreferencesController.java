package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.manager.request.Attribute;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.error.ErrorBean;
import com.julvez.pfc.teachonsnap.model.error.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.model.error.ErrorType;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.model.visit.Visit;

public class UserPreferencesController extends CommonController {

	private static final long serialVersionUID = -6482638863947050167L;
	
	private StringManager stringManager = StringManagerFactory.getManager();

	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String prevPage = requestManager.getLastPage(request);
		
		request.setAttribute(Attribute.STRING_PREVPAGE.toString(), prevPage);
		
		if(request.getMethod().equals("POST")){
			
			String firstname = requestManager.getParamfirstName(request);
			String lastname = requestManager.getParamlastName(request);
			String oldPassword = requestManager.getParamOldPassword(request);
			String newPassword = requestManager.getParamNewPassword(request);
			
			Visit visit = requestManager.getSessionVisit(request);
			User user = visit.getUser();
			
			if(!stringManager.isEmpty(firstname) && !stringManager.isEmpty(lastname)){
				
				if(firstname.equals(user.getFirstName()) && lastname.equals(user.getLastName())){
					//No ha cambiado nada
					requestManager.setErrorSession(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.SAVE_NOCHANGES));						
				}
				else{
					//Ha cambiado
					if(!firstname.equals(user.getFirstName())){
						user = userService.saveFirstName(user, firstname);					
					}
					else{
						user = userService.saveLastName(user, lastname);
					}
					requestManager.setVisitSession(request, visit);
					requestManager.setErrorSession(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.USERNAME_SAVED));			
				}				
				response.sendRedirect(requestManager.getLastPage(request));
			}
			else if(!stringManager.isEmpty(oldPassword) && !stringManager.isEmpty(newPassword)){
				if(userService.validatePassword(user, oldPassword)){
					if(newPassword.equals(oldPassword)){
						//No ha cambiado nada
						requestManager.setErrorSession(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.SAVE_NOCHANGES));						
					}
					else{
						//Ha cambiado
						userService.savePassword(user, newPassword);
						requestManager.setErrorSession(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.PASSWORD_CHANGED));			
					}
				}
				else{
					requestManager.setErrorSession(request, new ErrorBean(ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.WRONG_CURRENT_PASSWORD));
				}
				response.sendRedirect(requestManager.getLastPage(request));
			}
			else{
				//No recibimos los parámetros correctamente
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}			
			
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
