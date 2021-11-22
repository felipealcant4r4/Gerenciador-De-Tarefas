package model.db;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DbUtil {
	
	private static final String PERSISTENCE_UNIT = "MAINUNIT";
	private static EntityManagerFactory entityManagerFactory;
	
	public static EntityManagerFactory getEntityManagerFactory() {
		if(entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		}
		
		return entityManagerFactory;
	}
	
	public static void closeEntityFactory() {
		if(entityManagerFactory != null) {
			entityManagerFactory.close();
		}
	}
}
