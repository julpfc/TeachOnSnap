package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.error.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.model.error.ErrorType;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.model.visit.Visit;
import com.julvez.pfc.teachonsnap.service.url.Attribute;
import com.julvez.pfc.teachonsnap.service.url.Parameter;
import com.julvez.pfc.teachonsnap.service.url.SessionAttribute;

public class UserPreferencesController extends CommonController {

	private static final long serialVersionUID = -6482638863947050167L;
	
	private StringManager stringManager = StringManagerFactory.getManager();

	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String prevPage = requestManager.getSessionAttribute(request, SessionAttribute.LAST_PAGE);
		
		request.setAttribute(Attribute.STRING_PREVPAGE.toString(), prevPage);
		
		if(request.getMethod().equals("POST")){
			
			String firstname = requestManager.getParameter(request,Parameter.FIRST_NAME);
			String lastname = requestManager.getParameter(request,Parameter.LAST_NAME);
			String oldPassword = requestManager.getParameter(request,Parameter.OLD_PASSWORD);
			String newPassword = requestManager.getParameter(request,Parameter.NEW_PASSWORD);
			
			Visit visit = requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class);
			User user = visit.getUser();
			
			if(!stringManager.isEmpty(firstname) && !stringManager.isEmpty(lastname)){
				
				if(firstname.equals(user.getFirstName()) && lastname.equals(user.getLastName())){
					//No ha cambiado nada
					setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.SAVE_NOCHANGES);						
				}
				else{
					//Ha cambiado
					if(!firstname.equals(user.getFirstName())){
						user = userService.saveFirstName(user, firstname);					
					}
					else{
						user = userService.saveLastName(user, lastname);
					}
					requestManager.setSessionAttribute(request, SessionAttribute.VISIT, visit);
					setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.USERNAME_SAVED);			
				}				
				response.sendRedirect(prevPage);
			}
			else if(!stringManager.isEmpty(oldPassword) && !stringManager.isEmpty(newPassword)){
				if(userService.validatePassword(user, oldPassword)){
					if(newPassword.equals(oldPassword)){
						//No ha cambiado nada
						setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.SAVE_NOCHANGES);						
					}
					else{
						//Ha cambiado
						userService.savePassword(user, newPassword);
						setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.PASSWORD_CHANGED);			
					}
				}
				else{
					setErrorSession(request, ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.WRONG_CURRENT_PASSWORD);
				}
				response.sendRedirect(prevPage);
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
