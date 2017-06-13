import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FirstSheet {

	static final String REFRENCE = "Y:\\Release_Management\\release_artifacts\\ProductName_NN.NN.N.N_production_readiness.xlsx";
	static LinkedList<String> referenceContent = new LinkedList<>();
	static LinkedList<String> sourceContent = new LinkedList<>();

	static Logger log = Logger.getLogger(FirstSheet.class.getName());

	/*
	 * Check for the revision history in the Sheet. 
	 */
	public static boolean firstCheck(String sourcePath) {
		try {
			int referenceCount = getReferenceContent();
			int sourceCount = getSourceContent(sourcePath);
			if (sourceCount == -1) {
				log.error("Unlabe to read File : Either corrupted or Missing");
				return false;
			} else if (sourceCount == referenceCount && referenceContent.size() == sourceContent.size()
					&& referenceContent.containsAll(sourceContent)) {
				log.info("Successfully passed the First Sheet Test..!!");
				return true;
			} else {
				Iterator<String> refernce = referenceContent.iterator();
				Iterator<String> source = sourceContent.iterator();
				int count = 0;
				while (refernce.hasNext() && source.hasNext()) {
					if (!refernce.next().equals(source.next())) {
						// log File
						log.error("Reference Content : " + referenceContent.get(count));
						log.error("Source Content : " + sourceContent.get(count));
					}
					count++;
				}
				log.error("Using older version of the Production Rediness Sheet");
				return false;
			}
		} catch (Exception e) {
			log.debug("Error while verifying First Sheet : " + e);
			return false;
		}
	}

	public static int getReferenceContent() {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(REFRENCE)));
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					if (currentCell.getCellTypeEnum() == CellType.STRING) {
						referenceContent.add(currentCell.getStringCellValue());
					} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						Double value = currentCell.getNumericCellValue();
						referenceContent.add(value.toString());
					}
				}
			}
			return workbook.getNumberOfSheets();
		} catch (Exception e) {
			log.debug("Error in Reference First Sheet : " + e);
			return -1;
		}

	}

	public static int getSourceContent(String sourcePath) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(sourcePath)));
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					if (currentCell.getCellTypeEnum() == CellType.STRING) {
						sourceContent.add(currentCell.getStringCellValue());
					} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						Double value = currentCell.getNumericCellValue();
						sourceContent.add(value.toString());
					}
				}
			}
			return workbook.getNumberOfSheets();
		} catch (Exception e) {
			log.error("Error in Source First Sheet : " + e);
			return -1;
		}
	}
}
