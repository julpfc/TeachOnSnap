package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.controller.model.SessionAttribute;
import com.julvez.pfc.teachonsnap.error.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.error.model.ErrorType;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.user.model.User;

public class UserProfileController extends CommonController {

	private static final long serialVersionUID = -6482638863947050167L;
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response, Visit visit, User user) throws ServletException, IOException {
		
		String prevPage = requestManager.getSessionAttribute(request, SessionAttribute.LAST_PAGE);
		
		if(request.getMethod().equals("POST")){
			
			String firstname = stringManager.unescapeHTML(requestManager.getParameter(request,Parameter.FIRST_NAME));
			String lastname = stringManager.unescapeHTML(requestManager.getParameter(request,Parameter.LAST_NAME));
			String oldPassword = requestManager.getParameter(request,Parameter.OLD_PASSWORD);
			String newPassword = requestManager.getParameter(request,Parameter.NEW_PASSWORD);
		
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
					if(!lastname.equals(user.getLastName())){
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
			request.getRequestDispatcher("/WEB-INF/views/userProfile.jsp").forward(request, response);
		}

	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}

}
