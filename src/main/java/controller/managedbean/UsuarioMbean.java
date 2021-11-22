package controller.managedbean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import exception.ErroAoApagarException;
import exception.ErroAoAtualizarException;
import exception.ErroAoSalvarException;
import exception.RegistroNaoEncontradoException;
import model.dao.GenericDao;
import model.dao.UsuarioDao;
import model.entity.NivelPrioridade;
import model.entity.Usuario;

@ManagedBean
@SessionScoped
public class UsuarioMbean {

	private Usuario usuario;
	
	private String dataNascimentoString;
	
	private List<String> mensagensErro;

	
	SimpleDateFormat sdf;
	
	public UsuarioMbean() {
		usuario = new Usuario();
		mensagensErro = new ArrayList<String>();
		dataNascimentoString = null;
		sdf = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	public void cadastrar() {
		GenericDao<Usuario> usuarioDao = new UsuarioDao();
		if(mensagensErro.size()>0) {
			mensagensErro = new ArrayList<String>();
		}
		
		if(usuario.getCpf() == null || usuario.getCpf().length()<=0) {
			mensagensErro.add("O CPF não pode estar vazio");
		}
		
		if(usuario.getNome() == null || usuario.getNome().length()<=0) {
			mensagensErro.add("O nome não pode estar vazio");
		}
		
		if(dataNascimentoString != null && dataNascimentoString.length() > 0) {
			try {
				usuario.setDataNascimento(sdf.parse(dataNascimentoString));
			} catch (ParseException e) {
				mensagensErro.add("Formato de data inválido");
			}
		}
		
		if(!(mensagensErro.size()>0)) {
			try {
				usuarioDao.create(usuario);
			} catch (ErroAoSalvarException e) {
				mensagensErro.add("Erro ao cadastrar, verifique os dados inseridos");
			} finally {
				usuario = new Usuario();
				dataNascimentoString = null;
			}
		}
	}

	public String editar(long id) {
		GenericDao<Usuario> usuarioDao = new UsuarioDao();
		if(mensagensErro.size()>0) {
			mensagensErro = new ArrayList<String>();
		}
		
		if(usuarioDao.findById(id) == null) {
			return null;
		}
		
		usuario = usuarioDao.findById(id);
		if(usuario.getDataNascimento() != null) {
			dataNascimentoString = sdf.format(usuario.getDataNascimento());
		}
		
		return "editar-responsavel";
	}
	
	public void atualizar(long id) {
		GenericDao<Usuario> usuarioDao = new UsuarioDao();
		if(mensagensErro.size()>0) {
			mensagensErro = new ArrayList<String>();
		}
		
		if(usuarioDao.findById(id) == null) {
			mensagensErro.add("Usuário não encontrado");
		}
		
		if(dataNascimentoString != null && dataNascimentoString.length() > 0) {
			
			try {
				usuario.setDataNascimento(sdf.parse(dataNascimentoString));
			} catch (ParseException e) {
				mensagensErro.add("Formato de data inválido");
			}
		}
		
		if(!(mensagensErro.size()>0)) {
			try {
				usuarioDao.update(id, usuario);
			} catch (ErroAoAtualizarException e) {
				mensagensErro.add("Erro ao atualizar, revise os dados");
			} catch (RegistroNaoEncontradoException e) {
				mensagensErro.add("Usuário não encontrado");
			} finally {
				usuario = new Usuario();
				dataNascimentoString = null;
			}
		}
	}
	
	
	public void apagar(long id) {
		GenericDao<Usuario> usuarioDao = new UsuarioDao();
		if(mensagensErro.size()>0) {
			mensagensErro = new ArrayList<String>();
		}
		
		try {
			usuarioDao.delete(id);
		} catch (ErroAoApagarException e) {
			mensagensErro.add("Não foi possível excluir esse usuário pois ele se encontra associado a uma tarefa");
		} catch (RegistroNaoEncontradoException e) {
			mensagensErro.add("Usuário não encontrado");
		}
	}

	public List<Usuario> getListaUsuarios() {
		GenericDao<Usuario> usuarioDao = new UsuarioDao();
		return usuarioDao.findAll();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public List<String> getMensagensErro() {
		return mensagensErro;
	}

	public String getDataNascimento() {
		return dataNascimentoString;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimentoString = dataNascimento;
	}
	
}
