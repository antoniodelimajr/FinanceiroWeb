package br.com.sisnema.financeiroweb.webservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import br.com.sisnema.financeiroweb.model.Conta;
import br.com.sisnema.financeiroweb.model.Lancamento;
import br.com.sisnema.financeiroweb.negocio.ContaRN;
import br.com.sisnema.financeiroweb.negocio.LancamentoRN;
import br.com.sisnema.financeiroweb.util.RNException;

@WebService
public class FinanceiroWS {

	@WebMethod
	public float saldo(@WebParam(name="conta") int conta, @WebParam(name="dataSaldo") Date data) {
		LancamentoRN lancamentoRN = new LancamentoRN();
		ContaRN contaRN = new ContaRN();

		Conta contaPesquisada;
		try {
			contaPesquisada = contaRN.obterPorId(new Conta(conta));
			Float saldo = lancamentoRN.saldo(contaPesquisada, data);
			return saldo.floatValue();
		} catch (RNException e) {
			e.printStackTrace();
		}
		return 0f;
	}

	@WebMethod
	public List<LancamentoItem> extrato(@WebParam(name="conta") int conta, @WebParam(name="de") Date de, @WebParam(name="ate") Date ate){
		LancamentoRN lancamentoRN = new LancamentoRN();
		ContaRN contaRN = new ContaRN();
		List<LancamentoItem> retorno = new ArrayList<LancamentoItem>();
		LancamentoItem lancamentoItem = null;
		List<Lancamento> listaLancamentos = new ArrayList<Lancamento>();

		Conta contaPesquisada;
		try {
			contaPesquisada = contaRN.obterPorId(new Conta(conta));
			listaLancamentos = lancamentoRN.pesquisar(contaPesquisada, de, ate);
			for (Lancamento lancamento : listaLancamentos) {
				lancamentoItem = new LancamentoItem();
				lancamentoItem.setData(lancamento.getData());
				lancamentoItem.setDescricao(lancamento.getDescricao());
				lancamentoItem.setValor(lancamento.getValor().floatValue());
				retorno.add(lancamentoItem);
			}
		} catch (RNException e) {
			e.printStackTrace();
		}
		return retorno;
	}
}
