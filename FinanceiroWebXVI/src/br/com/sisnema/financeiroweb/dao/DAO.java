package br.com.sisnema.financeiroweb.dao;

import org.hibernate.Session;

import br.com.sisnema.financeiroweb.util.HibernateUtil;

public abstract class DAO<T> implements IDAO<T> {

	protected final Session sessao;

	public DAO() {
		super();
		this.sessao = HibernateUtil.getSessionFactory().getCurrentSession();
	}
	
}
