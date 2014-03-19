/**
 * FinanceiroWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.com.sisnema.financeiroweb.webservice;

public interface FinanceiroWS extends java.rmi.Remote {
    public float saldo(int conta, java.util.Calendar dataSaldo) throws java.rmi.RemoteException;
    public br.com.sisnema.financeiroweb.webservice.LancamentoItem[] extrato(int conta, java.util.Calendar de, java.util.Calendar ate) throws java.rmi.RemoteException;
}
