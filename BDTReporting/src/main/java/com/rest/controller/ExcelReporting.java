package com.rest.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import com.jenkins.database.HibernateWrappers;
import com.jenkins.database.MailingService;
import com.jenkins.database.TestDataSessionFactoryForMsSQL;
import com.jenkins.reporter.FinalReport;
import com.jenkins.reporter.BDTReport;

public class ExcelReporting {

	public static Logger log = Logger.getLogger(ExcelReporting.class.getName());
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) throws Exception {
		try {
			TestDataSessionFactoryForMsSQL.getSessionFactory();
			ConsolidatedReport.consolidatedFinalReportForAllProuct();

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +1);
			java.util.Date lastdateobj = cal.getTime();
			java.util.Date edate = sdf.parse(sdf.format(lastdateobj));

			cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			java.util.Date firstdateobj = cal.getTime();
			java.util.Date sdate = sdf.parse(sdf.format(firstdateobj));
			
			Criterion restriction = Restrictions.and(Restrictions.ge("deploymentStartDate", sdate),
					Restrictions.le("deploymentStartDate", edate));
			
			List<FinalReport> report = HibernateWrappers.getObjectListByRestrictionMsSQL(restriction,FinalReport.class);
			
			File fileePath = createBulkExcelSheet(report);
			BDTReport bdtReport = new BDTReport();

			getBDTDetails(report,bdtReport);

			MailingService mailingService = new MailingService();
			mailingService.setSubject("Continuous Integration Status from (" + sdf.format(firstdateobj) + ") to ("
					+ sdf.format(lastdateobj) + ") ");
			String mailBody = tableGenerator(sdate,edate,bdtReport);
			mailingService.setMessageBodyContent(mailBody);
			mailingService.sendEmailNotification(fileePath);
		} catch (Exception e) {
			log.debug(e);
		}
	}

	public static void getBDTDetails(List<FinalReport> report, BDTReport bdtReport) {
		try {
			for (FinalReport repo : report)
				System.out.println(repo.toString());

			// Data to be played is left

		} catch (Exception e) {
			log.debug("getBDTDetails :" + e);
		}
	}

	private static String tableGenerator(Date sdate, Date edate, BDTReport bdtReport) {
		try {

			String x = "<strong> Hi Team, </strong><br><br>";
			x += "<p> Check Attachement</p><br>";
			x += "<p>Attaching Monthly Continuous Integration Summary Report  (" + sdate + ") to ("
					+ edate + ") </p><br>";
			x += "<table style=\"border:1px solid #000\" cellspacing=\"1\" cellpadding=\"5\" align=\"center\" width=\"450\">"
					+ "<tr bgcolor=\"#C0F686\">" + "<th style=\"border:1px solid #000\" >Product</th>"
					+ "<th style=\"border:1px solid #000\">Build Success (%)</th>"
					+ "<th style=\"border:1px solid #000\">Deploy Success (%)</th>"
					+ "<th style=\"border:1px solid #000\">Test Success (%)</th>"
					+ "<th style=\"border:1px solid #000\">Complete BDT Success (%)</th>" + "</tr>" + "<tr>"
					+ "<td style=\"border:1px solid #000\"><font color=\"#1B1BEE\">iContract</font></td></tr>";
			
			//peace of code is left
			
			x += "</table><br><br>";
			x += "<h4>Regards</h4>";
			x += "<h4>Team Automation</h4>";
			return x;
		} catch (Exception e) {
			log.debug(e);
			return null;
		}
	}

	public static File createBulkExcelSheet(List<FinalReport> finalReportList) {
		try {
			Workbook wb = WorkbookFactory.create(new File("Files" + File.separator + "Final_Report.xlsx"));
			Sheet worksheet = wb.getSheet("Final_Report");
			Row fixedRow = worksheet.getRow(5);
			int x = fixedRow.getPhysicalNumberOfCells();
			for (int i = 0; i < finalReportList.size(); i++) {
				Row insertRow = copyRow(wb, worksheet, 5, 6 + i);
				Cell fixedCell;
				for (int j = 0; j < x; j++) {
					fixedCell = insertRow.getCell(j);
					String value = pickCellData(j, finalReportList.get(i)).toString();
					fixedCell.setCellValue(value.toString());
				}
			}
			File file = new File("Files" + File.separator + "Consolidated_Final_Report.xlsx");
			FileOutputStream output_file = new FileOutputStream(file);
			wb.write(output_file);
			output_file.close();
			return file;
		} catch (Exception e) {
			log.debug("createBulkExcelSheet :" + e);
			return null;
		}
	}

	public static String pickCellData(int i, FinalReport finalReport) {
		try {
			switch (i) {
			case 0:
				return finalReport.getProduct() == null ? "-" : finalReport.getProduct();
			case 1:
				return finalReport.getJenkinsBuildNumber() == null ? "-" : finalReport.getJenkinsBuildNumber();
			case 2:
				return finalReport.getBuildBranch() == null ? "-" : finalReport.getBuildBranch();
			case 3:
				return finalReport.getBuildGeneratedBy() == null ? "-" : finalReport.getBuildGeneratedBy();
			case 4:
				return finalReport.getBuildStartDate() == null ? "-" : finalReport.getBuildStartDate().toString();
			case 5:
				return finalReport.getBuildDuration() == null ? "-" : finalReport.getBuildDuration();
			case 6:
				return finalReport.getBuildStatus() == null ? "-" : finalReport.getBuildStatus();
			case 7:
				return finalReport.getDestination_path() == null ? "-" : finalReport.getDestination_path();
			case 8:
				return finalReport.getDestination_server() == null ? "-" : finalReport.getDestination_server();
			case 9:
				return finalReport.getDeploymentStatus() == null ? "-" : finalReport.getDeploymentStatus();
			case 10:
				return finalReport.getDeploymentStartDate() == null ? "-"
						: finalReport.getDeploymentStartDate().toString();
			case 11:
				return finalReport.getDeploymentDuration() == null ? "-" : finalReport.getDeploymentDuration();
			case 12:
				return finalReport.getTotal_tc() == null ? "-" : finalReport.getTotal_tc();
			case 13:
				return finalReport.getPassed_tc() == null ? "-" : finalReport.getPassed_tc();
			case 14:
				return finalReport.getFailed_tc() == null ? "-" : finalReport.getFailed_tc();
			case 15:
				return finalReport.getSkipped_tc() == null ? "-" : finalReport.getSkipped_tc();
			case 16:
				return finalReport.getTestDuration() == null ? "-" : finalReport.getTestDuration();
			case 17:
				return finalReport.getTestStatus() == null ? "-" : finalReport.getTestStatus();
			case 18:
				return finalReport.getqADeploymentStatus() == null ? "-" : finalReport.getqADeploymentStatus();
			default:
				return null;
			}
		} catch (Exception e) {
			log.debug("pickCellData :" + e);
			return null;
		}
	}

	private static Row copyRow(Workbook wb, Sheet worksheet, int sourceRowNum, int destinationRowNum) {
		try {
			Row newRow = worksheet.getRow(destinationRowNum);
			Row sourceRow = worksheet.getRow(sourceRowNum);

			if (newRow != null)
				worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
			else
				newRow = worksheet.createRow(destinationRowNum);

			for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
				Cell oldCell = sourceRow.getCell(i);
				Cell newCell = newRow.createCell(i);
				if (oldCell == null) {
					newCell = null;
					continue;
				}

				CellStyle newCellStyle = wb.createCellStyle();
				newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
				newCell.setCellStyle(newCellStyle);

				if (oldCell.getCellComment() != null)
					newCell.setCellComment(oldCell.getCellComment());

				if (oldCell.getHyperlink() != null)
					newCell.setHyperlink(oldCell.getHyperlink());

				newCell.setCellType(oldCell.getCellType());

				switch (oldCell.getCellType()) {
				case Cell.CELL_TYPE_BLANK:
					newCell.setCellValue(oldCell.getStringCellValue());
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					newCell.setCellValue(oldCell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_ERROR:
					newCell.setCellErrorValue(oldCell.getErrorCellValue());
					break;
				case Cell.CELL_TYPE_FORMULA:
					newCell.setCellFormula(oldCell.getCellFormula());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					newCell.setCellValue(oldCell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					newCell.setCellValue(oldCell.getRichStringCellValue());
					break;
				}
			}
			return newRow;
		} catch (Exception e) {
			log.debug("copyRow :" + e);
			return null;
		}
	}

}