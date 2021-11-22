package dao;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import model.dao.GenericDao;
import model.dao.UsuarioDao;
import model.entity.Usuario;

public class UsuarioDaoTest {
	GenericDao<Usuario> usuarioDao = new UsuarioDao();

//	@Test
	public void testCreate() {
		String pattern = "dd/MM/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		Date date = null;
		
		try {
			date = simpleDateFormat.parse("02/04/1990");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Usuario usuario = new Usuario();
		
		usuario.setCpf("99999999999");
		usuario.setDataNascimento(date);
		usuario.setNome("Fulano");
		usuario.setSobrenome("Cicrano");
		
		usuario = usuarioDao.create(usuario);	
		
		System.out.println(usuario);
	}

//	@Test
	public void testFindAll() {
		List<Usuario> usuarios = usuarioDao.findAll();
		
		System.out.println("\n\nResultado:");
		for (Usuario usuario : usuarios) {
			System.out.println(usuario);
		}
	}
	
	@Test
	public void testUpdate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date data = null;
		try {
			data = sdf.parse("29/06/2002");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		usuarioDao.update(1l, new  Usuario(null, "Pedro", "Teixeira", "88888888888", data));

	}
	
//	@Test
	public void testDelete() {
		usuarioDao.delete(2L);
	}
	
//	@Test
	public void testFindById() {
		System.out.println(usuarioDao.findById(1L));
	}
}
