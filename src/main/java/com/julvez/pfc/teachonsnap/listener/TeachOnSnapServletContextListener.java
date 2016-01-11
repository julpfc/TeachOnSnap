package com.julvez.pfc.teachonsnap.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.upload.repository.UploadRepository;
import com.julvez.pfc.teachonsnap.upload.repository.UploadRepositoryFactory;

/**
 * Listener for the servlet context destruction so we can cleanup services. 
 * <p>
 * @see DBManager#close()
 * @see UploadRepository#close()
 */
public class TeachOnSnapServletContextListener implements
		ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		DBManagerFactory.getDBManager().close();
		UploadRepositoryFactory.getRepository().close();
	}
}
