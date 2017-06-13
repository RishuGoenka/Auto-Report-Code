package smt.deliverable.com;

import java.io.File;
import org.apache.log4j.Logger;

public class PathVerification {

	static Logger log = Logger.getLogger(PathVerification.class.getName());

	static final String RELEASE = "release_\\d\\d.\\d\\d.\\d.\\d";
	static final String PATCHS = "patches";
	static final String PATCH = "patch_\\d\\d";
	static final String UTILITYS = "utilities";
	static final String UTILITY = "utility";
	static final String CSCRS = "customer_specific_change_requests";
	static final String CSCR = "CSCR_\\d\\d";
	static final String SMT = "SMT_Release_For_RM_Review";

	public PathVerification() {
		super();
	}

	/**
	 * 
	 * @author rishu.goenka
	 * @param source
	 *            It take deliverable path as input and check the provided path
	 *            is correct or not
	 * @return true/false
	 */
	public static boolean validatePath(String source) {
		try {
			String[] sourcePath = source.split("\\\\");
			int sourcePathLength = sourcePath.length;
			if (sourcePath[0].contentEquals("R:") && sourcePath[1].contentEquals(SMT)
					&& new File(source).isDirectory()) {
				switch (sourcePathLength) {
				case 4:
					return sourcePath[sourcePathLength - 1].matches(RELEASE);
				case 6:
					return sourcePath[sourcePathLength - 3].matches(RELEASE)
							&& (sourcePath[sourcePathLength - 2].contentEquals(PATCHS)
									&& sourcePath[sourcePathLength - 1].matches(PATCH))
							|| (sourcePath[sourcePathLength - 2].contentEquals(UTILITYS)
									&& sourcePath[sourcePathLength - 1].split("_")[0].contentEquals(UTILITY))
							|| ((sourcePath[sourcePathLength - 2].contentEquals(CSCRS))
									&& sourcePath[sourcePathLength - 1].matches(CSCR));
				default:
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			log.info("Error while validating path : ", e);
			return false;
		}
	}
}
