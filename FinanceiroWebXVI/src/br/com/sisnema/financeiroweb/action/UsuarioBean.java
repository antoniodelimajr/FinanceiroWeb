package br.com.sisnema.financeiroweb.action;

import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.sisnema.financeiroweb.model.Conta;
import br.com.sisnema.financeiroweb.model.Usuario;
import br.com.sisnema.financeiroweb.negocio.ContaRN;
import br.com.sisnema.financeiroweb.negocio.UsuarioRN;
import br.com.sisnema.financeiroweb.util.RNException;

@ManagedBean
@RequestScoped
public class UsuarioBean {

	private Usuario usuario = new Usuario();
	private String confirmaSenha;
	private List<Usuario> lista;
	private String destinoSalvar;
	private Conta conta	= new Conta();
	
	public String novo() {
		destinoSalvar = "usuarioSucesso";
		usuario = new Usuario();
		usuario.setAtivo(true);
		return "usuario";
	}
	
	public String editar(){
		confirmaSenha= usuario.getSenha();
		return "/publico/usuario";
	}
	
	public String salvar(){
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			if (!usuario.getSenha().equalsIgnoreCase(confirmaSenha)) {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, 
						"Senha confirmada incorretamente", ""));
				return null;
			}

			UsuarioRN usuarioRN = new UsuarioRN();
			usuarioRN.salvar(usuario);

			if (conta.getDescricao() != null) {
				conta.setUsuario(usuario);
				conta.setFavorita(true);
				ContaRN contaRN = new ContaRN();
				contaRN.salvar(conta);
			}
			
			// Salva Usuário
			return destinoSalvar;
		} catch (RNException e) {
			e.printStackTrace();
			context.addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							e.getMessage(), ""));
		}
		
		return null;
	}
	public String excluir(){
		try {
			UsuarioRN usuarioRN = new UsuarioRN();
			usuarioRN.excluir(usuario);
			lista = null;
		} catch (RNException e) {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							e.getMessage(), ""));
		}
		return null;
	}
	
	public String ativar(){
		if(usuario.isAtivo()){
			usuario.setAtivo(false);
		} else {
			usuario.setAtivo(true);
		}

		return null;
	}

	public List<Usuario> getLista(){
		if(lista == null){
			try {
				UsuarioRN usuarioRN = new UsuarioRN();
				lista = usuarioRN.pesquisar(null);
			} catch (RNException e) {
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								e.getMessage(), ""));
			}
		}
		return lista;
	}
	
	public String atribuiPermissao(Usuario usuario, String permissao) {

		this.usuario = usuario;

		Set<String> permissoes = this.usuario.getPermissao();

		if (permissoes.contains(permissao)) {
			permissoes.remove(permissao);
		} else {
			permissoes.add(permissao);
		}
		return null;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public String getDestinoSalvar() {
		return destinoSalvar;
	}

	public void setDestinoSalvar(String destinoSalvar) {
		this.destinoSalvar = destinoSalvar;
	}

	public void setLista(List<Usuario> lista) {
		this.lista = lista;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
	

}
