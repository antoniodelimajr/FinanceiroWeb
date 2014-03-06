package br.com.sisnema.financeiroweb.action;

import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.StreamedContent;

import br.com.sisnema.financeiroweb.model.Conta;
import br.com.sisnema.financeiroweb.negocio.ContaRN;
import br.com.sisnema.financeiroweb.util.ContextoUtil;
import br.com.sisnema.financeiroweb.util.RNException;
import br.com.sisnema.financeiroweb.util.RelatorioUtil;

@ManagedBean(name = "contaBean")
@RequestScoped
public class ContaBean {

	private Conta selecionada = new Conta();
	private List<Conta> lista = null;
	
	private StreamedContent	arquivoRetorno;
	private int				tipoRelatorio;

	public void salvar() {
		try {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			
			this.selecionada.setUsuario(contextoBean.getUsuarioLogado());
			
			if(Integer.valueOf(0).equals(this.selecionada.getCodigo())){
				this.selecionada.setCodigo(null);
			}
	
			ContaRN contaRN = new ContaRN();
			contaRN.salvar(this.selecionada);
			
			this.selecionada = new Conta();
			this.lista = null;
		} catch (RNException e) {

			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							e.getMessage(), ""));
		}
	}

	public void excluir() { //1
		try {
			ContaRN contaRN = new ContaRN();
			contaRN.excluir(selecionada);
			this.selecionada = new Conta();
			this.lista = null;
		} catch (RNException e) {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							e.getMessage(), ""));
		}
	}

	public List<Conta> getLista() { //2
		if (this.lista == null) {
			try {
				ContextoBean contextoBean = ContextoUtil.getContextoBean();
	
				ContaRN contaRN = new ContaRN();
				this.lista = contaRN.pesquisar(new Conta(contextoBean.getUsuarioLogado()));
				
			} catch (RNException e) {
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								e.getMessage(), ""));
			}
		}
		return this.lista;
	}

	public void tornarFavorita() { //3
		try {
			ContaRN contaRN = new ContaRN();
			contaRN.tornarFavorita(selecionada);

			this.selecionada = new Conta();
			
		} catch (RNException e) {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							e.getMessage(), ""));
		}
	}

	public Conta getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(Conta selecionada) {
		this.selecionada = selecionada;
	}

	public StreamedContent getArquivoRetorno() {
		FacesContext context = FacesContext.getCurrentInstance();
		ContextoBean contextoBean = ContextoUtil.getContextoBean();
		String usuario = contextoBean.getUsuarioLogado().getLogin();
		String nomeRelatorioJasper = "contas";
		String nomeRelatorioSaida = usuario + "_contas";
		RelatorioUtil relatorioUtil = new RelatorioUtil();
		HashMap parametrosRelatorio = new HashMap();
		parametrosRelatorio.put("codUsuario", contextoBean.getUsuarioLogado().getCodigo());
		parametrosRelatorio.put("nmUsuario", contextoBean.getUsuarioLogado().getNome());
		try {
			this.arquivoRetorno = relatorioUtil.geraRelatorio(parametrosRelatorio, nomeRelatorioJasper, nomeRelatorioSaida, this.tipoRelatorio);
		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		} 
		
		return arquivoRetorno;
	}

	public void setArquivoRetorno(StreamedContent arquivoRetorno) {
		this.arquivoRetorno = arquivoRetorno;
	}

	public int getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(int tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}
}
