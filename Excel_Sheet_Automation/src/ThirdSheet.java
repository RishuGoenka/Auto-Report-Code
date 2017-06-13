import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ThirdSheet {

	static final String REFRENCE = "Y:\\Release_Management\\release_artifacts\\ProductName_NN.NN.N.N_production_readiness.xlsx";
	static final String RESOLVED = "Resolved|Fixed|Closed";
	static final String NA = "Not Applicable";
	static final String TEAM = "To be fixed by support team";
	static final DateFormat DATEFORMAT = new SimpleDateFormat("yyyy/MM/dd");
	static Set<Integer> referenceContent = new LinkedHashSet<>();
	static LinkedList<BugStatus> sourceContent = new LinkedList<>();
	static final String INVALID = "InValid";
	static final Date INVALIDDATE = new Date(1990 / 01 / 01);
	static final String INVALIDREMARK = "InValid|NA|Not applicable|Done|Cannot be achieved|\\d+|\\s\\s";

	static Logger log = Logger.getLogger(ThirdSheet.class.getName());

	public static boolean thirdCheck(String sourcePath) {
		try {
			String product = sourcePath.split("\\\\")[2];
			getReferenceContent(product);
			getSourceContent(sourcePath, product);
			return verifyValue();
		} catch (Exception e) {
			log.error("Error while checking third Sheet"+ e);
			return false;
		}
	}

	private static boolean verifyValue() {
		try {
			boolean checkStatus = true;
			if (referenceContent.size() == sourceContent.size()) {
				for (BugStatus bug : sourceContent) {
					if (bug.getBugId().matches("[A-Z]+-\\d+")) {
						if ((bug.getStatus().matches(RESOLVED)) && !bug.getFixedIn().isEmpty()
								&& !bug.getFixedIn().matches(INVALID))
							log.info("Bug marked RESOLVED with Fixed In : " + bug.getFixedIn());
						else if (bug.getStatus().matches(NA) && !bug.getRemark().isEmpty()
								&& !bug.getRemark().matches(INVALIDREMARK))
							log.info("Bug marked NA with Remark : " + bug.getRemark());
						else if (bug.getStatus().matches(TEAM) && !bug.getRemark().isEmpty()
								&& !bug.getRemark().matches(INVALIDREMARK))
							log.info("Bug marked To be fixed with Team Name : " + bug.getRemark());
						else if (bug.getDate().after(presentDate()) && !bug.getRemark().isEmpty()
								&& !bug.getRemark().matches(INVALIDREMARK))
							log.info("Bug Remark : " + bug.getRemark());
						else {
							log.error("Bug Status not marked properly for Bug Id : " + bug.getBugId());
							checkStatus = false;
						}
					} else {
						log.error("In-Valid Bug Id : " + bug.getBugId());
						checkStatus = false;
					}
				}
				return checkStatus;
			} else {
				log.error("Some In Valid Modification is Been Done in the Third Sheet or duplicate bug id ");
				return false;
			}
		} catch (Exception e) {
			log.error("Error while verifying bug : " + e);
			return false;
		}
	}

	private static java.util.Date presentDate() {
		long millis = System.currentTimeMillis();
		return new Date(millis);
	}

	public static void getReferenceContent(String productName) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(REFRENCE)));
			Sheet datatypeSheet = workbook.getSheetAt(2);
			Iterator<Row> iterator = datatypeSheet.iterator();
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				if (currentRow.getCell(0).toString().equals(productName))
					referenceContent.add(currentRow.getRowNum());
			}
		} catch (Exception e) {
			log.debug("Error in Reference Third Sheet : " + e);
		}
	}

	public static void getSourceContent(String sourcePath, String productName) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(sourcePath)));
			Sheet datatypeSheet = workbook.getSheetAt(2);
			Iterator<Row> iterator = datatypeSheet.iterator();
			while (iterator.hasNext()) {
				BugStatus bugStatus = new BugStatus();
				Row currentRow = iterator.next();
				String product = currentRow.getCell(0).getStringCellValue();
				if (product.equals(productName)) {
					if (currentRow.getCell(6).getCellTypeEnum() == CellType.STRING)
						bugStatus.setBugId(currentRow.getCell(6).getStringCellValue());
					else
						bugStatus.setBugId(INVALID);
					if (currentRow.getCell(7).getCellTypeEnum() == CellType.STRING)
						bugStatus.setStatus(currentRow.getCell(7).getStringCellValue());
					else
						bugStatus.setStatus(INVALID);
					if (currentRow.getCell(8).getCellTypeEnum() == CellType.STRING)
						bugStatus.setFixedIn(currentRow.getCell(8).getStringCellValue());
					else
						bugStatus.setFixedIn(INVALID);
					if (currentRow.getCell(9).getCellTypeEnum() == CellType.STRING)
						bugStatus.setDate(DATEFORMAT.parse(currentRow.getCell(9).getStringCellValue()));
					else if (currentRow.getCell(9).getCellTypeEnum() == CellType.NUMERIC)
						bugStatus.setDate(currentRow.getCell(9).getDateCellValue());
					else
						bugStatus.setDate(INVALIDDATE);
					if (currentRow.getCell(10).getCellTypeEnum() == CellType.STRING)
						bugStatus.setRemark(currentRow.getCell(10).getStringCellValue());
					else
						bugStatus.setRemark(INVALID);
					sourceContent.add(bugStatus);
				}
			}
		} catch (Exception e) {
			log.error("Error in Source Third Sheet : " + e);
		}
	}
}
