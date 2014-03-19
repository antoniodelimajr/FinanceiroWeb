package com.livro.capitulo12.primefaces.enquete;

public class Voto {

	private String	opcao;
	private int	total;

	public Voto() {
	}

	public Voto(String opcao, int total) {
		this.opcao = opcao;
		this.total = total;
	}

	public String getOpcao() {
		return opcao;
	}

	public void setOpcao(String opcao) {
		this.opcao = opcao;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
