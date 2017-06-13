package smt.deliverable.com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class CopyDeliverable {
	
	static Logger log = Logger.getLogger(CopyDeliverable.class.getName());

	static final String CLIENT_PATH = "R:\\Client-Release-SMT";
	static final String RELEASEDELIVERABLE = "\\upgrade";
	
	public CopyDeliverable() {
		super();
	}

	/**
	 * @author rishu.goenka
	 * @param sourcepath
	 * @param clientPath
	 * @param logFilePath
	 * @return size is source and destination
	 */
	public String sourceCopy(String sourcepath, String clientPath, String logFilePath) {

		// store complete list of Source Path
		ArrayList<String> srcPath = new ArrayList<>();

		// store complete list of Destination Path
		ArrayList<String> destPath = new ArrayList<>();

		String destnationIS;
		if (clientPath.contains(CLIENT_PATH)){
			destnationIS = "Client";
		}else{
			destnationIS = "Turbatio";
			if(sourcepath.split("\\\\").length == 4){
				sourcepath = sourcepath + RELEASEDELIVERABLE;
				clientPath = clientPath + RELEASEDELIVERABLE;
			}
		}
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFilePath, true))) {
			File srcSMT = new File(sourcepath);
			File destClient = new File(clientPath);
			bw.write("Destiantion is: " + destnationIS);
			bw.newLine();
			// copy the file from source to destination
			copy(srcSMT, destClient, srcPath, destPath);
			bw.write("Number of File's in Source : " + srcPath.size());
			bw.newLine();
			bw.write("Number of Copyed File's in " + destnationIS + " : " + destPath.size());
			bw.newLine();
			bw.newLine();
			bw.write("Deliverable Copy Completed");
			bw.newLine();
			bw.newLine();
		} catch (Exception e) {
			log.info("Error In SourceCopy Method : ", e);
		}
		return srcPath.size() + "@" + destPath.size();
	}

	/**
	 * 
	 * @author rishu.goenka
	 * @param src
	 * @param dest
	 * @param srcPath
	 * @param destPath
	 */
	public static void copy(File src, File dest, ArrayList<String> srcPath, ArrayList<String> destPath) {
		try {
			if (src.isDirectory()) {
				// if directory not exists, create it
				if (!dest.exists()) {
					dest.mkdirs();
				}

				// list all the directory contents
				String[] files = src.list();
				for (String file : files) {
					// construct the src and dest file structure
					File srcFile = new File(src, file);
					File destFile = new File(dest, file);
					// recursive copy
					copy(srcFile, destFile, srcPath, destPath);
				}
			} else {
				// if file, then copy it
				InputStream in = new FileInputStream(src);
				OutputStream out = new FileOutputStream(dest);

				// store source file path
				srcPath.add(src.getAbsolutePath());
				// Use bytes stream to support all file types
				byte[] buffer = new byte[1024];

				int length;
				// copy the file content in bytes
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}

				in.close();
				out.close();

				// store destination file path
				destPath.add(dest.getAbsolutePath());
			}
		} catch (Exception e) {
			log.info("Error while File Copy", e);
		}
	}
}
