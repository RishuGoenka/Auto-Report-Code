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
import com.jenkins.reporter.DailyReport;
import com.jenkins.reporter.FinalReport;

public class DailyReporting {

	static Logger log = Logger.getLogger(DailyReporting.class.getName());
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) {
		try {
			ConsolidatedReport.consolidatedFinalReportForAllProuct();
			List<FinalReport> report = new ArrayList<>();

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +1);
			java.util.Date lastdateobj = cal.getTime();
			java.util.Date edate = sdf.parse(sdf.format(lastdateobj));

			cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 0);
			java.util.Date firstdateobj = cal.getTime();
			java.util.Date sdate = sdf.parse(sdf.format(firstdateobj));

			Criterion restriction = Restrictions.and(Restrictions.ge("buildStartDate", sdate),
					Restrictions.le("buildStartDate", edate));

			report = HibernateWrappers.getObjectListByRestrictionPostgreSQL(
					Restrictions.and(restriction, Restrictions.like("buildGeneratedBy", "CI")), FinalReport.class);

			File fileePath = ExcelReporting.createBulkExcelSheet(report);

			Map<String, DailyReport> BDTdata = new HashMap<>();

			for (FinalReport repo : report) {
				DailyReport dailyreportdata = new DailyReport();

				dailyreportdata.setBuildStatus(repo.getBuildStatus());
				dailyreportdata.setDeploymentStatus(repo.getDeploymentStatus());
				dailyreportdata.setTestStatus(repo.getTestStatus());
				dailyreportdata.setFailedTestCount(repo.getFailed_tc());
				dailyreportdata.setPassedTestCount(repo.getPassed_tc());
				dailyreportdata.setSkippedTestCount(repo.getSkipped_tc());
				dailyreportdata.setTotalTestCount(repo.getTotal_tc());
				BDTdata.put(repo.getProduct(), dailyreportdata);
			}

			MailingService mailingService = new MailingService();
			mailingService.setSubject("CI Status as on (" + sdf.format(firstdateobj) + ")");
			String mailBody = tableGeneratorForLastDay(BDTdata, firstdateobj);
			mailingService.setMessageBodyContent(mailBody);
			mailingService.sendEmailNotification(fileePath, args[0]);
		} catch (Exception e) {
			log.debug("Daily Report : " + e);
			e.printStackTrace();
		}
	}

	public static String tableGeneratorForLastDay(Map<String, DailyReport> dailyreportdata, Date firstdateobj) {
		try {
			String mailBody = "<strong> Hi Team, </strong><br><br>";
			mailBody += "<p>Attaching Continuous Integration Summary Report for (" + sdf.format(firstdateobj) + ")</p><br>"
					 + "<style>table, th, td {border: 1px solid black;border-collapse: collapse;}th, td {padding: 5px;text-align: center;}</style>"
					 + "<table style='width:100%'><tr><th colspan='8' style='text-align: center;background: #fff8dc;'>Continuous Integration Report</th></tr>"
					 + "<tr><th style='background: #4285f3;'>Product</th>"
					 + "<th style='background: #4285f3;'>Build Status</th>" 
					 + "<th style='background: #4285f3;'>Deploy Status</th>"  
					 + "<th style='background: #4285f3;'>Test Status</th>" 
					 + "<th style='background: #4CAF50;'>Test Passed</th>" 
					 + "<th style='background: #f44336;'>Test Failed</th>"
					 + "<th style='background: #ff9800;'>Test Skipped</th>"
					 + "<th style='background: #e7e7e7;'>Test Total</th></tr>";
			List<String> products = new ArrayList<>();
			products.add("iManage");
			products.add("iSource");
			products.add("iContract");
			products.add("iSave");
			products.add("iAnalyze");
			for (String prod : products) {
				if (!dailyreportdata.containsKey(prod))
					dailyreportdata.put(prod, new DailyReport("-", "-", "-", "-", "-", "-", "-"));
			}

			for (Map.Entry<String, DailyReport> entry : dailyreportdata.entrySet()) {
				String product = entry.getKey();
				DailyReport report = entry.getValue();
				mailBody += "<tr><td style='background: #999;'>" + product + "</td>"
						 + "<td>" + report.getBuildStatus() + "</td>"
				         + "<td>" + report.getDeploymentStatus() + "</td>"
				         + "<td >" + report.getTestStatus() + "</td>"
				         + "<td style='border-color: #4CAF50;'>" + report.getPassedTestCount() + "</td>"
				         + "<td style='border-color: #f44336;'>" + report.getFailedTestCount() + "</td>"
				         + "<td style='border-color: #ff9800;'>" + report.getSkippedTestCount() + "</td>"
				         + "<td style='border-color: #e7e7e7;'>" + report.getTotalTestCount() + "</td></tr>";
			}
			mailBody += "</table><br>";
			mailBody += "Please refer BDT Notification of your respective product for further details.";
			mailBody += "<br><br>";
			mailBody += "<h4>Regards</h4>";
			mailBody += "<h4>Team PDTRM</h4>";
			return mailBody;
		} catch (Exception e) {
			log.debug("tableGeneratorForLastDay :" + e);
			System.out.println("tableGeneratorForLastDay : Somthing is wrong");
			return null;
		}
	}

}
