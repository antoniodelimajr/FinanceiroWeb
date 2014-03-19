package com.livro.capitulo12.jfreechart.pizza;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "cidadeBean")
@RequestScoped
public class CidadeBean {

	private StreamedContent	grafico;
	private static final Logger	log	= Logger.getLogger(CidadeBean.class.getName());

	public CidadeBean() {
		try {
			JFreeChart graficoPizza = ChartFactory.createPieChart("5 cidades mais populosas de SC", this.geraDados(), true, true, false);
			File arquivoGrafico = new File("pizza.png");
			ChartUtilities.saveChartAsPNG(arquivoGrafico, graficoPizza, 500, 300);
			this.grafico = new DefaultStreamedContent(new FileInputStream(arquivoGrafico), "image/png");
		} catch (Exception e) {
			log.severe(e.getMessage());
		}
	}

	private DefaultPieDataset geraDados() {
		DefaultPieDataset dts = new DefaultPieDataset();
		dts.setValue("Joinville", new Double(497331.0));
		dts.setValue("Blumenau", new Double(299416.0));
		dts.setValue("Chapecó", new Double(174187.0));
		dts.setValue("Criciúma", new Double(188557.0));
		dts.setValue("Florianopólis", new Double(408161.0));
		return dts;
	}

	public StreamedContent getGrafico() {
		return grafico;
	}

	public void setGrafico(StreamedContent grafico) {
		this.grafico = grafico;
	}
}