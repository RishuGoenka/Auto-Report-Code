package smt.deliverable.com;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.reflect.Field;
import org.apache.log4j.Logger;

/**
 * 
 * @author rishu.goenka
 * 
 *         It takes the Deliverable path as a input String, Create the required
 *         folder structure for file and logs Copy the file to Client Folder and
 *         Turbatio, Check the Copy deliverable Mail to the Product team
 */

public class Deliverable {

	static Logger log = Logger.getLogger(Deliverable.class.getName());

	static final String SMT_PATH = "R:\\SMT_Release_For_RM_Review";
	static final String CLIENT_PATH = "R:\\Client-Release-SMT";
	static final String TURBATIO_PATH = "\\\\192.168.4.100\\Client-Releases-SMT";
	static final String LOG_PATH = "R:\\SMT_Release_For_RM_Review\\SMT_Release_Delivery_Log";
	static final String BASICLOG_PATH = "R:\\SMT_Release_For_RM_Review\\SMT_Release_Delivery_Log\\basiclog.txt";

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

	public Deliverable() {
		super();
	}

	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(BASICLOG_PATH, true))) {
			PathVerification valid = new PathVerification();
			CreateDirectory create = new CreateDirectory();
			CopyDeliverable copy = new CopyDeliverable();
			CopyVerification verify = new CopyVerification();
			SendMail mail = new SendMail();

			// Deliverable Path
			String sourcePath = args[0];
			bw.write("----------------------------!!---START---!!----------------------------");
			bw.newLine();
			System.out.println("Process Started....!!");

			// Destination Mail ID
			Deliverable deliverable = new Deliverable();
			String productName = sourcePath.split("\\\\")[2].trim();
			String mailto = "SMTRM@zycus.com";

			// Mail Id Check using Reflection
			Field[] fields = Deliverable.class.getDeclaredFields();
			for (Field field : fields) {
				if (field.getName().equals(productName)) {
					Object object = field.get(deliverable);
					mailto = object.toString();
				}
			}

			// verify Source Path
			if (valid.validatePath(sourcePath)) {

				// client Directory Creation
				String clientPath = sourcePath.replace(SMT_PATH, CLIENT_PATH);
				System.out.println("Client Directory Created : " + create.createDir(clientPath));

				// turbatio Directory Creation
				String turbatioPath = sourcePath.replace(SMT_PATH, TURBATIO_PATH);
				System.out.println("Turbatio Directory Created : " + create.createDir(turbatioPath));

				// log Directory Creation
				String logPath = sourcePath.replace(SMT_PATH, LOG_PATH);
				System.out.println("Log Directory Created : " + create.createDir(logPath));

				// create log File for the Deliverable Path
				String logFilePath = create.logFileCreation(logPath);

				// copy to Client
				String[] clientCopyCount = copy.sourceCopy(sourcePath, clientPath, logFilePath).split("@");

				// copy to Turbatio
				String[] turbatioCopyCount = copy.sourceCopy(sourcePath, turbatioPath, logFilePath).split("@");

				int sourceCount = verify.countVerify(clientCopyCount[0], sourcePath, logFilePath);
				int clientCount = verify.countVerify(clientCopyCount[1], clientPath, logFilePath);
				int turbatioCount = verify.countVerify(turbatioCopyCount[1], turbatioPath, logFilePath);
				if (clientCount != -1 && sourceCount == clientCount && turbatioCount != -1) {
					System.out.println("Total no of file copied : " + clientCount);
					System.out.println("Deliverable Copy Succesfull..!!");
					bw.write("Total no of file copied : " + clientCount);
					bw.newLine();
					bw.write("Deliverable Copy Succesfull..!! ");
					bw.newLine();

				} else {
					System.out.println("Total no of file in source : " + sourceCount);
					System.out.println("Total no of file in Client : " + clientCount);
					System.out.println("Total no of file in turbatio : " + turbatioCount);
					System.out.println("Some file might be missing");
					bw.write("Total no of file in source : " + sourceCount);
					bw.newLine();
					bw.write("Total no of file in Client : " + clientCount);
					bw.newLine();
					bw.write("Total no of file in turbatio : " + turbatioCount);
					bw.newLine();
					bw.write("Some file might be missing");
					bw.newLine();
				}
				System.out.println("Log Path : " + logFilePath);
				System.out.println("Client Path : " + clientPath);
				mail.mailReport(mailto, logFilePath, sourcePath);
				bw.write("Log Path : " + logFilePath);
				bw.newLine();
				bw.write("Client Path : " + clientPath);
				bw.newLine();
				bw.newLine();
			} else {
				System.out.println("The provided source path is In-Valid..!!");
				mail.mailReport(mailto, "NotValid", sourcePath);
				bw.write(" The provided source path is In-Valid ");
				bw.newLine();
				bw.write(" The Source Path : " + sourcePath);
				bw.newLine();
				bw.newLine();
			}
			System.out.println("Process Completed....!!");
		} catch (Exception e) {
			log.info("Deliverbale Main Error : ", e);
		}
	}
}
