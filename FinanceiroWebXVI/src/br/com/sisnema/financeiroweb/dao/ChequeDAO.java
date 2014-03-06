package br.com.sisnema.financeiroweb.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.com.sisnema.financeiroweb.model.Cheque;
import br.com.sisnema.financeiroweb.util.DAOException;

public class ChequeDAO extends DAO<Cheque> {

	@Override
	public void salvar(Cheque pojo) throws DAOException {
		sessao.saveOrUpdate(pojo);
	}

	@Override
	public void excluir(Cheque pojo) throws DAOException {
		sessao.delete(pojo);
	}

	@Override
	public Cheque obterPorId(Cheque filtro) throws DAOException {
		return (Cheque) sessao.get(Cheque.class, filtro.getChequeId());
	}

	@Override
	public List<Cheque> pesquisar(Cheque filtros) throws DAOException {
		Criteria criteria = sessao.createCriteria(Cheque.class);
		criteria.add(Restrictions.eq("conta", filtros.getConta()));
		
		return criteria.list();
	}

}
