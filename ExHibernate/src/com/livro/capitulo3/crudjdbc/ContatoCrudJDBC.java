package com.livro.capitulo3.crudjdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContatoCrudJDBC {
	
	public void salvar(Contato contato){
		Connection conexao = geraConexao();
		PreparedStatement insereSt =null;
		
		String sql = "insert into contato(nome, telefone, email, dt_cad, obs) values (?, ?, ?, ?, ?)";
		try {
			insereSt = conexao.prepareStatement(sql);

			insereSt.setString(1, contato.getNome());
			insereSt.setString(2, contato.getTelefone());
			insereSt.setString(3, contato.getEmail());
			insereSt.setDate(4, contato.getDataCadastro());
			insereSt.setString(5, contato.getObservacao());
			insereSt.setString(1, contato.getNome());
			insereSt.setString(1, contato.getNome());
			
			insereSt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				insereSt.close();
				conexao.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	
	public void atualizar(Contato contato){}
	public void excluir(Contato contato){}
	
	public List<Contato> listar(){
		Connection conexao = geraConexao();
		List<Contato> contatos = new ArrayList<Contato>();
		
		Statement consulta =null;
		ResultSet resultado = null;
		Contato contato = null;
		
		String sql = "select * from contato";
		try {
			consulta = conexao.createStatement();
			resultado = consulta.executeQuery(sql);
			
			while (resultado.next()) {
				contato = new Contato();
				contato.setCodigo(resultado.getInt("codigo"));
				contato.setDataCadastro(resultado.getDate("dataCadastro"));
				contato.setEmail(resultado.getString("email"));
				contato.setNome(resultado.getString("nome"));
				contato.setObservacao(resultado.getString("observacao"));
				contato.setTelefone(resultado.getString("telefone"));
				contatos.add(contato);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				consulta.close();
				resultado.close();
				conexao.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		
		return contatos;
	}
	
	public Contato buscaContato(int valor){return null;}
	public Connection geraConexao(){return null;}
	
	public static void main(String[] args) {
	
	}
	
}
