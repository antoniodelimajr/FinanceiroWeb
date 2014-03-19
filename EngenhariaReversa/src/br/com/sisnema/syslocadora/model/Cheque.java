package br.com.sisnema.syslocadora.model;

// Generated 29/01/2014 22:23:41 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Cheque generated by hbm2java
 */
@Entity
@Table(name = "cheque", catalog = "agenda")
public class Cheque implements java.io.Serializable {

	private ChequeId id;
	private Lancamento lancamento;
	private ContaBancaria contaBancaria;
	private Date dataCadastro;
	private String situacao;

	public Cheque() {
	}

	public Cheque(ChequeId id, ContaBancaria contaBancaria, String situacao) {
		this.id = id;
		this.contaBancaria = contaBancaria;
		this.situacao = situacao;
	}

	public Cheque(ChequeId id, Lancamento lancamento,
			ContaBancaria contaBancaria, Date dataCadastro, String situacao) {
		this.id = id;
		this.lancamento = lancamento;
		this.contaBancaria = contaBancaria;
		this.dataCadastro = dataCadastro;
		this.situacao = situacao;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "conta", column = @Column(name = "conta", nullable = false)),
			@AttributeOverride(name = "cheque", column = @Column(name = "cheque", nullable = false)) })
	public ChequeId getId() {
		return this.id;
	}

	public void setId(ChequeId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lancamento")
	public Lancamento getLancamento() {
		return this.lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "conta", nullable = false, insertable = false, updatable = false)
	public ContaBancaria getContaBancaria() {
		return this.contaBancaria;
	}

	public void setContaBancaria(ContaBancaria contaBancaria) {
		this.contaBancaria = contaBancaria;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro", length = 19)
	public Date getDataCadastro() {
		return this.dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	@Column(name = "situacao", nullable = false)
	public String getSituacao() {
		return this.situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

}
