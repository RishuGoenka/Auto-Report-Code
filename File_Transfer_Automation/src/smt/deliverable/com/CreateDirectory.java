package smt.deliverable.com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import org.apache.log4j.Logger;

public class CreateDirectory {

	static Logger log = Logger.getLogger(CreateDirectory.class.getName());

	public CreateDirectory() {
		super();
	}

	/**
	 * 
	 * @author rishu.goenka
	 * @param source
	 *            It take destination path as input and create directory
	 * @return true/false
	 */
	public static boolean createDir(String client) {
		File filePath = new File(client);
		if (!filePath.exists())
			filePath.mkdirs();
		else {
			cleanFolder(filePath);
			filePath.mkdirs();
		}
		return filePath.exists();
	}

	/**
	 * 
	 * @param logPath
	 * @return file Path
	 */
	public static String logFileCreation(String logPath) {
		BufferedWriter bufferedWriter = null;
		FileWriter fileWrite = null;
		String[] logPaths = logPath.split("\\\\");
		int logPathLength = logPaths.length;
		String logFile = logPath + "\\" + logPaths[logPathLength - 1] + ".txt";

		try {
			File file = new File(logFile);
			// if file doesn't exists, then create it
			if (!file.exists())
				file.createNewFile();

			// true means append file content
			fileWrite = new FileWriter(file.getAbsoluteFile(), true);
			bufferedWriter = new BufferedWriter(fileWrite);

			bufferedWriter.write("--------------------------------!!---START---!!--------------------------------");
			bufferedWriter.newLine();
			bufferedWriter.newLine();
			
			bufferedWriter.close();
			fileWrite.close();
		} catch(Exception e) {
			log.info("Log File Creation Error : ", e);
		}
		return logFile;
	}

	public static void cleanFolder(File file) {
		try{
		if (file.isDirectory()) {
			// directory is empty, then delete it
			if (file.list().length == 0)
				file.delete();
			else {
				// list all the directory contents
				String files[] = file.list();
				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);
					// recursive delete
					cleanFolder(fileDelete);
				}
				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
				}
			}
		} else {
			file.delete();
		}
		}catch(Exception e){
			log.info("Cleaning Folder Error : ", e);
		}
	}

}
