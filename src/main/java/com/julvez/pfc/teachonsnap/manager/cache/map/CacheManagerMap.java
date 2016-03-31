package com.julvez.pfc.teachonsnap.manager.cache.map;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;

/**
 * Implementation of the CacheManager, uses internal {@link LogManager} 
 * to log the errors and {@link StringManager} to manipulate strings.
 */
public class CacheManagerMap implements CacheManager {
	
	/** Cache map where the methods results will be stored */
	private Map<String, Map<String, Object>> caches;
	
	/** String manager providing string manipulation utilities */
	private StringManager stringManager;
	
	/** Log manager providing logging capabilities */
	private LogManager logger;
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param stringManager String manager providing string manipulation utilities
	 * @param logger Log manager providing logging capabilities
	 */
	public CacheManagerMap(StringManager stringManager, LogManager logger) {
		if(stringManager == null || logger == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.stringManager = stringManager;
		this.logger = logger;
		caches = Collections.synchronizedMap(new HashMap<String, Map<String, Object>>());
	}
	
	@Override
	public Object executeImplCached(Object impl, Object... params) {
		Object result = null;
		
		if(impl != null && params != null){
			//get cache name
			String methodName = getCallerMethodName();
			
			//get parameter's classes from the parameters
			Class<?>[] paramClasses = getClasses(params);
			
			//get cache from name
			Map<String, Object> cache = getCache(methodName);
			
			//synchronize threads
			synchronized (cache) {
				//check if the result is cached
				result = cache.get(stringManager.getKey(params));
				
				logger.debug(methodName + stringManager.getKey(params) + "=" + result);
				
				//invoke the implementation method to get the result
				if(result == null ){		
					try{
						Method m = impl.getClass().getMethod(methodName, paramClasses);
						result = m.invoke(impl,params);
						
						//save result if valid
						if(result != null){
							if(isPrimitiveClass(result.getClass())){
								if(isPrimitiveValidResult(result)){
									cache.put(stringManager.getKey(params), result);
								}
							}
							else{
								cache.put(stringManager.getKey(params), result);
							}
						}
					}
					catch(Throwable t){					
						logger.error(t, "Error executing read method: " + methodName + paramClasses);
					}
				}
			}		 
		}
		return result;
	}	
	
	@Override
	public Object updateImplCached(Object impl, String[] cacheKeys, String[] cacheNames, Object... params) {
		Object result = null;
		
		if(impl != null && params != null){
			//get cache name
			String methodName = getCallerMethodName();
			
			//get parameter's classes from the parameters
			Class<?>[] paramClasses = getClasses(params);
			
			try{
				//invoke the implementation method
				Method m = impl.getClass().getMethod(methodName, paramClasses);
				result = m.invoke(impl,params);
	
				//if cache keys where specified...
				if(cacheKeys != null){
					int i=0;
					//for each cache name...
					for(String cacheName:cacheNames){
						//get cache from name
						Map<String, Object> cache = getCache(cacheName);
					
						//Synchronize the threads
						synchronized (cache) {
							//remove from cache
							Object obj = cache.remove(cacheKeys[i]);
							
							if(obj!=null) {							
								logger.debug("Cache Key Deleted: "+cacheName+"["+cacheKeys[i]+"]");
							}
							else{
								//if no cache was deleted, try with the key prefix
								List<String> keys = new ArrayList<String>(cache.keySet());
								
								if(keys != null){								
									for(String key:keys){
										if(key.startsWith(cacheKeys[i])){
											cache.remove(key);
											logger.debug("Cache Key Prefix Deleted: "+cacheName+"["+key+"*]");
										}
									}
								}
							}
						}
						i++;
					}
				}
			}
			catch(Throwable t){			
				logger.error(t, "Error executing write method: " + methodName + paramClasses);
			}		
		}
		return result;
	}

	@Override
	public void clearCache(String cacheName) {
		if(cacheName!=null){
			//get cache from name
			Map<String, Object> cache = getCache(cacheName);
			
			//synchronize threads
			synchronized (cache) {
				//clear cache
				cache.clear();
				logger.debug("Cache Deleted: "+cacheName);
			}
		}
	}
	
	@Override
	public void incCacheValue(String cacheName, String cacheKey) {
		if(!stringManager.isEmpty(cacheName) && !stringManager.isEmpty(cacheKey)){	
			//get cache from name
			Map<String, Object> cache = getCache(cacheName);
			
			//get cached value
			Object value = cache.get(cacheKey);
			
			if(value != null){
				//synchronize threads
				synchronized (cache) {
					//get again in case was updated by another thread
					value = cache.get(cacheKey);				
					
					//inc object value
					value = incObjectValue(value);
	
					//cache new value
					cache.put(cacheKey, value);
				}
			}
			logger.debug("Cache Value Incremented: "+cacheName+"["+cacheKey+"]++="+value);
		}
		
	}
	
	/**
	 * Increments the object value by 1
	 * @param result object
	 * @return modified object if success
	 */
	private Object incObjectValue(Object result) {
		if(result!=null){
			Class<?> clazz = result.getClass();
			
			if(isPrimitiveClass(clazz)){
				if(clazz.equals(Integer.class))
					return (int)result+1;				
				else if(clazz.equals(Short.class))
					return (short)result+1;
				else if(clazz.equals(Long.class))
					return (long)result+1;
				else if(clazz.equals(Byte.class))
					return (byte)result+1;
				else if(clazz.equals(Double.class))
					return (double)result+1;
				else if(clazz.equals(Float.class))
					return (float)result+1;				
			}
		}
		return result;
	}
	
	/**
	 * Checks if the result is a primitive class and is a valid result.
	 * @param result object
	 * @return true if the result is a primitive class and the result is valid
	 */
	private boolean isPrimitiveValidResult(Object result) {
		Class<?> clazz = result.getClass();
		
		boolean valid = false;
		
		if(isPrimitiveClass(clazz)){
			if(clazz.equals(Boolean.class))
				valid = true;
			else if(clazz.equals(Integer.class))
				valid = (int)result>=0;				
			else if(clazz.equals(Short.class))
				valid = (short)result>=0;
			else if(clazz.equals(Long.class))
				valid = (long)result>=0;
			else if(clazz.equals(Character.class))
				valid = (char)result>=0;
			else if(clazz.equals(Byte.class))
				valid = (byte)result>=0;
			else if(clazz.equals(Double.class))
				valid = (double)result>=0;
			else if(clazz.equals(Float.class))
				valid = (float)result>=0;			
		}
		return valid;
	}


	/**
	 * Returns the primitive class from a primitive object
	 * @param clazz Class
	 * @return the primitive class if it's a primitive object, the same class otherwise
	 */
	private Class<?> getPrimitiveClass(Class<?> clazz){
		Class<?> returnClass = clazz;
		
		if(clazz.equals(Boolean.class))
			returnClass= boolean.class;
		else if(clazz.equals(Integer.class))
			returnClass= int.class;
		else if(clazz.equals(Short.class))
			returnClass= short.class;
		else if(clazz.equals(Long.class))
			returnClass= long.class;
		else if(clazz.equals(Character.class))
			returnClass= char.class;
		else if(clazz.equals(Byte.class))
			returnClass= byte.class;
		else if(clazz.equals(Double.class))
			returnClass= double.class;
		else if(clazz.equals(Float.class))
			returnClass= float.class;
		
		return returnClass;		
	}
		
	/**
	 * Checks if a class is a primitive class
	 * @param clazz Class
	 * @return true if it's a primitive class
	 */
	private boolean isPrimitiveClass(Class<?> clazz){
		return clazz.equals(Boolean.class) || clazz.equals(Integer.class) ||
				clazz.equals(Character.class) || clazz.equals(Byte.class) ||
				clazz.equals(Short.class) || clazz.equals(Double.class) ||
				clazz.equals(Long.class) ||	clazz.equals(Float.class);
	}

	/**
	 * Returns an array of classes from the array of objects
	 * @param objs to get the class from
	 * @return array of classes from input objects
	 */
	private Class<?>[] getClasses(Object[] objs){		
		Class<?>[] classes = new Class[objs.length];
		int i = 0;
		for(Object obj:objs){
			//get the primitive class if possible
			if(isPrimitiveClass(obj.getClass())){
				classes[i++] = getPrimitiveClass(obj.getClass());			
			}
			else classes[i++] = obj.getClass();			
		}
		return classes;
	
	}
	
	/**
	 * @return method's name from the caller of the method calling this.
	 */
	private String getCallerMethodName(){
		return (new Throwable()).getStackTrace()[2].getMethodName();
	}
	
	
	/**
	 * Returns the cache from the cache name. It creates it if necessary.
	 * Synchronized.
	 * @param cacheName Name of the cache
	 * @return the cache for that name
	 */
	private Map<String, Object> getCache(String cacheName){
		//try to get the cache if exists
		Map<String, Object> cache = caches.get(cacheName);
		
		//synchronize the thread to create the new cache
		if(cache==null){
			synchronized (caches) {
				//check if another thread created the cache meanwhile
				cache = caches.get(cacheName);
				if(cache==null){
					//create cache
					caches.put(cacheName, Collections.synchronizedMap(new HashMap<String, Object>()));
					logger.debug("Cache created: "+cacheName);					
				}			
			}
			cache = caches.get(cacheName);
		}
		return cache;		
	}
}
