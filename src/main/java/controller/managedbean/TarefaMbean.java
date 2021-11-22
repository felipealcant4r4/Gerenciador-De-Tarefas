package controller.managedbean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import exception.ErroAoApagarException;
import exception.ErroAoAtualizarException;
import exception.ErroAoSalvarException;
import exception.RegistroNaoEncontradoException;
import model.dao.GenericDao;
import model.dao.NivelPrioridadeDao;
import model.dao.TarefaDao;
import model.dao.UsuarioDao;
import model.entity.NivelPrioridade;
import model.entity.Tarefa;
import model.entity.Usuario;

@ManagedBean
@SessionScoped
public class TarefaMbean {

	private Tarefa tarefa;

	SimpleDateFormat sdf;

	private String deadlineString;

	private List<String> mensagensErro;
	
	private List<Tarefa> tarefasFiltradas;
	
	private boolean buscaExcutada = false;

	public TarefaMbean() {
		tarefa = new Tarefa();
		tarefa.setUsuario(new Usuario());
		tarefa.setNivelPrioridade(new NivelPrioridade());
		mensagensErro = new ArrayList<String>();
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		deadlineString = null;
		tarefasFiltradas = new ArrayList<Tarefa>();
	}

	public void cadastrar() {
		GenericDao<Tarefa> tarefaDao = new TarefaDao();
		GenericDao<NivelPrioridade> nivelPrioridadeDao = new NivelPrioridadeDao();
		GenericDao<Usuario> usuarioDao = new UsuarioDao();
		
		if (mensagensErro.size() > 0) {
			mensagensErro = new ArrayList<String>();
		}

		Usuario usuario = usuarioDao.findById(tarefa.getUsuario().getId());

		if (usuario == null) {
			mensagensErro.add("A tarefa deve ser atribuida a um responsável");
		}
		
		if (tarefa.getDescricao() == null || tarefa.getDescricao().length() <= 0) {
			mensagensErro.add("A tarefa deve possuir uma descrição");
		}

		if (deadlineString != null && deadlineString.length() > 0) {
			try {
				tarefa.setDeadline(sdf.parse(deadlineString));
			} catch (ParseException e) {
				mensagensErro.add("Formato de data inválido");
			}
		}

		if (!(mensagensErro.size() > 0)) {
			NivelPrioridade nivelPrioridade = nivelPrioridadeDao
					.findById(Long.valueOf(tarefa.getNivelPrioridade().getId()));

			tarefa.setDataCadastro(new Date());
			tarefa.setNivelPrioridade(nivelPrioridade);
			tarefa.setUsuario(usuario);
			tarefa.setEmAndamento(true);

			try {
				tarefaDao.create(tarefa);
			} catch (ErroAoSalvarException e) {
				mensagensErro.add("Erro ao cadastrar, verifique os dados inseridos");
			} finally {
				tarefa = new Tarefa();
				tarefa.setUsuario(new Usuario());
				tarefa.setNivelPrioridade(new NivelPrioridade());
				deadlineString = null;
			}
		}

	}

	public String atualizar(long id) {
		GenericDao<Tarefa> tarefaDao = new TarefaDao();
		GenericDao<NivelPrioridade> nivelPrioridadeDao = new NivelPrioridadeDao();
		GenericDao<Usuario> usuarioDao = new UsuarioDao();
		
		if (mensagensErro.size() > 0) {
			mensagensErro = new ArrayList<String>();
		}
		
		Usuario usuario = usuarioDao.findById(tarefa.getUsuario().getId());

		if (usuario == null) {
			mensagensErro.add("A tarefa deve ser atribuida a um responsável");
		}

		if (tarefa.getDescricao() == null || tarefa.getDescricao().length() <= 0) {
			mensagensErro.add("A tarefa deve possuir uma descrição");
		}
		
		try {
			Tarefa tarefa = tarefaDao.findById(id);
			if (tarefa == null) {
				mensagensErro.add("Tarefa não encontrada");
			}
		} catch (Exception e) {
			mensagensErro.add("Tarefa não encontrada");
		}

		

		if (deadlineString != null && deadlineString.length() > 0) {
			try {
				tarefa.setDeadline(sdf.parse(deadlineString));
			} catch (ParseException e) {
				mensagensErro.add("Formato de data inválido");
			}
		}
		
		if(!(mensagensErro.size()>0)) {
			NivelPrioridade nivelPrioridade = nivelPrioridadeDao
					.findById(Long.valueOf(tarefa.getNivelPrioridade().getId()));
			
			tarefa.setDataModicicacao(new Date());
			tarefa.setUsuario(usuario);
			tarefa.setNivelPrioridade(nivelPrioridade);
			
			try {
				tarefaDao.update(id, tarefa);
			} catch (ErroAoAtualizarException e) {
				mensagensErro.add("Erro ao atualizar, revise os dados");
			} catch (RegistroNaoEncontradoException e) {
				mensagensErro.add("Tarefa não encontrada");
			} finally {
				tarefa = new Tarefa();
				tarefa.setUsuario(new Usuario());
				tarefa.setNivelPrioridade(new NivelPrioridade());
				deadlineString = null;
			}
			return "listar-tarefas";
		}
		return null;
	}

	public String editar(long id) {
		GenericDao<Tarefa> tarefaDao = new TarefaDao();
		
		if (mensagensErro.size() > 0) {
			mensagensErro = new ArrayList<String>();
		}

		if (tarefaDao.findById(id) == null) {
			return null;
		}

		Tarefa tarefaAtual = tarefaDao.findById(id);
		
		tarefa.setId(tarefaAtual.getId());
		tarefa.setDataCadastro(tarefaAtual.getDataCadastro());
		tarefa.setDataModicicacao(tarefaAtual.getDataModicicacao());
		tarefa.setDeadline(tarefaAtual.getDeadline());
		tarefa.setDescricao(tarefaAtual.getDescricao());
		tarefa.setNivelPrioridade(tarefaAtual.getNivelPrioridade());
		tarefa.setTitulo(tarefaAtual.getTitulo());
		tarefa.setUsuario(tarefaAtual.getUsuario());
				
		if (tarefa.getDeadline() != null) {
			deadlineString = sdf.format(tarefa.getDeadline());
		}

		return "editar-tarefa";
	}

	public void apagar(long id) {
		GenericDao<Tarefa> tarefaDao = new TarefaDao();
		if (mensagensErro.size() > 0) {
			mensagensErro = new ArrayList<String>();
		}

		try {
			tarefaDao.delete(id);
		} catch (ErroAoApagarException e) {
			mensagensErro.add("Ocorreu um erro ao apagar");
		} catch (RegistroNaoEncontradoException e) {
			mensagensErro.add("Tarefa não encontrada");
		}
	}

	public void filtrarTarefas(){
		if(mensagensErro.size() > 0) {
			mensagensErro = new ArrayList<String>();
		}
		
		TarefaDao tarefaDao = new TarefaDao();
		
		try {
			tarefasFiltradas = (List<Tarefa>) tarefaDao.filter(tarefa.getId(), tarefa.getTitulo(), tarefa.getUsuario().getId(), tarefa.getEmAndamento());
			
		} catch (RegistroNaoEncontradoException e) {
			mensagensErro.add("Nenhum registro encontrado");
		} catch (NullPointerException e ) {
			e.printStackTrace();
		}
	}
	
	public void defineStatus(long id) {
		GenericDao<Tarefa> tarefaDao = new TarefaDao();
		
		if(tarefaDao.findById(id) !=  null) {
			Tarefa tarefa = new Tarefa();
			tarefa.setEmAndamento(false);
			
			try {
				tarefaDao.update(id, tarefa);
			} catch (ErroAoAtualizarException e) {
				mensagensErro.add("Erro ao atualizar, revise os dados");
			} catch (RegistroNaoEncontradoException e) {
				mensagensErro.add("Tarefa não encontrada");
			} finally {
				tarefa = new Tarefa();
				tarefa.setUsuario(new Usuario());
				tarefa.setNivelPrioridade(new NivelPrioridade());
				deadlineString = null;
			}
		}
		
	}
	
	public List<Tarefa> getTarefasFiltradas(){
		return tarefasFiltradas;
	}
	
	public List<Tarefa> getListaTarefas() {
		GenericDao<Tarefa> tarefaDao = new TarefaDao();
		return tarefaDao.findAll();
	}
	
	public List<String> getMensagensErro() {
		return mensagensErro;
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public String getDeadlineString() {
		return deadlineString;
	}

	public void setDeadlineString(String deadlineString) {
		this.deadlineString = deadlineString;
	}

}
