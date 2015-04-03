package com.julvez.pfc.teachonsnap.service.visit;

import com.julvez.pfc.teachonsnap.model.visit.Visit;

public interface VisitService {

	public Visit createVisit(String ip);

	public Visit saveUser(Visit visit);

}
