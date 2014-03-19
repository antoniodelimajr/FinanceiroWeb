package com.livro.capitulo3.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.livro.capitulo3.conexao.HibernateUtil;
import com.livro.capitulo3.pojos.Categoria;
import com.livro.capitulo3.pojos.Conta;
import com.livro.capitulo3.pojos.Usuario;

public class DAO {
	private Session session;
	
	public DAO() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	public void commit(){
		session.getTransaction().commit();
	}
	
	public void begin(){
		session.getTransaction().begin();
	}
	
	public void finalizaTransacao(){
		session.close();
	}
	
	public void salvar(Usuario usuario) {
		this.session.save(usuario);
	}
	
	public void salvar(Conta c) {
		this.session.save(c);
	}

	public Categoria salvar(Categoria categoria) {
		Categoria merged = (Categoria) this.session.merge(categoria);
		this.session.flush();
		this.session.clear();
		return merged;
	}

	public void atualizar(Usuario usuario) {

		if (usuario.getPermissao() == null || usuario.getPermissao().size() == 0) {
			Usuario usuarioPermissao = this.carregar(usuario.getCodigo());
			usuario.setPermissao(usuarioPermissao.getPermissao());
			this.session.evict(usuarioPermissao);
		}
		
		this.session.update(usuario);
	}

	public Usuario carregar(Integer codigo) {
		return (Usuario) this.session.get(Usuario.class, codigo);
	}

	public Usuario buscarPorLogin(String login) {
		String hql = "select u from Usuario u where u.login = :login";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("login", login);

		return (Usuario) consulta.uniqueResult();
	}
}
