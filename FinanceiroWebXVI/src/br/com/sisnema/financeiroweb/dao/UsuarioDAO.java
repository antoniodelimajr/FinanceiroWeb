package br.com.sisnema.financeiroweb.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.sisnema.financeiroweb.model.Usuario;
import br.com.sisnema.financeiroweb.util.DAOException;

public class UsuarioDAO extends DAO<Usuario> {

	@Override
	public void salvar(Usuario usuario) throws DAOException {
		sessao.save(usuario);
	}
	
	public void atualizar(Usuario usuario) throws DAOException {
		
		if(usuario.getPermissao() == null || 
				usuario.getPermissao().size() == 0){
			
			Usuario usPermissaoTemporario = obterPorId(usuario);
			usuario.setPermissao(usPermissaoTemporario.getPermissao());
			sessao.evict(usPermissaoTemporario);
		}
		
		sessao.update(usuario);
	}

	@Override
	public void excluir(Usuario usuario) throws DAOException {
		sessao.delete(usuario);
	}

	@Override
	public Usuario obterPorId(Usuario filtro) throws DAOException {
		return (Usuario) sessao.get(Usuario.class, filtro.getCodigo());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> pesquisar(Usuario filtros) throws DAOException {
		return sessao.createCriteria(Usuario.class).list();
	}
	
	
	public Usuario buscarPorLogin(String login){
		String hql = "select u from Usuario u where u.login = :login";
		
		Query consulta = sessao.createQuery(hql);
		consulta.setString("login", login);
		
		return (Usuario) consulta.uniqueResult();
	}
	
	
	public List<Usuario> pesquisarDaTela(Usuario filtros){
		Criteria criteria = sessao.createCriteria(Usuario.class);
		
		
		if(filtros.getNome() != null){
			criteria.add(Restrictions.ilike("nome", filtros.getNome(),
											 MatchMode.ANYWHERE ));	
		}
		
		if(filtros.getNascimento() != null){
			criteria.add(Restrictions.ge("nascimento", filtros.getNascimento()));
		}
		
		criteria.addOrder(Order.asc("nome"));
		
		return criteria.list();
		
	}

}












