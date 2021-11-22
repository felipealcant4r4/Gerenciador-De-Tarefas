package model.dao;

import java.util.List;

import javax.persistence.EntityManager;

import exception.ErroAoApagarException;
import exception.ErroAoAtualizarException;
import exception.ErroAoSalvarException;
import exception.RegistroNaoEncontradoException;
import model.db.DbUtil;
import model.entity.Usuario;

public class UsuarioDao implements GenericDao<Usuario> {

	EntityManager entityManager = DbUtil.getEntityManagerFactory().createEntityManager();
	
	@Override
	public Usuario create(Usuario usuario) {
		
		try {
			entityManager.getTransaction().begin();
			usuario = (Usuario) entityManager.merge(usuario);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			throw new ErroAoSalvarException();
		}
		
		return usuario;
	}

	@Override
	public List<Usuario> findAll() {
		return entityManager.createQuery("from Usuario", Usuario.class).getResultList();
	}

	@Override
	public Usuario update(Long id, Usuario usuario) {
		Usuario usuarioAtual = findById(id);
		
		if(usuarioAtual == null) {
			throw new RegistroNaoEncontradoException();
		}
		
		if(usuario.getCpf() != null)
			usuario.setCpf(usuario.getCpf());
		
		if(usuario.getDataNascimento() != null) {
			usuarioAtual.setDataNascimento(usuario.getDataNascimento());
		}
		
		if(usuario.getNome() != null) {
			usuarioAtual.setNome(usuario.getNome());
		}
		
		if(usuario.getSobrenome() != null) {
			usuarioAtual.setSobrenome(usuario.getSobrenome());
		}
		
		
		
		try {
			entityManager.getTransaction().begin();
			usuario = (Usuario) entityManager.merge(usuarioAtual);
			entityManager.getTransaction().commit();
		} catch(Exception e) {
			throw new ErroAoAtualizarException();
		}
		
		
		
		return usuario;
	}

	@Override
	public void delete(Long id) {
		Usuario usuario = new Usuario();
		usuario.setId(id);
				
		try {
			entityManager.getTransaction().begin();

			if(!entityManager.contains(usuario))
				usuario = entityManager.merge(usuario);
			
			entityManager.remove(usuario);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			throw new ErroAoApagarException();
		}
		
	}

	@Override
	public Usuario findById(Long id) {
		Usuario usuario = entityManager.find(Usuario.class, id);
		
		if(usuario == null) {
			throw new RegistroNaoEncontradoException();
		}
		
		return usuario;
	}

}
