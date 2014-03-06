package br.com.sisnema.financeiroweb.negocio;

import java.util.List;

import br.com.sisnema.financeiroweb.dao.UsuarioDAO;
import br.com.sisnema.financeiroweb.model.Usuario;
import br.com.sisnema.financeiroweb.util.DAOException;
import br.com.sisnema.financeiroweb.util.RNException;

public class UsuarioRN extends RN<Usuario> {
	
	public UsuarioRN() {
		super(new UsuarioDAO());
	}

	@Override
	public void salvar(Usuario usuario) throws RNException {
		try {
			
			// Inserção
			if(usuario.getCodigo() == null || usuario.getCodigo() == 0){
				Usuario user = buscarPorLogin(usuario.getLogin());
				
				if(user != null){
					throw new RNException("Já existe um usuário com o login informado.");
				}
				
				usuario.getPermissao().add("ROLE_USUARIO");
				
				dao.salvar(usuario);
				
				CategoriaRN categoriaRN = new CategoriaRN();
				categoriaRN.salvaEstruturaPadrao(usuario);
			
			// Alteração
			} else {
//				UsuarioDAO userDAO = (UsuarioDAO) dao;
//				userDAO.atualizar(usuario);
				
				((UsuarioDAO) dao).atualizar(usuario);
			}
			
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}
	

	public Usuario buscarPorLogin(String login){
		// UsuarioDAO userDao = (UsuarioDAO) dao;
		// return userDao.buscarPorLogin(login);
		
		return ((UsuarioDAO) dao).buscarPorLogin(login);
	}
	

	@Override
	public void excluir(Usuario pojo) throws RNException {
		try {
			CategoriaRN categoriaRN = new CategoriaRN();
			categoriaRN.excluir(pojo);
			
			dao.excluir(pojo);
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}

	@Override
	public Usuario obterPorId(Usuario filtro) throws RNException {
		try {
			return dao.obterPorId(filtro);
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}

	@Override
	public List<Usuario> pesquisar(Usuario filtros) throws RNException {
		try {
			return dao.pesquisar(filtros);
		} catch (DAOException e) {
			throw new RNException(e);
		}
	}

}
