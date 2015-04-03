package com.julvez.pfc.teachonsnap.manager.cache.map;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
					
					if(isPrimitiveClass(result.getClass())){
						if(isPrimitiveValidResult(result)){
							cache.put(stringManager.getKey(params), result);
						}
					}
					else{
						cache.put(stringManager.getKey(params), result);
					}
				}
				catch(Throwable t){
					t.printStackTrace();
				}
			}
		}
		 
		return result;
	}


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
						Object obj = cache.remove(cacheKeys[i]);
						
						if(obj!=null) {
							System.out.println("CacheEliminada: "+cacheName+"["+cacheKeys[i]+"]");
						}
						else{
							List<String> keys = new ArrayList<String>(cache.keySet());
							
							for(String key:keys){
								if(key.startsWith(cacheKeys[i])){
									cache.remove(key);
									System.out.println("Cache2Eliminada: "+cacheName+"["+key+"]");
								}
							}
						}
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

	//TODO Investigar lo de los clones en la caché
	/*

	 private Object copy(Object source) {
		 Object obj = null;
		 
		 if(source != null){
	    	 try {
				 ByteArrayOutputStream baos = new ByteArrayOutputStream();
				 ObjectOutputStream oos = new ObjectOutputStream(baos);
				 oos.writeObject(source);
				 oos.flush();
				 oos.close();

		         ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
		         obj = ois.readObject();
			 }
			 catch(IOException e) {
				 e.printStackTrace();
				 obj = source;
			 }
			 catch(ClassNotFoundException cnfe) {
				 cnfe.printStackTrace();
				 obj = source;
			 }
	 	} 
        return obj;
	}
	*/	
}
