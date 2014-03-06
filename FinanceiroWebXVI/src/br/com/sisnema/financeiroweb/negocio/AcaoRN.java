package br.com.sisnema.financeiroweb.negocio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.sisnema.financeiroweb.dao.AcaoDAO;
import br.com.sisnema.financeiroweb.model.Acao;
import br.com.sisnema.financeiroweb.model.AcaoVirtual;
import br.com.sisnema.financeiroweb.model.Usuario;
import br.com.sisnema.financeiroweb.util.DAOException;
import br.com.sisnema.financeiroweb.util.RNException;
import br.com.sisnema.financeiroweb.util.YahooFinanceUtil;

public class AcaoRN extends RN<Acao> {

	public AcaoRN() {
		super(new AcaoDAO());
	}

	@Override
	public void salvar(Acao pojo) throws RNException {
		try {
			dao.salvar(pojo);
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}

	@Override
	public void excluir(Acao pojo) throws RNException {
		try {
			dao.excluir(pojo);
		} catch (DAOException e) {
			throw new RNException();
		}
	}

	@Override
	public Acao obterPorId(Acao filtro) throws RNException {
		try {
			return dao.obterPorId(filtro);
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}

	@Override
	public List<Acao> pesquisar(Acao filtros) throws RNException {
		try {
			return dao.pesquisar(filtros);
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}
	
	public List<AcaoVirtual> listarAcaoVirtual(Usuario usuario) throws RNException {
		
		List<Acao> listaAcao = null;
		List<AcaoVirtual> listaAcaoVirtual = new ArrayList<AcaoVirtual>();
		AcaoVirtual acaoVirtual = null;
		String cotacao = null;
		float ultimoPreco = 0.0f;
		float total = 0.0f;
		int quantidade = 0;

		try {
			listaAcao = ((AcaoDAO) dao).pesquisar(usuario);
			for (Acao acao : listaAcao) {
				acaoVirtual = new AcaoVirtual();
				acaoVirtual.setAcao(acao);
				cotacao = this.retornaCotacao(YahooFinanceUtil.ULTIMO_PRECO_DIA_ACAO_INDICE, acao);
				
				if (cotacao != null) {
					ultimoPreco = new Float(cotacao).floatValue();
					quantidade = acao.getQuantidade();
					total = ultimoPreco * quantidade;
					acaoVirtual.setUltimoPreco(ultimoPreco);
					acaoVirtual.setTotal(total);
					listaAcaoVirtual.add(acaoVirtual);
				}
			}
		} catch (RNException e) {
			throw new RNException("Não foi possível listar ações. Erro: "
					+ e.getMessage());
		} catch (DAOException e) {
			throw new RNException();
		}
		return listaAcaoVirtual;
	}

	public String retornaCotacao(int indiceInformacao, Acao acao) throws RNException {
		YahooFinanceUtil yahooFinanceUtil = null;
		String informacao = null;
		try {
			yahooFinanceUtil = new YahooFinanceUtil(acao);
			informacao = yahooFinanceUtil.retornaCotacao(indiceInformacao,
					acao.getSigla());
		} catch (IOException e) {
			throw new RNException("Não foi possível recuperar cotação. Erro: "
					+ e.getMessage());
		}
		return informacao;
	}

	public String montaLinkAcao(Acao acao) {
		String link = null;
		if (acao != null) {
			if (acao.getOrigem() != null) {
				if (acao.getOrigem() == YahooFinanceUtil.ORIGEM_BOVESPA) {
					link = acao.getSigla() + YahooFinanceUtil.POSFIXO_ACAO_BOVESPA;
				} else {
					link = acao.getSigla();
				}
			} else {
				link = YahooFinanceUtil.INDICE_BOVESPA;
			}
		} else {
			link = YahooFinanceUtil.INDICE_BOVESPA;
		}
		return link;
	}
}
