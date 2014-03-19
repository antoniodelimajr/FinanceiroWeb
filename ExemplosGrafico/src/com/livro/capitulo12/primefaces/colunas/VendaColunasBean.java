package com.livro.capitulo12.primefaces.colunas;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="vendaColunasBean")
@RequestScoped
public class VendaColunasBean {

	private List<VendaColunas>	vendaColunas;

	public VendaColunasBean() {
		this.vendaColunas = new ArrayList<VendaColunas>();
		this.vendaColunas.add(new VendaColunas(2008, 191, 163, 178));
		this.vendaColunas.add(new VendaColunas(2009, 210, 300, 275));
		this.vendaColunas.add(new VendaColunas(2010, 35, 45, 60));
	}

	public List<VendaColunas> getVendaColunas() {
		return vendaColunas;
	}

	public void setVendaColunas(List<VendaColunas> vendaColunas) {
		this.vendaColunas = vendaColunas;
	}
}
