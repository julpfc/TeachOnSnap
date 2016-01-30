package com.julvez.pfc.teachonsnap.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.request.RequestManagerFactory;
import com.julvez.pfc.teachonsnap.upload.repository.UploadRepository;
import com.julvez.pfc.teachonsnap.upload.repository.UploadRepositoryFactory;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;

/**
 * Listener for the servlet:
 * <p>
 *    * Context destruction so we can cleanup services.
 *    <p>
 *    * Request lifecycle for logging mark-up. 
 * <p>
 * @see DBManager#close()
 * @see UploadRepository#close()
 * @see LogManager#addPrefix(String)
 * @see LogManager#removePrefix()
 */
public class TeachOnSnapServletListener implements
		ServletContextListener, ServletRequestListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		DBManagerFactory.getManager().close();
		UploadRepositoryFactory.getRepository().close();
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		if(sre.getServletRequest() instanceof HttpServletRequest){
			HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
			ControllerURI URI = ControllerURI.getURIFromPath(request.getServletPath());			
			LogManagerFactory.getManager().addPrefix(
					URI == null ? request.getServletPath() : URI.toString()); //Controller name
			LogManagerFactory.getManager().addPrefix(
					RequestManagerFactory.getManager().getSessionID(request)); //sessionID			
		}
	}

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		LogManagerFactory.getManager().removePrefix();	//sessionID
		LogManagerFactory.getManager().removePrefix();	//Controller name
	}
}
