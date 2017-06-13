import java.io.File;

import org.apache.log4j.Logger;

public class SheetCheck {

	static Logger log = Logger.getLogger(SheetCheck.class.getName());

	public static void main(String[] args) {
		try {
			String sourcePath = args[0];
			sourcePath = readinessPath(sourcePath);
			boolean status = false;
			System.out.println(sourcePath);
			if (new File(sourcePath).exists()) {
				log.info(sourcePath);
				boolean first = FirstSheet.firstCheck(sourcePath);
				boolean second = SecondSheet.secondCheck(sourcePath);
				boolean third = ThirdSheet.thirdCheck(sourcePath);
				if (first && second && third) {
					System.out.println("Readiness Sheet Succesfully Passed Standard..!!");
					status = true;
				} else {
					log.info("First Sheet Status : " + first);
					log.info("Second Sheet Status : " + second);
					log.info("Third Sheet Status : " + third);
					System.out.println("first : " + first + "  second : " + second + "  third : " + third);
					System.out.println("Readiness Sheet Failed Standard..!!");
				}
			} else {
				log.error(sourcePath);
				log.error("File not exist : Either naming standeard not followed or File Missing");
			}
			log.info("----------------------------------END----------------------------------");
			FileReStoreAndMail.FileStoreAndMail(sourcePath, status);
		} catch (Exception e) {
			log.debug("Error in main Method : " + e);
		}
	}

	private static String readinessPath(String sourcePath) {
		try {
			String[] path = sourcePath.split("\\\\");
			int length = path.length;
			return sourcePath + "\\" + path[length - 2] + "_" + path[length - 1].split("_")[1]
					+ "_production_readiness.xlsx";
		} catch (Exception e) {
			log.debug("While Creating Rediness path : " + e);
			return "unable to create path";
		}
	}

}
