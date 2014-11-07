package com.julvez.pfc.teachonsnap.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;

public class TeachOnSnapServletContextListener implements
		ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		DBManagerFactory.getDBManager().close();		
	}

}
