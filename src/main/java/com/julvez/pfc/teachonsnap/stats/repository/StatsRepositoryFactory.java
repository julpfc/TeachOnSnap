package com.julvez.pfc.teachonsnap.stats.repository;


public class StatsRepositoryFactory {
	private static StatsRepository repo;
	
	public static StatsRepository getRepository(){
		if(repo==null){
			repo = new StatsRepositoryDBCache();
		}
		return repo;
	}
}
