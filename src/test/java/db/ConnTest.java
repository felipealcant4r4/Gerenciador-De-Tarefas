package db;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.junit.Test;

import model.db.DbUtil;
import model.entity.Tarefa;

public class ConnTest {

	@Test
	public void test() {
		EntityManager entityManager = DbUtil.getEntityManagerFactory().createEntityManager();

		
		
		Tarefa task1 = new Tarefa();
		
		task1.setTitulo("Titulo");
		
		entityManager.getTransaction().begin();
		entityManager.persist(task1);
		entityManager.getTransaction().commit();
	}

}
