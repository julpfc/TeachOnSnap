package com.julvez.pfc.teachonsnap.manager.db.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;

public class DBManagerHibernate implements DBManager{

	private SessionFactory sessionFactory;
    
    public DBManagerHibernate(){
    	sessionFactory = buildSessionFactory();
    }
		
	private SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            System.out.println("Hibernate Configuration loaded");
             
            //apply configuration property settings to StandardServiceRegistryBuilder
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            System.out.println("Hibernate serviceRegistry created");
             
            SessionFactory sessionFactory = configuration
                                .buildSessionFactory(serviceRegistry);
             
            return sessionFactory;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
	private SQLQuery getQuery(Session sess, String queryName, Class<?> entityClass,
			Object... queryParams){
		
		SQLQuery query;
		
		if(entityClass != null)
			query = sess.createSQLQuery(sess.getNamedQuery(queryName).getQueryString())
				.addEntity(entityClass);
		else
			query = sess.createSQLQuery(sess.getNamedQuery(queryName).getQueryString());
	
		int i=0;
		
		String queryLog = query.getQueryString();
		
		for (Object queryParam : queryParams) {
			query.setParameter(i++, queryParam);
			queryLog = queryLog.replaceFirst("\\?", queryParam.toString());
		}
		
		System.out.println("QueryLog: "+ queryLog);
				
		return query;
	}
	
	
	@Override
	public List<?> getQueryResultList(String queryName, Class<?> entityClass,
			Object... queryParams) {
		Session sess = sessionFactory.getCurrentSession();
		
		List<?> resultados = new ArrayList<>();
		
		try{
			sess.beginTransaction();
		
			SQLQuery query = getQuery(sess, queryName, entityClass, queryParams);
			
			resultados = query.list();
			
			sess.getTransaction().commit();
			
		} catch (HibernateException e) {
			System.out.println(e);
			resultados = null;
		}		
		System.out.println("QueryLog: -> "+ resultados);
		return resultados;	
	}

	@Override
	public Object getQueryResultUnique(String queryName,
			Class<?> entityClass, Object... queryParams) {
		Session sess = sessionFactory.getCurrentSession();
		
		Object resultado;
		
		try{
			sess.beginTransaction();

			SQLQuery query = getQuery(sess, queryName, entityClass, queryParams);
					
			resultado = query.uniqueResult();
			
			sess.getTransaction().commit();
					
		} catch (HibernateException e) {
			System.out.println(e);
			resultado = null;
		}		
		System.out.println("QueryLog: -> "+ resultado);
		return resultado;	
	}
}