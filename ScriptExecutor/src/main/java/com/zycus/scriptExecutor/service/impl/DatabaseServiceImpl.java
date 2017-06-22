package com.zycus.scriptExecutor.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import com.zycus.scriptExecutor.bo.ConnectionDetails;
import com.zycus.scriptExecutor.constants.Constants;
import com.zycus.scriptExecutor.databaseAPI.DatabaseConnectionAPI;
import com.zycus.scriptExecutor.enums.UpgradeType;
import com.zycus.scriptExecutor.exception.InvalidApplicationServerPathException;
import com.zycus.scriptExecutor.exception.InvalidConnectionDetailsInFileException;
import com.zycus.scriptExecutor.exception.InvalidDatabasePasswordException;
import com.zycus.scriptExecutor.exception.InvalidDriverNameException;
import com.zycus.scriptExecutor.exception.InvalidUrlException;
import com.zycus.scriptExecutor.exception.InvalidVersionDetailsException;
import com.zycus.scriptExecutor.exception.InvalidVersionDetailsInDBException;
import com.zycus.scriptExecutor.exception.SQLScriptExecutionException;
import com.zycus.scriptExecutor.logger.CustomLogger;
import com.zycus.scriptExecutor.service.DatabaseService;
import com.zycus.scriptExecutor.service.FileService;
import com.zycus.scriptExecutor.service.TurbatioService;
import com.zycus.scriptExecutor.service.factory.ServiceFactory;
import com.zycus.scriptExecutor.utility.PropertiesUtil;
import com.zycus.scriptExecutor.utility.SQLScriptExecutor;
import com.zycus.scriptExecutor.utility.UpdateEnum;
import com.zycus.scriptExecutor.utility.VersionUtil;

public class DatabaseServiceImpl implements DatabaseService {

	private final CustomLogger LOGGER = CustomLogger.getInstance();

	@Override
	public List<ConnectionDetails> readDatabaseConnectionDetails(String appServerPath, String dbPasswordsInJSon)
			throws IOException, InvalidUrlException, InvalidConnectionDetailsInFileException,
			InvalidDriverNameException, NumberFormatException, InvalidDatabasePasswordException,
			InvalidApplicationServerPathException, Exception {
		if (isTurbatioConfigured()) {
			TurbatioService turbatioService = ServiceFactory.getTurbatioService();
			String turbatoURL = getTurbatoURL(appServerPath);
			if (dbPasswordsInJSon == null)
				throw new InvalidDatabasePasswordException(
						"Please mention all the database passwords in the correct format as arguments while using Turbatio");
			else
				return turbatioService.getDetailsFromTurbatio(turbatoURL, dbPasswordsInJSon);
		} else {
			FileService fileService = ServiceFactory.getFileService();
			return fileService.getDetailsFromFile();
		}
	}

	private String getTurbatoURL(String appServerPath)
			throws IOException, InvalidUrlException, InvalidApplicationServerPathException {
		try {
			if (appServerPath != null) {
				Properties properties = PropertiesUtil.getProperties(Constants.PRODUCT_INFORMATION_PROPERTIES);
				StringBuilder builder = new StringBuilder(properties.getProperty(Constants.TURBATO_URL).trim());
				builder.append("/services?service_name=").append(properties.getProperty(Constants.PRODUCE_NAME).trim())
						.append("&host=").append(properties.getProperty(Constants.PRODUCT_HOST).trim())
						.append("&jboss_path=").append(appServerPath);
				return builder.toString();
			} else {
				throw new InvalidApplicationServerPathException(
						"Please Provide the Application Server Path as Argument");
			}
		} catch (NullPointerException e) {
			throw new InvalidUrlException("Please check inputs in ProductInformation.properties.", e);
		}
	}

	@Override
	public boolean isNewDBVersionFound(DatabaseConnectionAPI database, String currentVersion, String upgradeType,
			String userName) throws SQLException, ClassNotFoundException, InvalidVersionDetailsException,
			InvalidVersionDetailsInDBException, InvalidApplicationServerPathException {
		if (!database.isTableExists(userName)) {
			database.createTable();
			LOGGER.info("Executing for the first time, so creating table");
			return true;
		} else if (upgradeType.equalsIgnoreCase(UpgradeType.Utility.getValue())) {
			LOGGER.info("Not checking DB version for Utility Script");
			return true;
		} else {
			LOGGER.info("Checking DB base version");
			String lastUpdatedVersion = database.getLastVersion(upgradeType);
			if (lastUpdatedVersion != null) {
				LOGGER.info("Base version is: " + lastUpdatedVersion);
			} else
				return true;
			return VersionUtil.isNewDBVerFound(lastUpdatedVersion, currentVersion);
		}
	}

	@Override
	public boolean excuteQueries(ConnectionDetails connectionDetails) throws SQLException, SQLScriptExecutionException {
		String result = null;
		SQLScriptExecutor executor = new SQLScriptExecutor(connectionDetails);
		LOGGER.info("Executing current script");
		result = executor.executeSql().name();
		LOGGER.info("Current script execution - " + result);
		if (UpdateEnum.SUCCESSFUL.name().equals(result))
			return true;
		return false;
	}

	@Override
	public void updateSQLScriptVersion(DatabaseConnectionAPI database, String versionNumber, String upgradeType)
			throws SQLException {
		database.updateVersion(versionNumber, upgradeType);
	}

	/*
	 * (non-Javadoc) Checks from SQLScriptversionning file if Turbato is
	 * configured or not
	 */
	private boolean isTurbatioConfigured() throws IOException {
		try {
			Properties properties = PropertiesUtil.getProperties(Constants.PRODUCT_INFORMATION_PROPERTIES);
			String turbatoConfigured = properties.getProperty(Constants.IS_TURBATO_CONFIGURED);
			if ((Constants.YES).equals(turbatoConfigured))
				return true;
			return false;
		} catch (NullPointerException e) {
			return false;
		} catch (FileNotFoundException e) {
			return false;
		}
	}

}
