package br.com.sisnema.financeiroweb.dao;

import java.util.List;

import org.hibernate.Query;

import br.com.sisnema.financeiroweb.model.Categoria;
import br.com.sisnema.financeiroweb.util.DAOException;

public class CategoriaDAO extends DAO<Categoria> {

	@Override
	public void salvar(Categoria pojo) throws DAOException {
		throw new DAOException("Não usar este método");
	}
	
	public Categoria salvarCategoria(Categoria categoria) throws DAOException {
		Categoria merged = (Categoria) sessao.merge(categoria);
		sessao.flush();
		sessao.clear();
		return merged;	
	}

	@Override
	public void excluir(Categoria categoria) throws DAOException {
		categoria = (Categoria) obterPorId(categoria);
		sessao.delete(categoria);
		sessao.flush();
		sessao.clear();
	}

	@Override
	public Categoria obterPorId(Categoria filtro) throws DAOException {
		return (Categoria) sessao.get(Categoria.class, filtro.getCodigo());
	}

	@Override
	public List<Categoria> pesquisar(Categoria filtros) throws DAOException {
		String hql = "select c from Categoria c where "
					+ " c.pai is null and c.usuario = :usuario";
		Query query = sessao.createQuery(hql);
		query.setInteger("usuario", filtros.getUsuario().getCodigo());

		List<Categoria> lista = query.list();

		return lista;
	}

}













