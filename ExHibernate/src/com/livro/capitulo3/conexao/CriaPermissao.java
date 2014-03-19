package com.livro.capitulo3.conexao;

import java.util.Date;

import com.livro.capitulo3.dao.DAO;
import com.livro.capitulo3.pojos.Categoria;
import com.livro.capitulo3.pojos.Conta;
import com.livro.capitulo3.pojos.Usuario;



public class CriaPermissao {

	public static void main(String[] args) {
		DAO dao = new DAO();

		criarUsuarioAluno(dao);
		criarUsuarioAdm(dao);
		dao.finalizaTransacao();
	}

	private static void criarUsuarioAdm(DAO dao) {
		Usuario adm = new Usuario();
		adm.setAtivo(true);
		adm.setCelular("123456");
		adm.setEmail("aluno@sisnema.com.br");
		adm.setIdioma("pt_BR");
		adm.setLogin("adm");
		adm.setNascimento(new Date());
		adm.setNome("Usuario Administrador");
		adm.setSenha("123");

		adm.getPermissao().add("ROLE_USUARIO");
		adm.getPermissao().add("ROLE_ADMINISTRADOR");
		
		Conta c = new Conta();
		c.setDataCadastro(new Date());
		c.setDescricao("Conta BB do Administrador");
		c.setFavorita(true);
		c.setSaldoInicial(100f);
		c.setUsuario(adm);
		
		dao.begin();
		dao.salvar(adm);
		dao.salvar(c);
		criaCategorias(adm, dao);
		dao.commit();
	}

	private static void criarUsuarioAluno(DAO dao) {
		Usuario aluno = new Usuario();

		aluno.setAtivo(true);
		aluno.setCelular("123456");
		aluno.setEmail("aluno@sisnema.com.br");
		aluno.setIdioma("pt_BR");
		aluno.setLogin("aluno");
		aluno.setNascimento(new Date());
		aluno.setNome("Usuario Aluno da Sisnema");
		aluno.setSenha("123");
		
		aluno.getPermissao().add("ROLE_USUARIO");

		Conta c = new Conta();
		c.setDataCadastro(new Date());
		c.setDescricao("Conta BB do Aluno");
		c.setFavorita(true);
		c.setSaldoInicial(100f);
		c.setUsuario(aluno);
		
		dao.begin();
		dao.salvar(aluno);
		dao.salvar(c);
		criaCategorias(aluno, dao);
		dao.commit();
	}
	
	private static void criaCategorias(Usuario usuario, DAO dao) {

		Categoria despesas = new Categoria(null, usuario, "DESPESAS", -1);
		despesas = dao.salvar(despesas);
		dao.salvar(new Categoria(despesas, usuario, "Moradia", -1));
		dao.salvar(new Categoria(despesas, usuario, "Alimentação", -1));
		dao.salvar(new Categoria(despesas, usuario, "Vestuário", -1));
		dao.salvar(new Categoria(despesas, usuario, "Deslocamento", -1));
		dao.salvar(new Categoria(despesas, usuario, "Cuidados Pessoais", -1));
		dao.salvar(new Categoria(despesas, usuario, "Educação", -1));
		dao.salvar(new Categoria(despesas, usuario, "Saúde", -1));
		dao.salvar(new Categoria(despesas, usuario, "Lazer", -1));
		dao.salvar(new Categoria(despesas, usuario, "Despesas Financeiras", -1));

		Categoria receitas = new Categoria(null, usuario, "RECEITAS", 1);
		receitas = dao.salvar(receitas);
		dao.salvar(new Categoria(receitas, usuario, "Salário", 1));
		dao.salvar(new Categoria(receitas, usuario, "Restituições", 1));
		dao.salvar(new Categoria(receitas, usuario, "Rendimento", 1));
	}
}
