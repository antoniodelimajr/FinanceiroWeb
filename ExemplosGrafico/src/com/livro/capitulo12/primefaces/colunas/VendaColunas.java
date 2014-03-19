package com.livro.capitulo12.primefaces.colunas;

public class VendaColunas {

	private int	ano;
	private int	vendasBrasil;
	private int	vendasEstadosUnidos;
	private int	vendasAlemanha;

	public VendaColunas() {
	}

	public VendaColunas(int ano, int vendasBrasil, int vendasEstadosUnidos, int vendasAlemanha) {
		this.ano = ano;
		this.vendasBrasil = vendasBrasil;
		this.vendasEstadosUnidos = vendasEstadosUnidos;
		this.vendasAlemanha = vendasAlemanha;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getVendasBrasil() {
		return vendasBrasil;
	}

	public void setVendasBrasil(int vendasBrasil) {
		this.vendasBrasil = vendasBrasil;
	}

	public int getVendasEstadosUnidos() {
		return vendasEstadosUnidos;
	}

	public void setVendasEstadosUnidos(int vendasEstadosUnidos) {
		this.vendasEstadosUnidos = vendasEstadosUnidos;
	}

	public int getVendasAlemanha() {
		return vendasAlemanha;
	}

	public void setVendasAlemanha(int vendasAlemanha) {
		this.vendasAlemanha = vendasAlemanha;
	}
}