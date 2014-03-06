package br.com.sisnema.financeiroweb.negocio;

import java.util.List;

import br.com.sisnema.financeiroweb.util.RNException;

public interface IRN<T> {
	
	void salvar(T pojo) throws RNException;
	void excluir(T pojo) throws RNException;
	
	T obterPorId(T filtro) throws RNException;
	List<T> pesquisar(T filtros) throws RNException;
}
