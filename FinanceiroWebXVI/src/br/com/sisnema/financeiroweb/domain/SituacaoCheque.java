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
	 * Não emitido
	 */
	N("Não emitido");
	
	private String descricao;
	
	private SituacaoCheque(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
