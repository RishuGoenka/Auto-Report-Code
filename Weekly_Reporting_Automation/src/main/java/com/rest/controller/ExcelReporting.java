package com.rest.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import com.jenkins.reporter.FinalReport;

public class ExcelReporting {

	public static Logger log = Logger.getLogger(ExcelReporting.class.getName());
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
			System.out.println("createBulkExcelSheet : Somthing is wrong");
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
				return finalReport.getBuildRepo() == null ? "-" : finalReport.getBuildRepo();
			case 3:
				return finalReport.getBuildBranch() == null ? "-" : finalReport.getBuildBranch();
			case 4:
				return finalReport.getBuildGeneratedBy() == null ? "-" : finalReport.getBuildGeneratedBy();
			case 5:
				return finalReport.getBuildStartDate() == null ? "-" : finalReport.getBuildStartDate().toString();
			case 6:
				return finalReport.getBuildDuration() == null ? "-" : finalReport.getBuildDuration();
			case 7:
				return finalReport.getBuildStatus() == null ? "-" : finalReport.getBuildStatus();
			case 8:
				return finalReport.getDestination_path() == null ? "-" : finalReport.getDestination_path();
			case 9:
				return finalReport.getDestination_server() == null ? "-" : finalReport.getDestination_server();
			case 10:
				return finalReport.getDeploymentStatus() == null ? "-" : finalReport.getDeploymentStatus();
			case 11:
				return finalReport.getDeploymentStartDate() == null ? "-"
						: finalReport.getDeploymentStartDate().toString();
			case 12:
				return finalReport.getDeploymentDuration() == null ? "-" : finalReport.getDeploymentDuration();
			case 13:
				return finalReport.getTotal_tc() == null ? "-" : finalReport.getTotal_tc();
			case 14:
				return finalReport.getPassed_tc() == null ? "-" : finalReport.getPassed_tc();
			case 15:
				return finalReport.getFailed_tc() == null ? "-" : finalReport.getFailed_tc();
			case 16:
				return finalReport.getSkipped_tc() == null ? "-" : finalReport.getSkipped_tc();
			case 17:
				return finalReport.getTestDuration() == null ? "-" : finalReport.getTestDuration();
			case 18:
				return finalReport.getTestStatus() == null ? "-" : finalReport.getTestStatus();
			case 19:
				return finalReport.getTesting_type() == null ? "-" : finalReport.getTesting_type();
			case 20:
				return finalReport.getqADeploymentStatus() == null ? "-" : finalReport.getqADeploymentStatus();
			default:
				return null;
			}
		} catch (Exception e) {
			log.debug("pickCellData :" + e);
			System.out.println("pickCellData : Somthing is wrong");
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
			System.out.println("copyRow : Somthing is wrong");
			return null;
		}
	}

}
