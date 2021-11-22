package model.dao;

import java.util.List;

public interface GenericDao<E> {

	E create(E entidade);

	List<E> findAll(); 
	
	E update(Long id, E entidade);
	
	void delete(Long id);
	
	E findById(Long id);
	
}
