package com.julvez.pfc.teachonsnap.ut.manager.db.hibernate;

import static org.mockito.Mockito.when;

import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.hibernate.DBManagerHibernate;
import com.julvez.pfc.teachonsnap.manager.db.hibernate.DBPropertyName;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.ut.manager.db.DBManagerTest;

public class DBManagerHibernateTest extends DBManagerTest {

	@Mock
	private LogManager logger;
	
	@Mock
	private PropertyManager properties;
	
	@Override
	protected DBManager getManager() {		
		return new DBManagerHibernate(logger, properties);
	}

	@Override
	public void setUp() {		
		when(properties.getPasswordProperty(DBPropertyName.HIBERNATE_DB_PASSWORD)).thenReturn("sa");
		super.setUp();		
	}	
}
