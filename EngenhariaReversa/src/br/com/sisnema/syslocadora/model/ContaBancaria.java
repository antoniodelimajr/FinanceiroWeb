package br.com.sisnema.syslocadora.model;

// Generated 29/01/2014 22:23:41 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ContaBancaria generated by hbm2java
 */
@Entity
@Table(name = "conta_bancaria", catalog = "agenda")
public class ContaBancaria implements java.io.Serializable {

	private Integer codConta;
	private Usuario usuario;
	private Date datCadastro;
	private String desConta;
	private Boolean favorita;
	private Float saldoInicial;
	private Set<Lancamento> lancamentos = new HashSet<Lancamento>(0);
	private Set<Cheque> cheques = new HashSet<Cheque>(0);

	public ContaBancaria() {
	}

	public ContaBancaria(Usuario usuario, Date datCadastro) {
		this.usuario = usuario;
		this.datCadastro = datCadastro;
	}

	public ContaBancaria(Usuario usuario, Date datCadastro, String desConta,
			Boolean favorita, Float saldoInicial, Set<Lancamento> lancamentos,
			Set<Cheque> cheques) {
		this.usuario = usuario;
		this.datCadastro = datCadastro;
		this.desConta = desConta;
		this.favorita = favorita;
		this.saldoInicial = saldoInicial;
		this.lancamentos = lancamentos;
		this.cheques = cheques;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "cod_conta", unique = true, nullable = false)
	public Integer getCodConta() {
		return this.codConta;
	}

	public void setCodConta(Integer codConta) {
		this.codConta = codConta;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_usuario", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dat_cadastro", nullable = false, length = 19)
	public Date getDatCadastro() {
		return this.datCadastro;
	}

	public void setDatCadastro(Date datCadastro) {
		this.datCadastro = datCadastro;
	}

	@Column(name = "des_conta")
	public String getDesConta() {
		return this.desConta;
	}

	public void setDesConta(String desConta) {
		this.desConta = desConta;
	}

	@Column(name = "favorita")
	public Boolean getFavorita() {
		return this.favorita;
	}

	public void setFavorita(Boolean favorita) {
		this.favorita = favorita;
	}

	@Column(name = "saldo_inicial", precision = 12, scale = 0)
	public Float getSaldoInicial() {
		return this.saldoInicial;
	}

	public void setSaldoInicial(Float saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contaBancaria")
	public Set<Lancamento> getLancamentos() {
		return this.lancamentos;
	}

	public void setLancamentos(Set<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contaBancaria")
	public Set<Cheque> getCheques() {
		return this.cheques;
	}

	public void setCheques(Set<Cheque> cheques) {
		this.cheques = cheques;
	}

}
