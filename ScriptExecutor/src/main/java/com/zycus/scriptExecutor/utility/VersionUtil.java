package com.zycus.scriptExecutor.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zycus.scriptExecutor.enums.UpgradeType;
import com.zycus.scriptExecutor.exception.InvalidVersionDetailsException;
import com.zycus.scriptExecutor.logger.CustomLogger;

/**
 * Utility with everything to check sql script file versions.
 * 
 * @author harshvardhan.dudeja
 *
 */
public class VersionUtil {
	private static final CustomLogger LOGGER = CustomLogger.getInstance();

	/**
	 * Compares the lastUpdated Version of the Database with the the current
	 * version from the file, determines if current is higher than the last
	 * updated one, i.e. if the update should be carried out or not
	 * 
	 * @param lastUpdatedVersion
	 *            is the version stored in the database table
	 * @param currentVersion
	 *            is the version specified in the upgrade file
	 * @return true if current is higher than the last updated version, false
	 *         otherwise
	 * @throws InvalidVersionDetailsException
	 *             when the version in the upgrade script file is not specified
	 *             or not in the proper format.
	 */
	public static boolean isNewDBVerFound(String lastUpdatedVersion, String currentVersion)
			throws InvalidVersionDetailsException {
		List<String> dbVersion = new ArrayList<String>(Arrays.asList(lastUpdatedVersion.split("\\.")));
		List<String> curVersion = new ArrayList<String>(Arrays.asList(currentVersion.split("\\.")));

		if (dbVersion.size() < 5)
			dbVersion.add("00");
		else
			dbVersion.set(4, dbVersion.get(4).substring(1));

		if (curVersion.size() < 5)
			curVersion.add("00");
		else
			curVersion.set(4, curVersion.get(4).substring(1));

		for (int i = 0; i < 5; i++) {
			try {
				int dbVersionInt = Integer.parseInt(dbVersion.get(i));
				int curVersionInt = Integer.parseInt(curVersion.get(i));
				if (dbVersionInt < curVersionInt) {
					LOGGER.info("Current script not executed before");
					return true;
				} else if (dbVersionInt > curVersionInt) {
					LOGGER.info("A script with higher version already updated before.");
					LOGGER.info("exiting");
					return false;
				}
			} catch (Exception e) {
				throw new InvalidVersionDetailsException("invalid database version format.", e);
			}
		}
		LOGGER.info("The version specified in the script file was already updated in the Database, exiting....");
		return false;
	}

	/**
	 * Parse the file in the current path to obtain the version specified in the
	 * file
	 * 
	 * @param scriptFile
	 *            if the script file to be executed
	 * @return version specified in the file
	 * @throws IOException
	 *             if any problem occurs while reading the file
	 * @throws InvalidVersionDetailsException
	 *             if version specified is not in the proper format or not
	 *             specified
	 */
	public static String getCurrentVersion(File scriptFile) throws IOException, InvalidVersionDetailsException {
		try {
			String version = null;
			BufferedReader br = new BufferedReader(new FileReader(scriptFile));
			String line = br.readLine();
			br.close();
			int i = 10;
			while (line.charAt(i) != '*') {
				i++;
			}
			version = line.substring(10, i);

			try {
				version = checkVersion(version);
			} catch (Exception e) {
				throw new InvalidVersionDetailsException(e);
			}
			return version;
		} catch (Exception e) {
			throw new InvalidVersionDetailsException(e);
		}

	}

	private static String checkVersion(String version) throws InvalidVersionDetailsException {
		String[] split = version.split("\\.");
		for (int i = 0; i < split.length; i++) {
			try {
				if (i != 4)
					Long.parseLong(split[i]);
			} catch (ClassCastException e) {
				throw new InvalidVersionDetailsException(e);
			}
		}
		return version;
	}

	public static String getUpgradeType(String currentVersion) {
		String[] versions = currentVersion.split("\\.");
		if (versions.length > 4)
			return versions[versions.length - 1].substring(0, 1);
		else
			return UpgradeType.Release.getValue();
	}

	public static String getProperFormat(String version) {
		StringBuilder result = new StringBuilder();
		List<String> versions = new ArrayList<String>(Arrays.asList(version.split("\\.")));
		for (int i = 0; i < 4; i++) {
			if (versions.get(i).length() == 2) {
				result.append(versions.get(i)).append(".");
				continue;
			} else if (versions.get(i).length() == 1)
				result.append("0" + versions.get(i)).append(".");
		}
		if (versions.size() == 5) {
			String upgradeType = versions.get(4).substring(0, 1);
			String no = versions.get(4).substring(1);
			if (no.length() == 1)
				result.append(upgradeType + "0" + no).append(".");
			else {
				result.append(versions.get(4)).append(".");
			}
		}
		return result.deleteCharAt(result.length() - 1).toString();
	}

}
