package br.com.sisnema.financeiroweb.negocio;

import java.util.List;

import br.com.sisnema.financeiroweb.dao.CategoriaDAO;
import br.com.sisnema.financeiroweb.model.Categoria;
import br.com.sisnema.financeiroweb.model.Usuario;
import br.com.sisnema.financeiroweb.util.DAOException;
import br.com.sisnema.financeiroweb.util.RNException;

public class CategoriaRN extends RN<Categoria> {

	public CategoriaRN() {
		super(new CategoriaDAO());
	}

	@Override
	public void salvar(Categoria categoria) throws RNException {
		try {
			Categoria pai = categoria.getPai();
	
			if (pai == null) {
				String msg = "A Categoria " + categoria.getDescricao() + " deve ter um pai definido";
				throw new IllegalArgumentException(msg);
			}
	
			boolean mudouFator = pai.getFator() != categoria.getFator();
	
			categoria.setFator(pai.getFator());
			categoria = ((CategoriaDAO)dao).salvarCategoria(categoria);
			
			if (mudouFator) {
				categoria = obterPorId(categoria);
				this.replicarFator(categoria, categoria.getFator());
			}
			
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}
	
	public void salvaEstruturaPadrao(Usuario usuario) throws RNException {
		try {
		
			CategoriaDAO catDAO = (CategoriaDAO) dao;
	
			Categoria despesas = new Categoria(null, usuario, "DESPESAS", -1);
			despesas = catDAO.salvarCategoria(despesas);
			
			catDAO.salvarCategoria(new Categoria(despesas, usuario, "Moradia", -1));
			catDAO.salvarCategoria(new Categoria(despesas, usuario, "Alimentação", -1));
			catDAO.salvarCategoria(new Categoria(despesas, usuario, "Vestuário", -1));
			catDAO.salvarCategoria(new Categoria(despesas, usuario, "Deslocamento", -1));
			catDAO.salvarCategoria(new Categoria(despesas, usuario, "Cuidados Pessoais", -1));
			catDAO.salvarCategoria(new Categoria(despesas, usuario, "Educação", -1));
			catDAO.salvarCategoria(new Categoria(despesas, usuario, "Saúde", -1));
			catDAO.salvarCategoria(new Categoria(despesas, usuario, "Lazer", -1));
			catDAO.salvarCategoria(new Categoria(despesas, usuario, "Despesas Financeiras", -1));
			
			Categoria receitas = new Categoria(null, usuario, "RECEITAS", 1);
			receitas = catDAO.salvarCategoria(receitas);
			catDAO.salvarCategoria(new Categoria(receitas, usuario, "Salário", 1));
			catDAO.salvarCategoria(new Categoria(receitas, usuario, "Restituições", 1));
			catDAO.salvarCategoria(new Categoria(receitas, usuario, "Rendimento", 1));
			
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}

	private void replicarFator(Categoria categoria, int fator) throws RNException {
		if (categoria.getFilhos() != null) {
			for (Categoria filho : categoria.getFilhos()) {
				try {
					filho.setFator(fator);
					((CategoriaDAO)dao).salvarCategoria(filho);
					this.replicarFator(filho, fator);
				} catch (DAOException e) {
					throw new RNException(e);
				}
			}
		}
	}

	@Override
	public void excluir(Categoria pojo) throws RNException {
		try {
			dao.excluir(pojo);
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}
	
	public void excluir(Usuario usuario) throws RNException {
		List<Categoria> lista = pesquisar(new Categoria(usuario));
		for (Categoria categoria: lista) {
			excluir(categoria);
		}
	}

	@Override
	public Categoria obterPorId(Categoria filtro) throws RNException {
		try {
			return dao.obterPorId(filtro);
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}

	@Override
	public List<Categoria> pesquisar(Categoria filtros) throws RNException {
		try {
			return dao.pesquisar(filtros);
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}

}
