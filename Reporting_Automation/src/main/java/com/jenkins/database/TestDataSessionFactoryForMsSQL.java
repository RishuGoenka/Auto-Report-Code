package com.jenkins.database;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class TestDataSessionFactoryForMsSQL {
	private static final SessionFactory sessionFactoryForMsSQL;
	private static ServiceRegistry serviceRegistryForMsSQL;
	static Logger log = Logger.getLogger(TestDataSessionFactoryForMsSQL.class.getClass());

	public TestDataSessionFactoryForMsSQL() {
		super();
	}

	static {
		try {
			log.info("Static Content for MsSQL");
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServer2008Dialect");
			configuration.setProperty("hibernate.connection.driver_class",
					"com.microsoft.sqlserver.jdbc.SQLServerDriver");
			configuration.setProperty("hibernate.connection.url",
					"jdbc:sqlserver://192.168.4.184:1433;databaseName=AUTOMATION_LOCAL");
			configuration.setProperty("hibernate.connection.username", "AUTOMATION_TESTING");
			configuration.setProperty("hibernate.connection.password", "AUTOMATION_TESTING");
			serviceRegistryForMsSQL = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
					.buildServiceRegistry();
			sessionFactoryForMsSQL = configuration.buildSessionFactory(serviceRegistryForMsSQL);
		} catch (Throwable e) {
			log.info("Failed to create sessionFactory object for MsSQL : " + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactoryForMsSQL;
	}
}
