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

	public static void main(String[] args) throws Exception {
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

			report = HibernateWrappers.getObjectListByRestrictionMsSQL(
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
			mailingService.setSubject("Continuous Integration Status as on (" + sdf.format(firstdateobj) + ")");
			String mailBody = tableGeneratorForLastDay(BDTdata, firstdateobj);
			mailingService.setMessageBodyContent(mailBody);
			mailingService.sendEmailNotification(fileePath);
		} catch (Exception e) {
			log.debug("Daily Report : " + e);
		}
	}

	public static String tableGeneratorForLastDay(Map<String, DailyReport> dailyreportdata, Date firstdateobj) {
		try {
			String mailBody = "<strong> Hi Team, </strong><br><br>";
			mailBody += "<p>Attaching Continuous Integration Summary Report as on (" + sdf.format(firstdateobj)
					+ ")</p><br>";
			mailBody += "<table style=\"border:1px solid #000\" cellspacing=\"1\" cellpadding=\"5\" align=\"center\" width=\"450\">"
					+ "<tr bgcolor=\"#C0F686\">" + "<th style=\"border:1px solid #000\" >Product</th>"
					+ "<th style=\"border:1px solid #000\">Build</th>"
					+ "<th style=\"border:1px solid #000\">Deploy</th>"
					+ "<th style=\"border:1px solid #000\">Test</th>"
					+ "<th style=\"border:1px solid #000\">Passed</th>"
					+ "<th style=\"border:1px solid #000\">Failed</th>"
					+ "<th style=\"border:1px solid #000\">Skipped</th>"
					+ "<th style=\"border:1px solid #000\">Total</th> </tr>";
			List<String> products = new ArrayList<>();
			products.add("iManage");
			products.add("iSource");
			products.add("iContract");
			products.add("iSave");
			for(String prod : products){
			if(!dailyreportdata.containsKey(prod))
				dailyreportdata.put(prod,new  DailyReport("Running", "No Info Available", "No Info Available", "No Info Available", "No Info Available", "No Info Available", "No Info Available"));
			}
			
			for (Map.Entry<String, DailyReport> entry : dailyreportdata.entrySet()) {
				String product = entry.getKey();
				DailyReport report = entry.getValue();
				mailBody += "<tr>"
						+ "<td style=\"border:1px solid #000\"><font color=\"#1B1BEE\"> "+ product+ " </font></td>"
						+ "<td style=\"border:1px solid #000\">" + report.getBuildStatus() + "</td>"
						+ "<td style=\"border:1px solid #000\">" + report.getDeploymentStatus() + "</td>"
						+ "<td style=\"border:1px solid #000\">" + report.getTestStatus() + "</td>"
						+ "<td style=\"border:1px solid #000\">" + report.getPassedTestCount() + "</td>"
						+ "<td style=\"border:1px solid #000\">" + report.getFailedTestCount() + "</td>"
						+ "<td style=\"border:1px solid #000\">" + report.getSkippedTestCount() + "</td>"
						+ "<td style=\"border:1px solid #000\">" + report.getTotalTestCount() + "</td>" + "</tr>" ;
			}
			mailBody += "</table><br>";
			mailBody += "Please refer BDT Notification of your respective product for further details.";
			mailBody += "<br><br>";
			mailBody += "<h4>Regards</h4>";
			mailBody += "<h4>Team PDTRM</h4>";
			return mailBody;
		} catch (Exception e) {
			log.debug(e);
			return null;
		}
	}

}
