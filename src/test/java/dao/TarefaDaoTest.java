package dao;


import java.util.List;

import org.junit.Test;

import model.dao.GenericDao;
import model.dao.NivelPrioridadeDao;
import model.dao.TarefaDao;
import model.dao.UsuarioDao;
import model.entity.NivelPrioridade;
import model.entity.Tarefa;
import model.entity.Usuario;

public class TarefaDaoTest {
	GenericDao<Tarefa> tarefaDao = new TarefaDao();
	
//	@Test
	public void testCreate() {
		Tarefa tarefa = new Tarefa();

		
		
		Usuario usuario = new UsuarioDao().findById(1L);
		
		NivelPrioridade nivelPrioridade = new NivelPrioridadeDao().findById(1L);
		
		tarefa.setUsuario(usuario);
		tarefa.setTitulo("Titulo da tarefa");
		tarefa.setDescricao("Comer");
		tarefa.setNivelPrioridade(nivelPrioridade);
		
		tarefa = tarefaDao.create(tarefa);	
		
		System.out.println(tarefa);
	}

//	@Test
	public void testFindAll() {
		List<Tarefa> tarefas = tarefaDao.findAll();
		
		System.out.println("\n\nResultado:");
		for (Tarefa tarefa : tarefas) {
			System.out.println(tarefa);
		}
	}
	
//	@Test
	public void testUpdate() {
		Tarefa tarefa = new Tarefa();
		tarefa.setTitulo("ABCD");
		tarefaDao.update(1l, tarefa);
	}
	
//	@Test
	public void testDelete() {
		tarefaDao.delete(1l);
	}
	
//	@Test
	public void testFindById() {
		System.out.println(tarefaDao.findAll());
	}
	
	@Test
	public void testFilter() {
		TarefaDao tarefaDao = (TarefaDao) this.tarefaDao;
		
		List<Tarefa> tarefas = tarefaDao.filter(0l, "t", 0l, true);
		
		for (Tarefa tarefa : tarefas) {
			System.out.println(tarefa);
		}
	}

}


