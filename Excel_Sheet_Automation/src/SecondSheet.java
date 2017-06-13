import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SecondSheet {

	static final String REFRENCE = "Y:\\Release_Management\\release_artifacts\\ProductName_NN.NN.N.N_production_readiness.xlsx";
	static Map<Coordinate, String> referenceContent = new LinkedHashMap<>();
	static Map<Coordinate, String> sourceContent = new LinkedHashMap<>();
	static final DateFormat DATEFORMAT = new SimpleDateFormat("yyyy/MM/dd");
	static final String AchievedValue = "10.0|YES";
	static final String UnachievedValue = "[0-9]\\.\\d+|NO";
	static final String NAValue = "NA";
	static final String INVALID = "InValid";
	static final Date INVALIDDATE = new Date(1990 / 01 / 01);
	static final String INVALIDREMARK = "InValid|NA|Not applicable|Done|Cannot be achieved|\\d+|\\s\\s";
	static final Coordinate ACHIEVEDSCORE = new Coordinate(108,7);
	static final int READINESSPOINTAXIS = 1;
	static final int APPROVINGAUTHORITYAXIS = 8;

	static Logger log = Logger.getLogger(SecondSheet.class.getName());

	public static boolean secondCheck(String sourcePath) {
		try {
			getReferenceContent();
			getSourceContent(sourcePath);
			return checkContent();
		} catch (Exception e) {
			log.debug("Error while checking Second Sheet : " + e);
			return false;
		}
	}

	public static void getReferenceContent() {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(REFRENCE)));
			Sheet datatypeSheet = workbook.getSheetAt(1);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			Iterator<Row> iterator = datatypeSheet.iterator();
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				while (cellIterator.hasNext()) {
					Coordinate coordinate = new Coordinate();
					Cell currentCell = cellIterator.next();
					coordinate.setXaxis(currentCell.getRowIndex());
					coordinate.setYaxis(currentCell.getColumnIndex());
					handelReferenceCell(coordinate, evaluator, currentCell);
				}
			}
		} catch (Exception e) {
			log.debug("Error in Reference Second Sheet : " + e);
		}
	}

	private static void handelReferenceCell(Coordinate coordinate, FormulaEvaluator evaluator, Cell currentCell) {
		try {
			if (currentCell.getCellTypeEnum() == CellType.STRING) {
				referenceContent.put(coordinate, currentCell.getStringCellValue());
			} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
				if (currentCell.getColumnIndex() == 9) {
					Date date = currentCell.getDateCellValue();
					referenceContent.put(coordinate, DATEFORMAT.format(date));
				} else {
					Double value = currentCell.getNumericCellValue();
					referenceContent.put(coordinate, value.toString());
				}
			} else if (currentCell.getCellTypeEnum() == CellType.FORMULA) {
				Double value = evaluator.evaluate(currentCell).getNumberValue();
				if (coordinate.getYaxis() == 7) {
					// No need to Store the Value
				} else {
					referenceContent.put(coordinate, value.toString());
				}
			}
		} catch (Exception e) {
			log.debug("Error While Handling the Cell Coordinate in Reference: " + coordinate.toString());
			log.debug("Error is : " + e);
		}
	}

	private static void getSourceContent(String sourcePath) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(sourcePath)));
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			Sheet datatypeSheet = workbook.getSheetAt(1);
			Iterator<Row> iterator = datatypeSheet.iterator();
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				while (cellIterator.hasNext()) {
					Coordinate coordinate = new Coordinate();
					Cell currentCell = cellIterator.next();
					coordinate.setXaxis(currentCell.getRowIndex());
					coordinate.setYaxis(currentCell.getColumnIndex());
					handelSourceCell(coordinate, evaluator, currentCell);
				}
			}
		} catch (Exception e) {
			log.error("Error in Source Second Sheet : " + e);
		}
	}

	private static void handelSourceCell(Coordinate coordinate, FormulaEvaluator evaluator, Cell currentCell) {
		try {
			if (currentCell.getCellTypeEnum() == CellType.STRING) {
				sourceContent.put(coordinate, currentCell.getStringCellValue());
			} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
				if (currentCell.getColumnIndex() == 9) {
					Date date = currentCell.getDateCellValue();
					sourceContent.put(coordinate, DATEFORMAT.format(date));
				} else {
					Double value = currentCell.getNumericCellValue();
					sourceContent.put(coordinate, value.toString());
				}
			} else if (currentCell.getCellTypeEnum() == CellType.FORMULA) {
				Double value = evaluator.evaluate(currentCell).getNumberValue();
				sourceContent.put(coordinate, value.toString());
			}
		} catch (Exception e) {
			log.error("Error While Handling the Cell Coordinate in Source: " + coordinate.toString());
			log.error("Error is : " + e);
		}
	}

	private static boolean checkContent() {
		try {
			boolean checkStatus = true;
			for (Map.Entry<Coordinate, String> value : referenceContent.entrySet()) {
				Coordinate coordinate = new Coordinate();
				coordinate = value.getKey();
				String refrenceValue = referenceContent.get(coordinate);
				String sourceValue = sourceContent.get(coordinate);
				int yaxis = coordinate.getYaxis();
				if (refrenceValue.contentEquals(sourceValue) && yaxis != 6) {
					// Correct Content No Action Taken
				} else if (yaxis == 6) {
					if (sourceValue.contains("Achieved") || sourceValue.contains("Score")) {
						// No Action Needed
					} else {
						checkStatus = achievedValueCheck(coordinate, checkStatus);
					}
				} else {
					log.error(coordinate.toString());
					log.error("Reference Value : " + refrenceValue);
					log.error("Source Value : " + sourceValue);
					checkStatus = false;
				}
			}
			log.info("Achieved Score : " + sourceContent.get(ACHIEVEDSCORE));
			System.out.println("Achieved Score : " + sourceContent.get(ACHIEVEDSCORE));
			return checkStatus;
		} catch (Exception e) {
			log.error("Error while Checking Content" + e);
			return false;
		}
	}

	private static boolean achievedValueCheck(Coordinate coordinate, boolean checkStatus) {
		try {
			int xaxis = coordinate.getXaxis();
			int yaxis = coordinate.getYaxis();
			Date block;
			Coordinate blockDateCoordinate = new Coordinate(xaxis, yaxis + 3);
			Coordinate remarkCoordinate = new Coordinate(xaxis, yaxis + 4);
			String achieved = sourceContent.get(coordinate);
			String blockDate = sourceContent.get(blockDateCoordinate);
			String remark = sourceContent.get(remarkCoordinate);

			if (blockDate == null)
				block = INVALIDDATE;
			else
				block = DATEFORMAT.parse(blockDate);

			if (achieved.matches(AchievedValue)) {
				// No Logging required
			} else if (achieved.matches(NAValue) && !remark.matches(INVALIDREMARK)) {
				log.info("Achieved value marked NA with remark : " + remark);
			} else if (achieved.matches(UnachievedValue) && block.after(presentDate())
					&& !remark.matches(INVALIDREMARK)) {
				log.info("Achieved value marked : " + achieved);
				log.info("Block Date : " + block);
				log.info("Remark : " + remark);
			} else {
				log.error("Error for the point : " + referenceContent.get(new Coordinate(coordinate.getXaxis() , READINESSPOINTAXIS)));
				log.error("Approving Authority : " + referenceContent.get(new Coordinate(coordinate.getXaxis() , APPROVINGAUTHORITYAXIS)));
				log.error("Achieved Marked Value : " + achieved);
				log.error("Not Proper Block Date : " + block + " Default date : Thu Jan 01 05:30:01 IST 1970");
				log.error("Not Proper Remark : " + remark);
				checkStatus = false;
			}
			return checkStatus;
		} catch (Exception e) {
			log.error("Exception for the coordinate : " + coordinate.toString());
			log.error("Error for the point : " + sourceContent.get(new Coordinate(coordinate.getXaxis() , 1)));
			log.error("Error while Checking Achieved value : " + e);
			return false;
		}
	}

	private static java.util.Date presentDate() {
		long millis = System.currentTimeMillis();
		return new Date(millis);
	}
}
