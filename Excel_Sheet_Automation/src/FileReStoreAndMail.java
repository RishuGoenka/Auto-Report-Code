import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import org.apache.log4j.Logger;

/**
 * 
 * @author rishu.goenka
 * 
 */

public class FileReStoreAndMail {

	static Logger log = Logger.getLogger(FileReStoreAndMail.class.getName());

	static final String READINESSLOG = "R:\\SMT_Release_For_RM_Review\\SMT_Release_Delivery_Log\\readinesslogs.log";
	static final String READINESSLOGPATH = "R:\\SMT_Release_For_RM_Review\\SMT_Release_Delivery_Log\\ReadinessLog";

	static final String ActiveMQ_Cluster = "SMTRM@zycus.com";
	static final String ActiveMQ_Monitor = "SMTRM@zycus.com";
	static final String Auto_Restart_Monitor = "SMTRM@zycus.com";
	static final String CMD = "Team_CMD@zycus.com";
	static final String CNS = "GD-IT@zycus.com";
	static final String CRMS = "Team_Galaxy@zycus.com";
	static final String Customer_Branding = "GD-IT@zycus.com";
	static final String Dashboard = "GD-IT@zycus.com";
	static final String eCatalog = "team_eproc@zycus.com";
	static final String eInvoice = "Team_eInvoice@zycus.com";
	static final String eProc = "team_eproc@zycus.com";
	static final String FieldLibrary = "Team_FL@zycus.com";
	static final String Flexiform = "Team_FL@zycus.com";
	static final String Forum = "GD-IT@zycus.com";
	static final String Framework = "Team_Framework@zycus.com";
	static final String FTE = "pdt-integration@zycus.com";
	static final String iAnalyze = "team_nova@zycus.com";
	static final String iConsole = "pdt-integration@zycus.com ";
	static final String iContract = "team_cobra@zycus.com";
	static final String iCost = "Team_Pgm_Management@zycus.com";
	static final String iManage = "Team_Pgm_Management@zycus.com";
	static final String iMine = "Team_iRequest@zycus.com";
	static final String iMobile = "Team_OneView@zycus.com";
	static final String iMonitor = "Team_iRequest@zycus.com";
	static final String iNotify = "Team_iRequest@zycus.com";
	static final String Integration = "pdt-integration@zycus.com ";
	static final String IntegrationPlatform = "pdt-integration@zycus.com";
	static final String iRequest = "Team_iRequest@zycus.com";
	static final String iSave = "Team_Pgm_Management@zycus.com";
	static final String iSource = "team_sting@zycus.com";
	static final String Memcached = "SMTRM@zycus.com";
	static final String MongoDB = "SMTRM@zycus.com";
	static final String Notifier_Engine = "SMTRM@zycus.com";
	static final String OneView = "Team_OneView@zycus.com";
	static final String QuickSearch = "team_nova@zycus.com";
	static final String Rainbow = "Team_Rainbow@zycus.com";
	static final String Redis = "SMTRM@zycus.com";
	static final String Redis_Cluster = "SMTRM@zycus.com";
	static final String Reporting = "SMTRM@zycus.com";
	static final String rsync = "SMTRM@zycus.com";
	static final String SIM = "team_sim@zycus.com";
	static final String SMT_Common = "SMTRM@zycus.com";
	static final String SolrZookeeper_Cluster = "SMTRM@zycus.com";
	static final String SpendDashboard = "team_nova@zycus.com";
	static final String SPM = "team_spm@zycus.com";
	static final String SSO_Bridge = "SSO-DEV@zycus.com";
	static final String Supplier_Portal = "Team_ZSN@zycus.com";
	static final String TMS = "team_sso@zycus.com";
	static final String Transformation_Engine = "pdt-integration@zycus.com";
	static final String Workflow = "Team_workflow@zycus.com";
	static final String ZCS = "Team_workflow@zycus.com";
	static final String ZDocManager = "pdt-integration@zycus.com";
	static final String ZSN = "Team_ZSN@zycus.com";
	static final String ZygrateSecurity = "pdt-integration@zycus.com";
	static final String Zytrack = "team_sso@zycus.com";

	public FileReStoreAndMail() {
		super();
	}

	/**
	 * 
	 * @param args
	 */
	public static void FileStoreAndMail(String sourcePath, boolean status) {

		try {
			SendMail mail = new SendMail();

			// Destination Mail ID
			FileReStoreAndMail filerestoreAndmail = new FileReStoreAndMail();
			String productName = sourcePath.split("\\\\")[2].trim();
			String mailto = "SMTRM@zycus.com";

			// Mail Id Check using Reflection
			Field[] fields = FileReStoreAndMail.class.getDeclaredFields();
			for (Field field : fields) {
				if (field.getName().equals(productName)) {
					Object object = field.get(filerestoreAndmail);
					mailto = object.toString();
				}
			}
			mail.mailReport(mailto, READINESSLOG, sourcePath, status);
			storeFile(productName);
			System.out.println("Process Completed....!!");
		} catch (Exception e) {
			log.debug("FileStoreAndMail method Error : ", e);
		}
	}

	private static void storeFile(String productName) {
		try {

			InputStream inStream = null;
			OutputStream outStream = null;
			
			String millis = Long.toString(System.currentTimeMillis());
			
			File afile = new File(READINESSLOG);
			File bfile = new File(READINESSLOGPATH + "\\" + productName + "_" + millis + ".log");

			inStream = new FileInputStream(afile);
			outStream = new FileOutputStream(bfile);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {

				outStream.write(buffer, 0, length);

			}

			inStream.close();
			outStream.close();

			System.out.println("File is copied successful!");

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
