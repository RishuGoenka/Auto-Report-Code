package com.jenkins.database;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import com.jenkins.reporter.FinalReport;

public class HibernateWrappers {

	static Logger log = Logger.getLogger(HibernateWrappers.class.getName());

	public static void updateContractFolder(FinalReport finalReport) {
		SessionFactory sessionFactory = null;
		Session session = null;
		try {
			sessionFactory = TestDataSessionFactoryForPostgreSQL.getSessionFactory();
			session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.save(finalReport);
			transaction.commit();
		} catch (Exception e) {
			log.debug("updateContractFolder : " + e);
		} finally {
			if (session != null)
				session.close();
		}
	}

	public static <T> List<T> getObjectListByRestrictionPostgreSQL(Criterion restrictions, Class<T> classT) {
		SessionFactory sessionFactory = null;
		Session session = null;
		try {
			sessionFactory = TestDataSessionFactoryForPostgreSQL.getSessionFactory();
			session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(classT);
			List<T> objectList = criteria.add(restrictions).list();
			transaction.commit();
			return objectList;
		} catch (Exception e) {
			log.debug("getObjectListByRestrictionPostgreSQL : " + e);
			System.out.println("getObjectListByRestrictionPostgreSQL : Somthing is wrong");
			return null;
		} finally {
			if (session != null)
				session.close();
		}
	}

}
