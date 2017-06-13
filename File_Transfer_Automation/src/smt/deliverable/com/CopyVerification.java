package smt.deliverable.com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.apache.log4j.Logger;

public class CopyVerification {

	static Logger log = Logger.getLogger(CopyVerification.class.getName());
	
	public CopyVerification() {
		super();
	}

	/**
	 * 
	 * @param count
	 * @param sourcePath
	 * @param logFilePath
	 * @return true/false
	 */
	public int countVerify(String count, String sourcePath, String logFilePath) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFilePath, true))) {
			File sourceDir = new File(sourcePath);
			int counts = Integer.parseInt(count);
			bw.write("File count while Copying : " + counts);
			bw.newLine();
			int reCount = countFilesInDirectory(sourceDir, logFilePath);
			bw.write("File Re-Count after Copying : " + reCount);
			bw.newLine();
			bw.newLine();
			if (reCount == counts)
				return reCount;
			else
				return -1;
		} catch (Exception e) {
			log.info("Error while checking File Count", e);
			return -1;
		}
	}

	/**
	 * 
	 * @author rishu.goenka
	 * @param directory
	 * @param logFilePath
	 * @return
	 */

	public static int countFilesInDirectory(File directory, String logFilePath) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFilePath, true))) {
			int count = 0;
			for (File file : directory.listFiles()) {
				if (file.isFile()) {
					count++;
					bw.write(file.toString());
					bw.newLine();
				}
				if (file.isDirectory()) {
					count += countFilesInDirectory(file, logFilePath);
				}
			}
			return count;
		} catch (Exception e) {
			log.info("Error while checking File Count", e);
		}
		return 0;
	}

}
