package com.julvez.pfc.teachonsnap.manager.json.jackson;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.julvez.pfc.teachonsnap.manager.json.JSONManager;
import com.julvez.pfc.teachonsnap.manager.json.JSONViews;

public class JSONManagerJackson implements JSONManager {


	@Override
	public <T> T JSON2Object(String json, Class<T> clazz) {
		T obj = null;
		
		try{
			ObjectMapper mapper = new ObjectMapper();
			obj = mapper.readValue(json, clazz);
		}
		catch(Throwable t){
			System.out.println("JSONManager: " + obj);
			t.printStackTrace();
			obj = null;
		}
		return obj;
	}

	@Override
	public String object2JSON(Object o) {
		String json = null;
		try{
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writeValueAsString(o);
		}
		catch(Throwable t){
			System.out.println("JSONManager: " + json);
			t.printStackTrace();
			json = null;
		}
		return json;
	}

	@Override
	public <T> T JSON2SimpleObject(String json, Class<T> clazz) {
		T obj = null;
		
		try{
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
			obj = mapper.readerWithView(JSONViews.Simple.class)
                    .withType(clazz)
                    .readValue(json);					
		}
		catch(Throwable t){
			System.out.println("JSONManager: " + obj);
			t.printStackTrace();
			obj = null;
		}
		return obj;
	}

	@Override
	public String object2SimpleJSON(Object o) {
		String json = null;
		try{
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
			
			json = mapper.writerWithView(JSONViews.Simple.class)
		            .writeValueAsString(o);
		}
		catch(Throwable t){
			System.out.println("JSONManager: " + json);
			t.printStackTrace();
			json = null;
		}
		return json;
	}

}
