package br.com.sisnema.financeiroweb.action;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.sisnema.financeiroweb.model.Acao;
import br.com.sisnema.financeiroweb.model.AcaoVirtual;
import br.com.sisnema.financeiroweb.negocio.AcaoRN;
import br.com.sisnema.financeiroweb.util.ContextoUtil;
import br.com.sisnema.financeiroweb.util.RNException;
import br.com.sisnema.financeiroweb.util.YahooFinanceUtil;

@ManagedBean(name = "acaoBean")
@RequestScoped
public class AcaoBean {

	private AcaoVirtual selecionada = new AcaoVirtual();
	private List<AcaoVirtual> lista = null;
	private String linkCodigoAcao	= null;

	public void salvar() {
		try {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			AcaoRN acaoRN = new AcaoRN();
			Acao acao = this.selecionada.getAcao();
			acao.setSigla(acao.getSigla().toUpperCase());
			acao.setUsuario(contextoBean.getUsuarioLogado());
			
			if(Integer.valueOf(0).equals(acao.getCodigo())){
				acao.setCodigo(null);
			}
			acaoRN.salvar(acao);
			
			this.selecionada = new AcaoVirtual();
			this.lista = null;
		} catch (RNException e) {
			FacesContext.getCurrentInstance()
			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}

	public void excluir() {
		try {
			AcaoRN acaoRN = new AcaoRN();
			acaoRN.excluir(this.selecionada.getAcao());
			
			this.selecionada = new AcaoVirtual();
			this.lista = null;
		} catch (RNException e) {
			FacesContext.getCurrentInstance()
			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}

	public List<AcaoVirtual> getLista() {
		FacesContext context = FacesContext.getCurrentInstance();
		ContextoBean contextoBean = ContextoUtil.getContextoBean();
		AcaoRN acaoRN = new AcaoRN();
		try {
			if (this.lista == null) {
				this.lista = acaoRN.listarAcaoVirtual(contextoBean.getUsuarioLogado());
			}
		} catch (RNException e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));
		}
		return this.lista;
	}

	public String getLinkCodigoAcao() {
		AcaoRN acaoRN = new AcaoRN();
		if (this.selecionada != null) {
			this.linkCodigoAcao = acaoRN.montaLinkAcao(this.selecionada.getAcao());
		} else {
			this.linkCodigoAcao = YahooFinanceUtil.INDICE_BOVESPA;
		}
		return this.linkCodigoAcao;
	}

	public AcaoVirtual getSelecionada() {
		return this.selecionada;
	}

	public void setLinkCodigoAcao(String linkCodigoAcao) {
		this.linkCodigoAcao = linkCodigoAcao;
	}

	public void setLista(List<AcaoVirtual> lista) {
		this.lista = lista;
	}

	public void setSelecionada(AcaoVirtual selecionada) {
		this.selecionada = selecionada;
	}
}
