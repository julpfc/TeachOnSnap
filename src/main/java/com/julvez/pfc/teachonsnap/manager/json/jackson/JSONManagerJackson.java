package com.julvez.pfc.teachonsnap.manager.json.jackson;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.julvez.pfc.teachonsnap.manager.json.JSONManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;

/**
 * Implementation of the JSONManager, uses internal {@link LogManager} 
 * to log the errors.
 */
public class JSONManagerJackson implements JSONManager {

	/** Log manager providing logging capabilities */
	private LogManager logger;
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param logger Log manager providing logging capabilities
	 */
	public JSONManagerJackson(LogManager logger) {
		if(logger == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.logger = logger;
	}

	@Override
	public <T> T JSON2Object(String json, Class<T> clazz) {
		T obj = null;
		
		try{
			//get object mapper
			ObjectMapper mapper = new ObjectMapper();
			
			//read object from JSON string
			obj = mapper.readValue(json, clazz);
		}
		catch(Throwable t){
			logger.error(t, "Error mapping object: " + clazz);
			obj = null;
		}
		return obj;
	}

	@Override
	public String object2JSON(Object o) {
		String json = null;
		
		if(o != null){
			try{
				//get object mapper
				ObjectMapper mapper = new ObjectMapper();
				
				//write JSON from object
				json = mapper.writeValueAsString(o);
			}
			catch(Throwable t){
				logger.error(t, "Error getting JSON from object: " + o);
				json = null;
			}
		}
		return json;
	}

	@Override
	public <T> T JSON2SimpleObject(String json, Class<T> clazz) {
		T obj = null;
		
		try{
			//get object mapper
			ObjectMapper mapper = new ObjectMapper();
			
			//disable default view
			mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
			
			//write JSON from object using simple view
			obj = mapper.readerWithView(JSONManager.SimpleView.class)
                    .withType(clazz)
                    .readValue(json);					
		}
		catch(Throwable t){
			logger.error(t, "Error mapping object (simple view): " + clazz);
			obj = null;
		}
		return obj;
	}

	@Override
	public String object2SimpleJSON(Object o) {
		String json = null;
		
		if(o != null){
			try{
				//get object mapper
				ObjectMapper mapper = new ObjectMapper();
				
				//disable default view 
				mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
				
				//write JSON from object using Simple view
				json = mapper.writerWithView(JSONManager.SimpleView.class)
			            .writeValueAsString(o);
			}
			catch(Throwable t){
				logger.error(t, "Error getting JSON from object (simple view): " + o);
				json = null;
			}
		}
		return json;
	}

}
