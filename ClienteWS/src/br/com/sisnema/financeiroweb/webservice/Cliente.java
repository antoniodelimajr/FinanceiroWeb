package br.com.sisnema.financeiroweb.webservice;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Calendar;

import javax.xml.rpc.ServiceException;

public class Cliente {
	public static void main(String[] args) {
		try {
			FinanceiroWSServiceLocator service = new FinanceiroWSServiceLocator();
			
			FinanceiroWS financeiroWS = service.getFinanceiroWSPort();
			Calendar inicio = Calendar.getInstance();
			inicio.add(Calendar.MONTH, -30);
			
			Calendar hoje = Calendar.getInstance();
			
			int conta = 2;
			
			Float saldo = financeiroWS.saldo(conta, hoje);
			System.out.println("Saldo da conta: "+saldo);
			
			System.out.println("============= Lançamentos da Conta =============");
			LancamentoItem[] lancamentos = financeiroWS.extrato(conta, inicio, hoje);
			
			for (LancamentoItem item : lancamentos) {
				System.out.println("Data: " + item.getData().getTime()  + 
						            ". Descrição: "+item.getDescricao() + 
						            ". Valor: "+item.getValor());
			}
			
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
