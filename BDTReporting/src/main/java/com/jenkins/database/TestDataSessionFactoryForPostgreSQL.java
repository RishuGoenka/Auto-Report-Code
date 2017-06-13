package com.jenkins.database;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class TestDataSessionFactoryForPostgreSQL {
	private static final SessionFactory sessionFactoryForPostgreSQL;
	private static ServiceRegistry serviceRegistryForPostgreSQL;
	static Logger log = Logger.getLogger(TestDataSessionFactoryForPostgreSQL.class.getName());

	public TestDataSessionFactoryForPostgreSQL() {
		super();
	}

	static {
		try {
			log.info("Static Content for PostgreSQL");
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
			configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
			configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://192.168.5.251:5432/RM_REPORT");
			configuration.setProperty("hibernate.connection.username", "postgres");
			configuration.setProperty("hibernate.connection.password", "postgres123");
			serviceRegistryForPostgreSQL = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
					.buildServiceRegistry();
			sessionFactoryForPostgreSQL = configuration.buildSessionFactory(serviceRegistryForPostgreSQL);
		} catch (Throwable e) {
			log.debug("Failed to create sessionFactory object." + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactoryForPostgreSQL;
	}
}
