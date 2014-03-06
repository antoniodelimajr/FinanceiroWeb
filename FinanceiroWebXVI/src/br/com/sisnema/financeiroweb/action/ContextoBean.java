package br.com.sisnema.financeiroweb.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import br.com.sisnema.financeiroweb.model.Conta;
import br.com.sisnema.financeiroweb.model.Usuario;
import br.com.sisnema.financeiroweb.negocio.ContaRN;
import br.com.sisnema.financeiroweb.negocio.UsuarioRN;
import br.com.sisnema.financeiroweb.util.RNException;

@ManagedBean(name = "contextoBean")
@SessionScoped
public class ContextoBean {

	private Usuario		 usuarioLogado = null;
	private Conta		 contaAtiva	   = null;
	private Locale	     localizacao   = null;
	private List<Locale> idiomas;

	public Usuario getUsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String login = external.getRemoteUser();

		if (this.usuarioLogado == null || !login.equals(this.usuarioLogado.getLogin())) {

			if (login != null) {
				UsuarioRN usuarioRN = new UsuarioRN();
				this.usuarioLogado = usuarioRN.buscarPorLogin(login);
				this.contaAtiva = null;

				String[] info = this.usuarioLogado.getIdioma().split("_");
				Locale locale = new Locale(info[0], info[1]);
				context.getViewRoot().setLocale(locale);
			}
		}
		return usuarioLogado;
	}
	
	public void setUsuarioLogado(Usuario usuario) {
		this.usuarioLogado = usuario;
	}	
	

	public Conta getContaAtiva() {
		if (this.contaAtiva == null) {
			Usuario usuario = this.getUsuarioLogado();

			ContaRN contaRN = new ContaRN();
			this.contaAtiva = contaRN.buscarFavorita(usuario);

			if (this.contaAtiva == null) {
				try {
					List<Conta> contas = contaRN.pesquisar(new Conta(usuario));
					if (contas != null) {
						contaAtiva = contas.get(0);
					}
				} catch (RNException e) {
					e.printStackTrace();
				}
			}
		}
		return this.contaAtiva;
	}

	public void setContaAtiva(ValueChangeEvent event) {
		try {

			Integer codigo = (Integer) event.getNewValue();
	
			ContaRN contaRN = new ContaRN();
			contaAtiva = contaRN.obterPorId(new Conta(codigo));
			
		} catch (RNException e) {
			e.printStackTrace();
		}
	}

	public Locale getLocaleUsuario() {
//		if (this.localizacao == null) {
			Usuario usuario = this.getUsuarioLogado();
			String idioma = usuario.getIdioma();
			String[] info = idioma.split("_");
			this.localizacao = new Locale(info[0], info[1]);
//		}
		return this.localizacao;
	}

	public List<Locale> getIdiomas() {
		FacesContext context = FacesContext.getCurrentInstance();
		Iterator<Locale> locales = context.getApplication().getSupportedLocales();
		this.idiomas = new ArrayList<Locale>();
		while (locales.hasNext()) {
			this.idiomas.add(locales.next());
		}
		return idiomas;
	}

	public void setIdiomaUsuario(String idioma) {
		try {
			UsuarioRN usuarioRN = new UsuarioRN();
			this.usuarioLogado = usuarioRN.obterPorId(getUsuarioLogado());
			this.usuarioLogado.setIdioma(idioma);
			usuarioRN.salvar(this.usuarioLogado);
			
			String[] info = idioma.split("_");
			Locale locale = new Locale(info[0], info[1]);
			
			FacesContext context = FacesContext.getCurrentInstance();
			context.getViewRoot().setLocale(locale);
		} catch (RNException e) {
			e.printStackTrace();
		}
	}
}










