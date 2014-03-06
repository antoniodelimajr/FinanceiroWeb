package br.com.sisnema.financeiroweb.negocio;

import java.util.Date;
import java.util.List;

import br.com.sisnema.financeiroweb.dao.LancamentoDAO;
import br.com.sisnema.financeiroweb.model.Conta;
import br.com.sisnema.financeiroweb.model.Lancamento;
import br.com.sisnema.financeiroweb.util.DAOException;
import br.com.sisnema.financeiroweb.util.RNException;

public class LancamentoRN extends RN<Lancamento> {
	
	public LancamentoRN() {
		super(new LancamentoDAO());
	}

	@Override
	public void salvar(Lancamento pojo) throws RNException {
		try {
			
			if(pojo.getCategoria().getPai() == null){
				throw new RNException("Não é possível criar um lancamento para"
										+ " uma categoria PAI");
			}
			
			dao.salvar(pojo);
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}

	@Override
	public void excluir(Lancamento pojo) throws RNException {
		try {
			dao.excluir(pojo);
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}

	@Override
	public Lancamento obterPorId(Lancamento filtro) throws RNException {
		try {
			return dao.obterPorId(filtro);
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}

	@Override
	public List<Lancamento> pesquisar(Lancamento filtros) throws RNException {
		try {
			return dao.pesquisar(filtros);
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}
	
	public float saldo(Conta conta, Date data) { 
		float saldoInicial = conta.getSaldoInicial();
		float saldoNaData = ((LancamentoDAO) dao).saldo(conta, data);
		return saldoInicial + saldoNaData;
	}

	public List<Lancamento> pesquisar(Conta conta, Date dataInicio, Date dataFim) { 
		return ((LancamentoDAO) dao).pesquisar(conta, dataInicio, dataFim);
	}

}
