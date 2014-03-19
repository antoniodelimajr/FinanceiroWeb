package br.com.sisnema.syslocadora.model;

// Generated 29/01/2014 22:23:41 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Acao generated by hbm2java
 */
@Entity
@Table(name = "acao", catalog = "agenda")
public class Acao implements java.io.Serializable {

	private Integer codAcao;
	private Usuario usuario;
	private String descricao;
	private Character origem;
	private Integer quantidade;
	private String sigla;

	public Acao() {
	}

	public Acao(Usuario usuario, String descricao, Character origem,
			Integer quantidade, String sigla) {
		this.usuario = usuario;
		this.descricao = descricao;
		this.origem = origem;
		this.quantidade = quantidade;
		this.sigla = sigla;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "cod_acao", unique = true, nullable = false)
	public Integer getCodAcao() {
		return this.codAcao;
	}

	public void setCodAcao(Integer codAcao) {
		this.codAcao = codAcao;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_usuario")
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "descricao", length = 30)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name = "origem", length = 1)
	public Character getOrigem() {
		return this.origem;
	}

	public void setOrigem(Character origem) {
		this.origem = origem;
	}

	@Column(name = "quantidade")
	public Integer getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	@Column(name = "sigla", length = 10)
	public String getSigla() {
		return this.sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

}