package org.remo;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Factory che si comporta come un singleton.
 * 
 * 
 * @author remo
 *
 */
public class JPAUtil {
	  private static final String PERSISTENCE_UNIT_NAME = "parcheggio";
	  private static EntityManagerFactory factory;

	  private JPAUtil() {}
	
	  public static EntityManagerFactory getEntityManagerFactory() {
	    if (factory == null) {
	    	factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	    }
	    return factory;
	  }


	  public static void shutdown() {
	    if (factory != null) {
	      factory.close();
	    }
	  }
}