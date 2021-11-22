package controller.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import exception.ErroAoApagarException;
import exception.ErroAoSalvarException;
import exception.RegistroNaoEncontradoException;
import model.dao.GenericDao;
import model.dao.NivelPrioridadeDao;
import model.entity.NivelPrioridade;

@ManagedBean
public class NivelPrioridadeMbean {

	private NivelPrioridade nivelPrioridade;

	private List<String> mensagensErro;

	public NivelPrioridadeMbean() {
		
		nivelPrioridade = new NivelPrioridade();
		mensagensErro = new ArrayList<String>();
	}

	public void cadastrar() {
		GenericDao<NivelPrioridade> nivelPrioridadeDao = new NivelPrioridadeDao();
		if (nivelPrioridade.getDescricao() == null) {
			mensagensErro.add("A descrição não pode estar vazia");
		}
		
		if(!(mensagensErro.size()>0)) {
			try {
				nivelPrioridadeDao.create(nivelPrioridade);
			} catch (ErroAoSalvarException e) {
				mensagensErro.add("Erro ao cadastrar, verifique os dados inseridos");
			}
		}
	}

	public void apagar(long id) {
		GenericDao<NivelPrioridade> nivelPrioridadeDao = new NivelPrioridadeDao();
		try {
			nivelPrioridadeDao.delete(id);
		} catch (ErroAoApagarException e) {
			mensagensErro.add("Esse registro não pode ser apagado pois está em uso");
		} catch (RegistroNaoEncontradoException e) {
			mensagensErro.add("Registro não encontrado");
		}
	}

	public List<NivelPrioridade> getListaNiveis() {
		GenericDao<NivelPrioridade> nivelPrioridadeDao = new NivelPrioridadeDao();
		return nivelPrioridadeDao.findAll();
	}

	public NivelPrioridade getNivelPrioridade() {
		return nivelPrioridade;
	}

	public List<String> getMensagensErro() {
		return mensagensErro;
	}

}
