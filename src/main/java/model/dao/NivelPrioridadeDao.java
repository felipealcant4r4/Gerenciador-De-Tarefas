package model.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import exception.ErroAoApagarException;
import exception.ErroAoAtualizarException;
import exception.ErroAoSalvarException;
import exception.RegistroNaoEncontradoException;
import model.db.DbUtil;
import model.entity.NivelPrioridade;

public class NivelPrioridadeDao implements GenericDao<NivelPrioridade> {

	EntityManager entityManager = DbUtil.getEntityManagerFactory().createEntityManager();
	
	@Override
	public NivelPrioridade create(NivelPrioridade nivelPrioridade) {
		
		try {
			entityManager.getTransaction().begin();
			nivelPrioridade = (NivelPrioridade) entityManager.merge(nivelPrioridade);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			throw new ErroAoSalvarException();
		}
		return nivelPrioridade;
	}

	@Override
	public List<NivelPrioridade> findAll() {
		return entityManager.createQuery("from NivelPrioridade", NivelPrioridade.class).getResultList();
	}

	@Override
	public NivelPrioridade update(Long id, NivelPrioridade nivelPrioridade) {
		NivelPrioridade nivelPrioridadeAtual = findById(id);
		
		if(nivelPrioridadeAtual == null) {
			throw new RegistroNaoEncontradoException();
		}
		
		if(nivelPrioridade.getDescricao() == null) {
			nivelPrioridadeAtual.setDescricao(nivelPrioridade.getDescricao());
		} 
		
		
		try {
			entityManager.getTransaction().begin();
			nivelPrioridade = (NivelPrioridade) entityManager.merge(nivelPrioridadeAtual);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			throw new ErroAoAtualizarException();
			
		} 
		
		return nivelPrioridade;
	}

	@Override
	public void delete(Long id) {
		NivelPrioridade nivelPrioridade = new NivelPrioridade();
		nivelPrioridade.setId(id.intValue());
		
		
		try {
			entityManager.getTransaction().begin();	
			if(!entityManager.contains(nivelPrioridade))
				nivelPrioridade = entityManager.merge(nivelPrioridade);
			entityManager.remove(nivelPrioridade);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			throw new ErroAoApagarException();
		} ;
	}

	@Override
	public NivelPrioridade findById(Long id) {
		NivelPrioridade nivelPrioridade =  entityManager.find(NivelPrioridade.class, id.intValue());
		if(nivelPrioridade == null) {
			throw new RegistroNaoEncontradoException();
		} 
		return nivelPrioridade;
	}
	
}
