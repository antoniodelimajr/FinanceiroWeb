package br.com.sisnema.financeiroweb.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.com.sisnema.financeiroweb.model.Conta;
import br.com.sisnema.financeiroweb.model.Usuario;
import br.com.sisnema.financeiroweb.util.DAOException;

public class ContaDAO extends DAO<Conta> {

	@Override
	public void salvar(Conta pojo) throws DAOException {
		sessao.saveOrUpdate(pojo);
	}

	@Override
	public void excluir(Conta pojo) throws DAOException {
		sessao.delete(pojo);
	}

	@Override
	public Conta obterPorId(Conta filtro) throws DAOException {
		return (Conta) sessao.get(Conta.class, filtro.getCodigo());
	}

	@Override
	public List<Conta> pesquisar(Conta filtros) throws DAOException {
		Criteria criteria = sessao.createCriteria(Conta.class);
		criteria.add(Restrictions.eq("usuario", filtros.getUsuario()));
		return criteria.list();
	}
	
	public Conta buscarFavorita(Usuario user) {
		Criteria criteria = sessao.createCriteria(Conta.class);
		
		criteria.add(Restrictions.eq("usuario", user));
		criteria.add(Restrictions.eq("favorita", true));
		
		return (Conta) criteria.uniqueResult();
	}

}













