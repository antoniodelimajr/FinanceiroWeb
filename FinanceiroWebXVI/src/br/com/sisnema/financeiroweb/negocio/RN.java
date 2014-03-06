package br.com.sisnema.financeiroweb.negocio;

import br.com.sisnema.financeiroweb.dao.IDAO;

public abstract class RN<T> implements IRN<T>{
	
	protected final IDAO<T> dao;

	public RN(IDAO<T> dao) {
		super();
		this.dao = dao;
	}
}
