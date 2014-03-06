package br.com.sisnema.financeiroweb.domain;

public enum SituacaoCheque {

	/**
	 * Baixado
	 */
	B("Baixado"),
	
	/**
	 * Cancelado
	 */
	C("Cancelado"),
	
	/**
	 * N�o emitido
	 */
	N("N�o emitido");
	
	private String descricao;
	
	private SituacaoCheque(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
