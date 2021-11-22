package model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import exception.ErroAoApagarException;
import exception.ErroAoAtualizarException;
import exception.ErroAoSalvarException;
import exception.RegistroNaoEncontradoException;
import model.db.DbUtil;
import model.entity.Tarefa;

public class TarefaDao implements GenericDao<Tarefa> {

	EntityManager entityManager = DbUtil.getEntityManagerFactory().createEntityManager();

	@Override
	public Tarefa create(Tarefa tarefa) {
		
		try {
			entityManager.getTransaction().begin();
			tarefa = (Tarefa) entityManager.merge(tarefa);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			throw new ErroAoSalvarException();
		}
		
		return tarefa;
	}

	@Override
	public List<Tarefa> findAll() {
		return entityManager.createQuery("from Tarefa", Tarefa.class).getResultList();
	}

	@Override
	public Tarefa update(Long id, Tarefa tarefa) {
		Tarefa tarefaAtual = findById(id);

		if (tarefaAtual == null) {
			throw new RegistroNaoEncontradoException();
		}

		if (tarefa.getDescricao() != null) {
			tarefaAtual.setDescricao(tarefa.getDescricao());
		}

		if (tarefa.getUsuario() != null) {
			tarefaAtual.setUsuario(tarefa.getUsuario());
		}

		if (tarefa.getDeadline() != null) {
			tarefaAtual.setDeadline(tarefa.getDeadline());
		}

		if (tarefa.getDataCadastro() != null) {
			tarefaAtual.setDataCadastro(tarefa.getDataCadastro());
		}

		if (tarefa.getDataModicicacao() != null) {
			tarefaAtual.setDataModicicacao(tarefa.getDataModicicacao());
		}

		if (tarefa.getNivelPrioridade() != null) {
			tarefaAtual.setNivelPrioridade(tarefa.getNivelPrioridade());
		}

		if (tarefa.getTitulo() != null) {
			tarefaAtual.setTitulo(tarefa.getTitulo());
		}
		
		if(tarefa.getEmAndamento() != null) {
			tarefaAtual.setEmAndamento(tarefa.getEmAndamento());
		}

		try {
			entityManager.getTransaction().begin();
			tarefa = (Tarefa) entityManager.merge(tarefaAtual);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			throw new ErroAoAtualizarException();
		}
		
		return tarefa;
	}

	@Override
	public void delete(Long id) {
		Tarefa tarefa = new Tarefa();
		tarefa.setId(id);

		

		try {
			entityManager.getTransaction().begin();
			if (!entityManager.contains(tarefa))
				tarefa = entityManager.merge(tarefa);

			entityManager.remove(tarefa);
			entityManager.getTransaction().commit();
		} catch (Exception p) {
			throw new ErroAoApagarException();
		}
		
	}

	@Override
	public Tarefa findById(Long id) {
		Tarefa tarefa = entityManager.find(Tarefa.class, id);
		if (tarefa == null) {
			throw new RegistroNaoEncontradoException();
		}
		return tarefa;
	}

	public List<Tarefa> filter(Long id, String titulo, Long idUsuario, Boolean emAndamento) {

		String hql = "from Tarefa t";

		StringBuilder restricoes = new StringBuilder();

		restricoes.append(hql);
		restricoes.append(" where ");
		if (id != null && id > 0) {
			restricoes.append(" t.id = " + id + " and ");
		}

		if (titulo != null && titulo.length() > 0) {
			restricoes.append(" (upper(t.titulo) like upper(:tituloTarefa) or upper(t.descricao) like upper(:tituloTarefa)) and ");
		}

		if (idUsuario != null &&  idUsuario > 0 ) {
			restricoes.append(" t.usuario.id = " + idUsuario + " and");
		}

		if(emAndamento != null) {
			restricoes.append(" t.emAndamento is " + emAndamento + " and ");
		}
		restricoes.append(" 1=1 ");

		Query query = entityManager.createQuery(restricoes.toString());

		if (titulo != null && titulo.length() > 0) {
			query.setParameter("tituloTarefa", "%"+titulo+"%");
		}

		try {
		return (List<Tarefa>) query.getResultList();
		} catch (RuntimeException e) {
			throw new RegistroNaoEncontradoException();
		}
	}

}
