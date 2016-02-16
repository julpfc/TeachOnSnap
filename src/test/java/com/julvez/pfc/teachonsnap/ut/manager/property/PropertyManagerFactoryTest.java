package com.julvez.pfc.teachonsnap.ut.manager.property;

import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.ut.manager.ManagerFactoryTest;

public class PropertyManagerFactoryTest extends ManagerFactoryTest<PropertyManager> {

	@Override
	protected PropertyManager getTestManager() {	
		return PropertyManagerFactory.getManager();
	}
}
