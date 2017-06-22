package com.zycus.scriptExecutor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.zycus.scriptExecutor.bo.ConnectionDetails;
import com.zycus.scriptExecutor.databaseAPI.DatabaseConnectionAPI;
import com.zycus.scriptExecutor.exception.InvalidApplicationServerPathException;
import com.zycus.scriptExecutor.exception.InvalidConnectionDetailsException;
import com.zycus.scriptExecutor.exception.InvalidConnectionDetailsInFileException;
import com.zycus.scriptExecutor.exception.InvalidContentInTurbatioException;
import com.zycus.scriptExecutor.exception.InvalidDatabasePasswordException;
import com.zycus.scriptExecutor.exception.InvalidDriverNameException;
import com.zycus.scriptExecutor.exception.InvalidUrlException;
import com.zycus.scriptExecutor.exception.InvalidVersionDetailsException;
import com.zycus.scriptExecutor.exception.InvalidVersionDetailsInDBException;
import com.zycus.scriptExecutor.exception.SQLScriptExecutionException;
import com.zycus.scriptExecutor.factory.ConnectionFactory;
import com.zycus.scriptExecutor.logger.CustomLogger;
import com.zycus.scriptExecutor.service.DatabaseService;
import com.zycus.scriptExecutor.service.factory.ServiceFactory;
import com.zycus.scriptExecutor.utility.DatabaseConnectionUtil;
import com.zycus.scriptExecutor.utility.VersionUtil;

public class ScriptExcutor {
	private static final CustomLogger LOGGER = CustomLogger.getInstance();
	private static DatabaseService databaseService = ServiceFactory.getDatabaseService();

	public static void main(String[] args) {
		try {
			String appServerPath = null;
			String dbPasswordsInJSon = null;
			if (args.length > 0) {
				appServerPath = args[0];
				dbPasswordsInJSon = args[1];
			}
			List<ConnectionDetails> connectionDetails = databaseService.readDatabaseConnectionDetails(appServerPath,
					dbPasswordsInJSon);

			for (ConnectionDetails connectionDetail : connectionDetails) {
				DatabaseConnectionAPI database = ConnectionFactory.getConnection(connectionDetail);
				String scriptDBVersion = VersionUtil.getCurrentVersion(connectionDetail.getScriptFile());
				String upgradeType = VersionUtil.getUpgradeType(scriptDBVersion);
				LOGGER.info("Starting execution for");
				LOGGER.info("Category: " + connectionDetail.getDatabaseCategory());
				LOGGER.info("Database: " + connectionDetail.getUrl());
				LOGGER.info("Username: " + connectionDetail.getUsername());
				if (!scriptDBVersion.equalsIgnoreCase("0.0.0.0"))
					LOGGER.info("Version of the upgrade-script file: " + scriptDBVersion);

				if (databaseService.isNewDBVersionFound(database, scriptDBVersion, upgradeType,
						connectionDetail.getUsername())) {
					if (databaseService.excuteQueries(connectionDetail)) {
						try {
							scriptDBVersion = VersionUtil.getProperFormat(scriptDBVersion);
							databaseService.updateSQLScriptVersion(database, scriptDBVersion, upgradeType);
							LOGGER.info("Upgraded to Version: " + scriptDBVersion);
						} catch (SQLException e) {
							LOGGER.error(
									"Unable to update version in DB. Script was executed though, kindly rollback and try again and if problem persists contact ADMIN",
									e);
							System.exit(1);
						}
					}
				}
				LOGGER.info("*********************************************");
			}

			System.exit(0);

		} catch (InvalidApplicationServerPathException e) {
			LOGGER.error("Make sure to provide the Application Server Path as Argument", e);
			System.exit(1);
		} catch (NullPointerException e) {
			LOGGER.error("Make sure correct connection details file is present", e);
			System.exit(1);
		} catch (InvalidUrlException e) {
			LOGGER.error("Please Check the details of the Turbato Url Specified.", e);
			System.exit(1);
		} catch (InvalidContentInTurbatioException e) {
			LOGGER.error(
					"Incorrect content Found in Turbato. Make sure the details given for the TURBATO Url(Application Server Path/ProductInformation.properties) are correct.",
					e);
			System.exit(1);
		} catch (IOException e) {
			LOGGER.error("File not found, make sure all required Files are Present", e);
			System.exit(1);
		} catch (SQLException e) {
			LOGGER.error("Illegal SQL statement!!, make sure script responsible for upgrade is correct ", e);
			System.exit(1);
		} catch (ClassNotFoundException e) {
			LOGGER.error("Class Not Found, make sure all required files are in the class path", e);
			System.exit(1);
		} catch (InvalidConnectionDetailsInFileException e) {
			LOGGER.error("Correct the details provided and try again", e);
			System.exit(1);
		} catch (InvalidConnectionDetailsException e) {
			LOGGER.error("Could Not Establish Connection, Check Connection Details", e);
			System.exit(1);
		} catch (InvalidVersionDetailsException e) {
			LOGGER.error("Please specify the current Version in the DBQueries File in the proper format", e);
			System.exit(1);
		} catch (InvalidDriverNameException e) {
			LOGGER.error("Make sure the DATABASE TYPE is entered correctly", e);
			System.exit(1);
		} catch (SQLScriptExecutionException e) {
			LOGGER.error("Failed to Commit, ROLLBACK immediately if required and try again", e);
			System.exit(1);
		} catch (NumberFormatException e) {
			LOGGER.error(
					"Please mention the count of the connection details in the databaseDetails.properties File in the Proper Format.",
					e);
			System.exit(1);
		} catch (InvalidDatabasePasswordException e) {
			LOGGER.error(
					"Please mention all the database passwords in the correct format as arguments while using Turbatio",
					e);
			System.exit(1);
		} catch (InvalidVersionDetailsInDBException e) {
			LOGGER.error("Incorrect DATA in Database Mentioned !", e);
			System.exit(1);
		} catch (Exception e) {
			LOGGER.fatal("OOPS, Something went wrong, try again", e);
			System.exit(1);
		} finally {
			DatabaseConnectionUtil.closeConnection();
		}
	}
}
