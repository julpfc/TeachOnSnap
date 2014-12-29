package com.julvez.pfc.teachonsnap.manager.cache.map;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;

public class CacheManagerMap implements CacheManager {

	//He quitado el static, supuestamente no afecta, vigilar cachés
	private Map<String, Map<String, Object>> caches = 
			Collections.synchronizedMap(new HashMap<String, Map<String, Object>>());
	
	private StringManager stringManager = StringManagerFactory.getManager();
	
	@Override
	public Object executeImplCached(Object impl, Object... params) {
		Object result = null;
		
		String methodName = getCallerMethodName();
		
		Class<?>[] paramClasses = getClasses(params);
		
		Map<String, Object> cache = getCache(methodName);
		
		//TODO Revisar si sacamos el get la primera vez fuera y si es null ya sincronizamos
		
		synchronized (cache) {
			result = cache.get(stringManager.getKey(params));	
			System.out.println("Cache: "+methodName+stringManager.getKey(params)+"="+result);

			if(result == null ){		
				try{
					Method m = impl.getClass().getMethod(methodName, paramClasses);
					result = m.invoke(impl,params);
					cache.put(stringManager.getKey(params), result);
				}
				catch(Throwable t){
					t.printStackTrace();
				}
			}
		}
		 
		return result;
	}


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
		
	private boolean isPrimitiveClass(Class<?> clazz){
		return clazz.equals(Boolean.class) || clazz.equals(Integer.class) ||
				clazz.equals(Character.class) || clazz.equals(Byte.class) ||
				clazz.equals(Short.class) || clazz.equals(Double.class) ||
				clazz.equals(Long.class) ||	clazz.equals(Float.class);
	}

	private Class<?>[] getClasses(Object[] objs){
		
		Class<?>[] classes = new Class[objs.length];
		int i = 0;
		for(Object obj:objs){
			if(isPrimitiveClass(obj.getClass())){
				classes[i++] = getPrimitiveClass(obj.getClass());			
			}
			else classes[i++] = obj.getClass();			
		}
		return classes;
	
	}
	
	private String getCallerMethodName(){
		return (new Throwable()).getStackTrace()[2].getMethodName();
	}
	
	
	private Map<String, Object> getCache(String cacheName){
		Map<String, Object> cache = caches.get(cacheName);
		
		if(cache==null){
			synchronized (caches) {
				cache = caches.get(cacheName);
				if(cache==null){
					caches.put(cacheName, Collections.synchronizedMap(new HashMap<String, Object>()));
					System.out.println("Cache: creada "+cacheName);
				}			
			}
			cache = caches.get(cacheName);
		}
		return cache;		
	}
	
	@Override
	public Object updateImplCached(Object impl, String[] cacheKeys, String[] cacheNames, Object... params) {
		
		Object result = null;
		
		String methodName = getCallerMethodName();
		
		Class<?>[] paramClasses = getClasses(params);
		
		try{
			Method m = impl.getClass().getMethod(methodName, paramClasses);
			result = m.invoke(impl,params);

			if(cacheKeys!=null){
				int i=0;
				for(String cacheName:cacheNames){
					Map<String, Object> cache = getCache(cacheName);
				
					synchronized (cache) {
						cache.remove(cacheKeys[i]);
						System.out.println("CacheEliminada: "+cacheName+"["+cacheKeys[i]+"]");
					}
					i++;
				}
			}
		}
		catch(Throwable t){
			t.printStackTrace();
		}
		
		return result;
	}


	@Override
	public void clearCache(String cacheName) {
		if(cacheName!=null){
			
			Map<String, Object> cache = getCache(cacheName);
			
			synchronized (cache) {
				cache.clear();
				System.out.println("CacheEliminadaTotal: "+cacheName);
			}
		}
	}
	
		
}
