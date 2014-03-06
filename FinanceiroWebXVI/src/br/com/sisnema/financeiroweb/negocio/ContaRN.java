package br.com.sisnema.financeiroweb.negocio;

import java.util.Date;
import java.util.List;

import br.com.sisnema.financeiroweb.dao.ContaDAO;
import br.com.sisnema.financeiroweb.model.Conta;
import br.com.sisnema.financeiroweb.model.Usuario;
import br.com.sisnema.financeiroweb.util.DAOException;
import br.com.sisnema.financeiroweb.util.RNException;

public class ContaRN extends RN<Conta> {

	public ContaRN() {
		super(new ContaDAO());
	}
	
	@Override
	public void salvar(Conta pojo) throws RNException {
		try {
			pojo.setDataCadastro(new Date());
			dao.salvar(pojo);
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}

	@Override
	public void excluir(Conta pojo) throws RNException {
		try {
			dao.excluir(pojo);
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}

	@Override
	public Conta obterPorId(Conta filtro) throws RNException {
		try {
			return dao.obterPorId(filtro);
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}

	@Override
	public List<Conta> pesquisar(Conta filtros) throws RNException {
		try {
			return dao.pesquisar(filtros);
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}

	public Conta buscarFavorita(Usuario user) {
		
//		ContaDAO cDao = (ContaDAO) dao;
//		cDao.buscarFavorita(user);
		
		return ((ContaDAO)dao).buscarFavorita(user);
	}
	
	public void tornarFavorita(Conta conta) throws RNException{
		Conta contaFavorita = buscarFavorita(conta.getUsuario());
		
		if(contaFavorita != null){
			contaFavorita.setFavorita(false);
			salvar(contaFavorita);
		}

		conta.setFavorita(true);
		salvar(conta);
	}
	
}
