package com.rest.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import com.jenkins.database.HibernateWrappers;
import com.jenkins.database.MailingService;
import com.jenkins.reporter.FinalReport;
import com.jenkins.reporter.Report;

public class Reporting {

	static Logger log = Logger.getLogger(Reporting.class.getName());
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) {
		try {
			List<FinalReport> report = new ArrayList<>();

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +1);
			java.util.Date lastdateobj = cal.getTime();
			java.util.Date edate = sdf.parse(sdf.format(lastdateobj));

			cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 0);
			java.util.Date presentdateobj = cal.getTime();
			java.util.Date presentdate = sdf.parse(sdf.format(presentdateobj));

			String diffDate = args[1];
			cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -Integer.parseInt(diffDate));
			java.util.Date firstdateobj = cal.getTime();
			java.util.Date sdate = sdf.parse(sdf.format(firstdateobj));

			Criterion restriction = Restrictions.and(Restrictions.ge("buildStartDate", sdate),
					Restrictions.le("buildStartDate", edate));

			Criterion aborted = Restrictions.and(Restrictions.ne("deploymentStatus", "ABORTED"),
					Restrictions.ne("testStatus", "ABORTED"));

			report = HibernateWrappers.getObjectListByRestrictionPostgreSQL(restriction, FinalReport.class);

			File fileePath = ExcelReporting.createBulkExcelSheet(report);

			Map<String, Report> BDTdata = new HashMap<>();

			List<String> products = new ArrayList<>();
			products.add("iManage");
			products.add("iSource");
			products.add("iContract");
			products.add("iSave");
			products.add("iAnalyze");

			for (String prod : products) {
				List<FinalReport> listbuild_Success = HibernateWrappers.getObjectListByRestrictionPostgreSQL(
						Restrictions.and(Restrictions.and(Restrictions.eq("buildStatus", "SUCCESS"),
								Restrictions.and(restriction, Restrictions.eq("product", prod))), aborted),
						FinalReport.class);
				int build_Success = listbuild_Success.size();
				int build_Aborted = HibernateWrappers
						.getObjectListByRestrictionPostgreSQL(
								Restrictions.and(Restrictions.eq("buildStatus", "ABORTED"),
										Restrictions.and(restriction, Restrictions.eq("product", prod))),
								FinalReport.class)
						.size();
				int build_Failure = HibernateWrappers.getObjectListByRestrictionPostgreSQL(
						Restrictions.and(Restrictions.and(Restrictions.eq("buildStatus", "FAILURE"),
								Restrictions.and(restriction, Restrictions.eq("product", prod))), aborted),
						FinalReport.class).size();
				int build_Unstable = HibernateWrappers.getObjectListByRestrictionPostgreSQL(
						Restrictions.and(Restrictions.and(Restrictions.eq("buildStatus", "UNSTABLE"),
								Restrictions.and(restriction, Restrictions.eq("product", prod))), aborted),
						FinalReport.class).size();
				List<FinalReport> listdeploy_Success = HibernateWrappers
						.getObjectListByRestrictionPostgreSQL(Restrictions.and(
								Restrictions.and(Restrictions.eq("deploymentStatus", "SUCCESS"),
										Restrictions.and(restriction, Restrictions.eq("product", prod))),
								Restrictions.ne("testStatus", "ABORTED")), FinalReport.class);
				int deploy_Success = listdeploy_Success.size();
				int deploy_Aborted = HibernateWrappers
						.getObjectListByRestrictionPostgreSQL(
								Restrictions.and(Restrictions.eq("deploymentStatus", "ABORTED"),
										Restrictions.and(restriction, Restrictions.eq("product", prod))),
								FinalReport.class)
						.size();
				int deploy_Failure = HibernateWrappers.getObjectListByRestrictionPostgreSQL(Restrictions.and(
						Restrictions.and(Restrictions.eq("deploymentStatus", "FAILURE"),
								Restrictions.and(restriction, Restrictions.eq("product", prod))),
						Restrictions.ne("testStatus", "ABORTED")), FinalReport.class).size();
				int deploy_Unstable = HibernateWrappers.getObjectListByRestrictionPostgreSQL(Restrictions.and(
						Restrictions.and(Restrictions.eq("deploymentStatus", "UNSTABLE"),
								Restrictions.and(restriction, Restrictions.eq("product", prod))),
						Restrictions.ne("testStatus", "ABORTED")), FinalReport.class).size();
				List<FinalReport> listtest_Success = HibernateWrappers
						.getObjectListByRestrictionPostgreSQL(
								Restrictions.and(Restrictions.eq("testStatus", "SUCCESS"),
										Restrictions.and(restriction, Restrictions.eq("product", prod))),
								FinalReport.class);
				int test_Success = listtest_Success.size();
				int test_Aborted = HibernateWrappers
						.getObjectListByRestrictionPostgreSQL(
								Restrictions.and(Restrictions.eq("testStatus", "ABORTED"),
										Restrictions.and(restriction, Restrictions.eq("product", prod))),
								FinalReport.class)
						.size();
				int test_Failure = HibernateWrappers
						.getObjectListByRestrictionPostgreSQL(
								Restrictions.and(Restrictions.eq("testStatus", "FAILURE"),
										Restrictions.and(restriction, Restrictions.eq("product", prod))),
								FinalReport.class)
						.size();
				List<FinalReport> listtest_Unstable = HibernateWrappers
						.getObjectListByRestrictionPostgreSQL(
								Restrictions.and(Restrictions.eq("testStatus", "UNSTABLE"),
										Restrictions.and(restriction, Restrictions.eq("product", prod))),
								FinalReport.class);
				int test_Unstable = listtest_Unstable.size();
				float buildstability = Math.round(
						((float) build_Success / (float) (build_Success + build_Failure + build_Unstable)) * 100);
				float deploystability = Math.round(
						((float) deploy_Success / (float) (deploy_Success + deploy_Failure + deploy_Unstable)) * 100);
				float teststability = Math.round(
						((float) (test_Success + test_Unstable) / (float) (test_Success + test_Failure + test_Unstable))
								* 100);
				float BDTstability = Math.round(((float) (test_Success + test_Unstable)
						/ (float) (build_Success + build_Failure + build_Unstable)) * 100);
				int BDTaborted = build_Aborted + deploy_Aborted + test_Aborted;

				BDTdata.put(prod,
						new Report(build_Success, build_Aborted, build_Failure, build_Unstable, deploy_Success,
								deploy_Aborted, deploy_Failure, deploy_Unstable, test_Success, test_Aborted,
								test_Failure, test_Unstable, buildstability, deploystability, teststability,
								BDTstability, BDTaborted));
			}

			MailingService mailingService = new MailingService();
			mailingService.setSubject("CI Summary Report as on (" + sdf.format(firstdateobj) + ")");
			String mailBody = tableGeneratorForLastDay(BDTdata, firstdateobj, presentdate);
			mailingService.setMessageBodyContent(mailBody);
			mailingService.sendEmailNotification(fileePath, args[0]);
		} catch (Exception e) {
			log.debug("Daily Report : " + e);
			e.printStackTrace();
		}
	}

	public static String tableGeneratorForLastDay(Map<String, Report> reportdata, Date firstdateobj, Date presentdate) {
		try {
			String mailBody = "<strong> Hi Team, </strong><br><br>";
			mailBody += "<p>Attaching Continuous Integration Summary Report as on (" + sdf.format(firstdateobj)
					+ ") - (" + sdf.format(presentdate) + ")</p><br>"
					+ "<style>table, th, td {border: 1px solid black;border-collapse: collapse;}th, td {padding: 5px;text-align: center;}</style>"
					+ "<table style='width:100%'><tr><th colspan='9' style='text-align: center;background: #fff8dc;'>Continuous Integration Report</th></tr>"
					+ "<tr><th rowspan='2' style='background: #8ED;'>Product</th>"
					+ "<th colspan='2' style='background: #999;'>Build</th>"
					+ "<th colspan='2' style='background: #998;'>Deploy</th>"
					+ "<th colspan='2' style='background: #997;'>Test</th>"
					+ "<th colspan='2' style='background: #fffff9;'>BDT</th>"
					+ "<tr><th colspan='2' style='background: #4CAF50;'>Execution Percentage</th>"
					+ "<th colspan='2' style='background: #4CAF50;'>Execution Percentage</th>"
					+ "<th colspan='2' style='background: #4CAF50;'>Execution Percentage</th>"
					+ "<th colspan='2' style='background: #4CAF50;'>Execution Percentage</th></tr>";

			for (Map.Entry<String, Report> entry : reportdata.entrySet()) {
				String product = entry.getKey();
				Report report = entry.getValue();
				int buildTotal = report.getBuild_Failure() + report.getBuild_Unstable() + report.getBuild_Success();
				String build = Integer.toString(report.getBuild_Success()) + "/" + Integer.toString(buildTotal);

				int deployTotal = report.getDeploy_Failure() + report.getDeploy_Unstable() + report.getDeploy_Success();
				String deploy = Integer.toString(report.getDeploy_Success()) + "/" + Integer.toString(deployTotal);

				int testSuccess = report.getTest_Success() + report.getTest_Unstable();
				int testTotal = testSuccess + report.getTest_Failure();
				String test = Integer.toString(testSuccess) + "/" + Integer.toString(testTotal);

				String BDT = Integer.toString(testSuccess) + "/" + Integer.toString(buildTotal);

				mailBody += "<tr><td style='background: #8ED;'>" + product + "</td>"
						+ "<td style='border-color: #4CAF50;'>" + report.getBuildstability() + "</td>"
						+ "<td style='border-color: #4CAF50;'>" + build + "</td>"
						+ "<td style='border-color: #4CAF50;'>" + report.getDeploystability() + "</td>"
						+ "<td style='border-color: #4CAF50;'>" + deploy + "</td>"
						+ "<td style='border-color: #4CAF50;'>" + report.getTeststability() + "</td>"
						+ "<td style='border-color: #4CAF50;'>" + test + "</td>" + "<td style='border-color: #4CAF50;'>"
						+ report.getBDTstability() + "</td>" + "<td style='border-color: #4CAF50;'>" + BDT
						+ "</td></tr>";
			}

			mailBody += "</table><br><br>" + "<table style='width:60%'>"
					+ "<tr><th colspan='5' style='text-align: center;background: #f44336;'>BDT Aborted</th></tr>"
					+ "<tr><th rowspan='2' style='background: #8ED;'>Product</th>"
					+ "<th style='background: #999;'>Build</th>" + "<th style='background: #998;'>Deploy</th>"
					+ "<th style='background: #997;'>Test</th>"
					+ "<th rowspan='2' style='background: #fffff9;'>Total</th></tr>"
					+ "<tr><th style='background: #2196f3;'>Total</th>" + "<th style='background: #2196f3;'>Total</th>"
					+ "<th style='background: #2196f3;'>Total</th>";

			for (Map.Entry<String, Report> entry : reportdata.entrySet()) {
				String product = entry.getKey();
				Report report = entry.getValue();
				mailBody += "<tr><td style='background: #8ED;'>" + product + "</td>"
						+ "<td style='border-color: #f44336;'>" + report.getBuild_Aborted() + "</td>"
						+ "<td style='border-color: #f44336;'>" + report.getDeploy_Aborted() + "</td>"
						+ "<td style='border-color: #f44336;'>" + report.getTest_Aborted() + "</td>"
						+ "<td style='border-color: #f44336;'>" + report.getAborted() + "</td></tr>";
			}
			/*
			 * mailBody += "</table><br>" + "<table style='width:60%'>" +
			 * "<tr><th colspan='6' style='text-align: center;background: #4CAF50;'>BDT Average Time in Minutes</th></tr>"
			 * + "<tr><th style='background: #8ED;'>Product</th>" +
			 * "<th style='background: #999;'>Build</th>" +
			 * "<th style='background: #998;'>Deploy</th>" +
			 * "<th style='background: #997;'>Test Sanity</th>" +
			 * "<th style='background: #997;'>Test Smoke</th>" +
			 * "<th style='background: #997;'>Test Regretion</th></tr>";
			 * 
			 * mailBody += table;
			 */
			mailBody += "</table><br>";
			mailBody += "Please refer BDT Notification of your respective product for further details.";
			mailBody += "<br><br>";
			mailBody += "<h4>Regards</h4>";
			mailBody += "<h4>Team PDTRM</h4>";
			return mailBody;
		} catch (Exception e) {
			log.debug("tableGeneratorForLastDay :" + e);
			e.printStackTrace();
			return null;
		}
	}

}
