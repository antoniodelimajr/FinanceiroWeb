package br.com.sisnema.financeiroweb.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import br.com.sisnema.financeiroweb.domain.SituacaoCheque;
import br.com.sisnema.financeiroweb.model.Categoria;
import br.com.sisnema.financeiroweb.model.Cheque;
import br.com.sisnema.financeiroweb.model.ChequeId;
import br.com.sisnema.financeiroweb.model.Conta;
import br.com.sisnema.financeiroweb.model.Lancamento;
import br.com.sisnema.financeiroweb.negocio.ChequeRN;
import br.com.sisnema.financeiroweb.negocio.LancamentoRN;
import br.com.sisnema.financeiroweb.util.ContextoUtil;
import br.com.sisnema.financeiroweb.util.RNException;

@ManagedBean(name = "lancamentoBean")
@ViewScoped
public class LancamentoBean {

	private List<Lancamento> lista;
	private List<Lancamento> listaAteHoje;
	private List<Lancamento> listaFuturos;
	private List<Double> saldos	= new ArrayList<Double>();
	private float saldoGeral;

	private Lancamento editado	= new Lancamento();
	private Integer	numeroCheque;
	
	public LancamentoBean() {
		this.novo();
	}

	public void novo() {
		this.editado = new Lancamento();
		this.editado.setData(new Date());
		this.numeroCheque = null;
	}

	public void editar() {
		Cheque cheque = this.editado.getCheque();
		if (cheque != null) {
			this.numeroCheque = cheque.getChequeId().getNumero();
		}
	}

	public void salvar() {
		try {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			this.editado.setUsuario(contextoBean.getUsuarioLogado());
			this.editado.setConta(contextoBean.getContaAtiva());
			
			if(Integer.valueOf(0).equals(editado.getCodigo())){
				editado.setCodigo(null);
			}
			
			ChequeRN chequeRN = new ChequeRN();
			ChequeId chequeId = null;
			if (this.numeroCheque != null) {
				chequeId = new ChequeId();
				chequeId.setConta(contextoBean.getContaAtiva().getCodigo());
				chequeId.setNumero(this.numeroCheque);
				Cheque cheque = chequeRN.obterPorId(new Cheque(chequeId));
				FacesContext context = FacesContext.getCurrentInstance();
				if (cheque == null) {
					FacesMessage msg = new FacesMessage("Cheque não cadastrado");
					context.addMessage(null, msg);
					return;
					
				} else if (SituacaoCheque.C.equals(cheque.getSituacao())) {
					FacesMessage msg = new FacesMessage("Cheque já cancelado");
					context.addMessage(null, msg);
					return;
					
				} else {
					this.editado.setCheque(cheque);
					chequeRN.baixarCheque(chequeId, this.editado);
				}
			}
			LancamentoRN lancamentoRN = new LancamentoRN();
			lancamentoRN.salvar(editado);
			
			this.novo();
			this.lista = null;
		} catch (RNException e) {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							e.getMessage(), ""));
		}
	}

	public void excluir() {
		try {
			
			LancamentoRN lancamentoRN = new LancamentoRN();
			this.editado = lancamentoRN.obterPorId(editado);
			lancamentoRN.excluir(this.editado);
			this.lista = null;
			
		} catch (RNException e) {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							e.getMessage(), ""));
		}
	}

	public List<Lancamento> getLista() {
		if (this.lista == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			Conta conta = contextoBean.getContaAtiva();
			
			Calendar dataSaldo = new GregorianCalendar();
			dataSaldo.add(Calendar.MONTH, -1);
			dataSaldo.add(Calendar.DAY_OF_MONTH, -1);

			Calendar inicio = new GregorianCalendar();
			inicio.add(Calendar.MONTH, -1);

			LancamentoRN lancamentoRN = new LancamentoRN();
			this.saldoGeral = lancamentoRN.saldo(conta, dataSaldo.getTime());
			this.lista = lancamentoRN.pesquisar(conta, inicio.getTime(), null);

			Categoria categoria = null;
			double saldo = this.saldoGeral;
			for (Lancamento lancamento : this.lista) {
				categoria = lancamento.getCategoria();
				saldo = saldo + (lancamento.getValor().floatValue() * categoria.getFator());
				this.saldos.add(saldo);
			}
		}
		return this.lista;
	}
	
   public List<Lancamento> getListaAteHoje() {
   	if (this.listaAteHoje == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			Conta conta = contextoBean.getContaAtiva();

			Calendar hoje = new GregorianCalendar();

			LancamentoRN lancamentoRN = new LancamentoRN();
			this.listaAteHoje = lancamentoRN.pesquisar(conta, null, hoje.getTime());
		}
		return this.listaAteHoje;
   }
   
   public List<Lancamento> getListaFuturos() {
   	if (this.listaFuturos == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			Conta conta = contextoBean.getContaAtiva();

			Calendar amanha = new GregorianCalendar();
			amanha.add(Calendar.DAY_OF_MONTH, 1);

			LancamentoRN lancamentoRN = new LancamentoRN();
			this.listaFuturos = lancamentoRN.pesquisar(conta, amanha.getTime(), null);
		}
		return this.listaFuturos;
   	}
   
	public void mudouCheque(ValueChangeEvent event) {
		Integer chequeAnterior = (Integer) event.getOldValue();
		if (chequeAnterior != null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			ChequeRN chequeRN = new ChequeRN();
			try {
				chequeRN.desvinculaLancamento(contextoBean.getContaAtiva(), chequeAnterior);
			} catch (RNException e) {
				FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(e.getMessage());
				context.addMessage(null, msg);
				return;
			}
		}
	}
	
	public float getSaldoGeral() {
		return saldoGeral;
	}

	public void setSaldoGeral(float saldo) {
		this.saldoGeral = saldo;
	}

	public void setLista(List<Lancamento> lista) {
		this.lista = lista;
	}

	public List<Double> getSaldos() {
		return saldos;
	}

	public void setSaldos(List<Double> saldos) {
		this.saldos = saldos;
	}

	public Lancamento getEditado() {
		return editado;
	}

	public void setEditado(Lancamento selecionado) {
		this.editado = selecionado;
	}

	public Integer getNumeroCheque() {
		return numeroCheque;
	}

	public void setNumeroCheque(Integer numeroCheque) {
		this.numeroCheque = numeroCheque;
	} 
}