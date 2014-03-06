package br.com.sisnema.financeiroweb.action;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.sisnema.financeiroweb.model.Categoria;
import br.com.sisnema.financeiroweb.negocio.CategoriaRN;
import br.com.sisnema.financeiroweb.util.ContextoUtil;
import br.com.sisnema.financeiroweb.util.RNException;

@ManagedBean(name = "categoriaBean")
@RequestScoped
public class CategoriaBean {

	private TreeNode categoriasTree;
	private Categoria editada = new Categoria();
	private List<SelectItem> categoriasSelect;
	private boolean mostraEdicao	= false;

	public void novo() {
		try {
			Categoria pai = null;
			if (this.editada.getCodigo() != null) {
				CategoriaRN categoriaRN = new CategoriaRN();
				pai = categoriaRN.obterPorId(editada);
			}
			
			this.editada = new Categoria();
			this.editada.setPai(pai);
			this.mostraEdicao = true;
			
		} catch (RNException e) {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							e.getMessage(), ""));
		}
	}

	public void selecionar(NodeSelectEvent event) {
		this.editada = (Categoria) event.getTreeNode().getData();

		Categoria pai = this.editada.getPai();
		if (this.editada != null && pai != null && pai.getCodigo() != null) {
			this.mostraEdicao = true;
		} else {
			this.mostraEdicao = false;
		}
	}

	public void salvar() {
		try {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
	
			CategoriaRN categoriaRN = new CategoriaRN();
			this.editada.setUsuario(contextoBean.getUsuarioLogado());
			categoriaRN.salvar(editada);
			
			this.editada = null;
			this.mostraEdicao = false;
			this.categoriasTree = null;
			this.categoriasSelect = null;
			
		} catch (RNException e) {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							e.getMessage(), ""));
		}
	}

	public void excluir() {
		try {
			CategoriaRN categoriaRN = new CategoriaRN();
			categoriaRN.excluir(editada);
			editada = null;
	
			mostraEdicao = false;
			categoriasTree = null;
			categoriasSelect = null;
			
		} catch (RNException e) {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							e.getMessage(), ""));
		}
	}

	public TreeNode getCategoriasTree() {
		if (this.categoriasTree == null) {
			try {
				ContextoBean contextoBean = ContextoUtil.getContextoBean();
	
				CategoriaRN categoriaRN = new CategoriaRN();
				List<Categoria> categorias =
						categoriaRN.pesquisar(new Categoria(
									contextoBean.getUsuarioLogado()));
				
				categoriasTree = new DefaultTreeNode(null, null);
				montaDadosTree(this.categoriasTree, categorias);
			} catch (RNException e) {
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								e.getMessage(), ""));
			}

		}
		return this.categoriasTree;
	}

	private void montaDadosTree(TreeNode pai, List<Categoria> lista) {
		if (lista != null && lista.size() > 0) {
			TreeNode filho = null;
			for (Categoria categoria : lista) {
				filho = new DefaultTreeNode(categoria, pai);
				this.montaDadosTree(filho, categoria.getFilhos());
			}
		}
	}

	public List<SelectItem> getCategoriasSelect() {
		if (this.categoriasSelect == null) {
			try {
				this.categoriasSelect = new ArrayList<SelectItem>();
				ContextoBean contextoBean = ContextoUtil.getContextoBean();
	
				CategoriaRN categoriaRN = new CategoriaRN();
				List<Categoria> categorias = categoriaRN.pesquisar(
					new Categoria( contextoBean.getUsuarioLogado()));
				
				this.montaDadosSelect(this.categoriasSelect, categorias, "");
				
			} catch (RNException e) {
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								e.getMessage(), ""));
			}
		}
		return categoriasSelect;
	}

	private void montaDadosSelect(List<SelectItem> select, List<Categoria> categorias, String prefixo) {

		SelectItem item = null;
		if (categorias != null) {
			for (Categoria categoria : categorias) {
				item = new SelectItem(categoria, prefixo + categoria.getDescricao());
				item.setEscape(false);
				select.add(item);
				this.montaDadosSelect(select, categoria.getFilhos(), prefixo + "&nbsp;&nbsp;");
			}
		}
	}

	public boolean isMostraEdicao() {
		return mostraEdicao;
	}

	public void setMostraEdicao(boolean mostraEdicao) {
		this.mostraEdicao = mostraEdicao;
	}

	public Categoria getEditada() {
		return editada;
	}

	public void setEditada(Categoria editada) {
		this.editada = editada;
	}
}
