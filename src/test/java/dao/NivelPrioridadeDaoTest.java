package dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import model.dao.GenericDao;
import model.dao.NivelPrioridadeDao;
import model.entity.NivelPrioridade;

public class NivelPrioridadeDaoTest {

	GenericDao<NivelPrioridade> nivelPrioridadeDao = new NivelPrioridadeDao();

//	@Test
	public void testCreate() {
		NivelPrioridade nivelPrioridade = new NivelPrioridade();

		// baixo, médio, alto
		nivelPrioridade.setDescricao("baixo");

		nivelPrioridade = nivelPrioridadeDao.create(nivelPrioridade);

		System.out.println(nivelPrioridade);
	}

//	@Test
	public void testFindAll() {
		List<NivelPrioridade> niveis = nivelPrioridadeDao.findAll();

		System.out.println("\n\nResultado:");
		for (NivelPrioridade nivel : niveis) {
			System.out.println(nivel);
		}
	}

	@Test
	public void testUpdate() {
		nivelPrioridadeDao.update(1000000l, new  NivelPrioridade(null, "alto"));
	}

//	@Test
	public void testDelete() {
		nivelPrioridadeDao.delete(100l);
	}

//	@Test
	public void testFindById() {
		System.out.println(nivelPrioridadeDao.findById(100000L));
	}
}
