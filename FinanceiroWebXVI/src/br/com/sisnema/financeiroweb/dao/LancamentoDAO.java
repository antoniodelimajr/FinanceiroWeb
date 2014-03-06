package br.com.sisnema.financeiroweb.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.sisnema.financeiroweb.model.Conta;
import br.com.sisnema.financeiroweb.model.Lancamento;
import br.com.sisnema.financeiroweb.util.DAOException;

public class LancamentoDAO extends DAO<Lancamento> {

	@Override
	public void salvar(Lancamento pojo) throws DAOException {
		sessao.saveOrUpdate(pojo);
	}

	@Override
	public void excluir(Lancamento pojo) throws DAOException {
		sessao.delete(pojo);
	}

	@Override
	public Lancamento obterPorId(Lancamento filtro) throws DAOException {
		return (Lancamento) sessao.get(Lancamento.class, filtro.getCodigo());
	}

	@Override
	public List<Lancamento> pesquisar(Lancamento filtros) throws DAOException {
		return null;
	}
	
	public List<Lancamento> pesquisar(Conta conta, Date dataInicio, Date dataFim) {
		Criteria criteria = sessao.createCriteria(Lancamento.class);

		if (dataInicio != null && dataFim != null) {
			criteria.add(Restrictions.between("data", dataInicio, dataFim));
			
		} else if (dataInicio != null) {
			criteria.add(Restrictions.ge("data", dataInicio));
			
		} else if (dataFim != null) {
			criteria.add(Restrictions.le("data", dataFim));
		}

		criteria.add(Restrictions.eq("conta", conta));
		criteria.addOrder(Order.asc("data"));
		return criteria.list();
	}

	public float saldo(Conta conta, Date data) {

		if (data == null) {
			throw new IllegalArgumentException("[Financeiro] data cannot be null");
		}

		StringBuffer sql = new StringBuffer();
		sql.append("select sum(l.valor * c.fator)");
		sql.append("  from LANCAMENTO l,");
		sql.append("	     CATEGORIA c");
		sql.append(" where l.categoria = c.codigo");
		sql.append("   and l.conta = :conta");
		sql.append("   and l.data <= :data");

		SQLQuery query = sessao.createSQLQuery(sql.toString());

		query.setParameter("conta", conta.getCodigo());
		query.setParameter("data", data);

		BigDecimal saldo = (BigDecimal) query.uniqueResult();

		if (saldo != null) {
			return saldo.floatValue();
		}
		return 0f;
	}
}
