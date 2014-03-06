package br.com.sisnema.financeiroweb.util;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.sisnema.financeiroweb.action.ContextoBean;

public class ContextoUtil {

	public static ContextoBean getContextoBean() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		HttpSession session = (HttpSession) external.getSession(true);

		ContextoBean contextoBean = (ContextoBean) 
				session.getAttribute("contextoBean");
		
		return contextoBean;
	}
}
