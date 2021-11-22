package model.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NivelPrioridade implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NivelPrioridade() {
		
	}
	
	public NivelPrioridade(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	@Id
	@Column(name = "idNivelPrioridade")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String descricao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "NivelPrioridade [id=" + id + ", descricao=" + descricao + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NivelPrioridade other = (NivelPrioridade) obj;
		return Objects.equals(id, other.id);
	}
	
}
