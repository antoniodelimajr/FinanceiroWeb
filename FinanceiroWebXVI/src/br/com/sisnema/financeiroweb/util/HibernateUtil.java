package br.com.sisnema.financeiroweb.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionfactory();

	private static SessionFactory buildSessionfactory() {

		try {
			AnnotationConfiguration cfg = new AnnotationConfiguration();
			cfg.configure("hibernate.cfg.xml");
			return cfg.buildSessionFactory();
		} catch (Throwable e) {
			System.out
					.println("Criação inicial do objeto SessionFacotry falhou. Erro "
							+ e);
			throw new ExceptionInInitializerError(e);

		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
