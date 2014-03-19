package com.livro.capitulo12.primefaces.pizza;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="vendaBean")
@RequestScoped
public class VendaBean {

	private List<Venda>	vendaPais;

	public VendaBean() {
		this.vendaPais = new ArrayList<Venda>();
		this.vendaPais.add(new Venda("Brasil", 540.50f));
		this.vendaPais.add(new Venda("Estados Unidos", 500.52f));
		this.vendaPais.add(new Venda("Inglaterra", 475.30f));
		this.vendaPais.add(new Venda("França", 400));
		this.vendaPais.add(new Venda("Alemanha", 397.33f));
	}

	public List<Venda> getVendaPais() {
		return vendaPais;
	}

	public void setVendaPais(List<Venda> vendaPais) {
		this.vendaPais = vendaPais;
	}

}
