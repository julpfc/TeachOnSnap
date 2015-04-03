package com.julvez.pfc.teachonsnap.service.visit.impl;

import com.julvez.pfc.teachonsnap.model.visit.Visit;
import com.julvez.pfc.teachonsnap.repository.visit.VisitRepository;
import com.julvez.pfc.teachonsnap.repository.visit.VisitRepositoryFactory;
import com.julvez.pfc.teachonsnap.service.visit.VisitService;

public class VisitServiceImpl implements VisitService {

	private static final String IP_NULL = "0.0.0.0";
	
	private VisitRepository visitRepository = VisitRepositoryFactory.getRepository();

	@Override
	public Visit createVisit(String ip) {
		Visit visit = null;
		
		if(ip==null){
			ip = IP_NULL;
		}
		
		int idVisit = visitRepository.createVisit(ip);
		
		if(idVisit>0){
			visit = new Visit(idVisit);
		}
		
		return visit;
	}

	@Override
	public Visit saveUser(Visit visit) {
		if(visit!=null && visit.getUser()!=null){
			if(!visitRepository.saveUser(visit.getId(), visit.getUser().getId())){
				visit = null;
			}			
		}
		return visit;
	}

}
