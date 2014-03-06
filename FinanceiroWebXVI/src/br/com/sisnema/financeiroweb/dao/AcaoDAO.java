package br.com.sisnema.financeiroweb.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.com.sisnema.financeiroweb.model.Acao;
import br.com.sisnema.financeiroweb.model.Usuario;
import br.com.sisnema.financeiroweb.util.DAOException;

public class AcaoDAO extends DAO<Acao> {

	@Override
	public void salvar(Acao pojo) throws DAOException {
		sessao.saveOrUpdate(pojo);
	}

	@Override
	public void excluir(Acao pojo) throws DAOException {
		sessao.delete(pojo);
	}

	@Override
	public Acao obterPorId(Acao filtro) throws DAOException {
		return (Acao) sessao.get(Acao.class, filtro.getCodigo());
	}

	@Override
	public List<Acao> pesquisar(Acao filtros) throws DAOException {
		return null;
	}

	public List<Acao> pesquisar(Usuario usuario) throws DAOException {
		Criteria criteria = sessao.createCriteria(Acao.class);
		criteria.add(Restrictions.eq("usuario", usuario));
		return criteria.list();
	}

}
