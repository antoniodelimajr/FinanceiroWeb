package br.com.sisnema.financeiroweb.dao;

import java.util.List;

import br.com.sisnema.financeiroweb.util.DAOException;

public interface IDAO<T> {
	
	void salvar(T pojo) throws DAOException;
	void excluir(T pojo) throws DAOException;
	
	T obterPorId(T filtro) throws DAOException;
	List<T> pesquisar(T filtros) throws DAOException;
	

}
