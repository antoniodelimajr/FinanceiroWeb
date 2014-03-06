package br.com.sisnema.financeiroweb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.hibernate.SessionFactory;

import br.com.sisnema.financeiroweb.util.HibernateUtil;

@WebFilter(urlPatterns={"*.jsf","/webservice/*"})
public class ConexaoHibernateFilter implements Filter {

	private SessionFactory sessao;
	
	
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		try{
			// Inicio uma transação
			sessao.getCurrentSession().beginTransaction();
			
			// Segue o baile (vai ao servidor fazer o que foi requisitado)
			chain.doFilter(request, response);
			
			// Ao voltar do servidor, comita a transação
			sessao.getCurrentSession().getTransaction().commit();
			
		} catch(Throwable e){
			
			// Caso tenha ocorrido algum erro, da ROLLBACK (CANCELA TUDO)
			if(sessao.getCurrentSession().getTransaction().isActive()){
				sessao.getCurrentSession().getTransaction().rollback();
			}
			
		} finally { 
			// Após ter comitado a transação fecha-a.
			sessao.getCurrentSession().close();
		} 
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// INICIALIZO A VARIAVEL SESSAO
		sessao = HibernateUtil.getSessionFactory();
	}
}





