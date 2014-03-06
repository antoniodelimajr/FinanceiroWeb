package br.com.sisnema.financeiroweb.negocio;

import java.util.Date;
import java.util.List;

import br.com.sisnema.financeiroweb.dao.ChequeDAO;
import br.com.sisnema.financeiroweb.domain.SituacaoCheque;
import br.com.sisnema.financeiroweb.model.Cheque;
import br.com.sisnema.financeiroweb.model.ChequeId;
import br.com.sisnema.financeiroweb.model.Conta;
import br.com.sisnema.financeiroweb.model.Lancamento;
import br.com.sisnema.financeiroweb.util.DAOException;
import br.com.sisnema.financeiroweb.util.RNException;

public class ChequeRN extends RN<Cheque> {
	
	public ChequeRN() {
		super(new ChequeDAO());
	}

	@Override
	public void salvar(Cheque cheque) throws RNException {
		try {
			dao.salvar(cheque);
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}

	@Override
	public void excluir(Cheque cheque) throws RNException {
		if (SituacaoCheque.N.equals(cheque.getSituacao())) {
			try {
				dao.excluir(cheque);
			} catch (DAOException e) {
				throw new RNException(e);
			}
		} else {
			throw new RNException("Não é possível excluir cheque, status não permitido para operação.");
		}
	}
	
	public int salvarSequencia(Conta conta, Integer chequeInicial, Integer chequeFinal) throws RNException {
		
		Cheque cheque = null;
		Cheque chequeVerifica = null;
		ChequeId chequeId = null;
		int contaTotal = 0;
		
		for (int i = chequeInicial; i <= chequeFinal; i++) {
			chequeId = new ChequeId();
			chequeId.setNumero(i);
			chequeId.setConta(conta.getCodigo().intValue());
			
			Cheque chequeAux = new Cheque();
			chequeAux.setChequeId(chequeId);
			try {
				chequeVerifica = obterPorId(chequeAux);
			} catch (RNException e) {
				throw new RNException(e);
			}
			
			if (chequeVerifica == null) {
				cheque = new Cheque();
				cheque.setChequeId(chequeId);
				cheque.setSituacao(SituacaoCheque.N);
				cheque.setDataCadastro(new Date());
				salvar(cheque);
				contaTotal++;
			}
		}
		
		return contaTotal;
	}

	@Override
	public Cheque obterPorId(Cheque cheque) throws RNException {
		try {
			return dao.obterPorId(cheque);
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}

	@Override
	public List<Cheque> pesquisar(Cheque filtros) throws RNException {
		try {
			return dao.pesquisar(filtros);
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}
	
	public void cancelarCheque(Cheque cheque) throws RNException {
		
		if (SituacaoCheque.N.equals(cheque.getSituacao())) {
			cheque.setSituacao(SituacaoCheque.C);
			try {
				dao.salvar(cheque);
			} catch (DAOException e) {
				throw new RNException(e);
			}
		} else {
			throw new RNException("Não é possível cancelar cheque, status não permitido para operação.");
		}
	}
	
	public void baixarCheque(ChequeId chequeId, Lancamento lancamento) throws RNException {
		Cheque chequeAux = new Cheque();
		chequeAux.setChequeId(chequeId);
		Cheque cheque = null;
		try {
			cheque = obterPorId(chequeAux);
		} catch (RNException e) {
			throw new RNException(e);
		}
		
		if (cheque != null) {
			cheque.setSituacao(SituacaoCheque.B);
			cheque.setLancamento(lancamento);
			try {
				dao.salvar(cheque);
			} catch (DAOException e) {
				throw new RNException(e);
			}
		}
	}
	
	public void desvinculaLancamento(Conta conta, Integer numeroCheque) throws RNException {
		ChequeId chequeId = new ChequeId();
		chequeId.setNumero(numeroCheque);
		chequeId.setConta(conta.getCodigo().intValue());
		
		Cheque chequeAux = new Cheque();
		chequeAux.setChequeId(chequeId);
		Cheque cheque = null;
		try {
			cheque = obterPorId(chequeAux);
		} catch (RNException e) {
			throw new RNException(e);
		}
		
		if (SituacaoCheque.C.equals(cheque.getSituacao())) {
			throw new RNException("Não é possível usar cheque cancelado.");
		} else {
			cheque.setSituacao(SituacaoCheque.N);
			cheque.setLancamento(null);
			try {
				dao.salvar(cheque);
			} catch (DAOException e) {
				throw new RNException(e);
			}
		}
	}

}
