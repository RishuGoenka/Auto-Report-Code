package com.zycus.scriptExecutor.utility;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;

import com.zycus.scriptExecutor.bo.ConnectionDetails;
import com.zycus.scriptExecutor.exception.SQLScriptExecutionException;
import com.zycus.scriptExecutor.logger.CustomLogger;

/**
 * Utility to offer automated execution of the SQL Script
 * 
 * @author harshvardhan.dudeja
 *
 */
public class SQLScriptExecutor {
	private static final CustomLogger LOGGER = CustomLogger.getInstance();
	private final File scriptFile;
	private final String driver;
	private final String url;
	private final String userName;
	private final String passWord;
	private String delimiter;

	/**
	 * Parameterized Constructor with exact arguments
	 * 
	 * @param scriptFilePath
	 *            is the script file
	 * @param driver
	 *            is the driver name
	 * @param url
	 *            is the database url
	 * @param userName
	 *            is the username of the database
	 * @param passWord
	 *            is the password required to connect to the database
	 * @throws Exception
	 *             for unforseeable scenarios
	 */
	public SQLScriptExecutor(File scriptFilePath, String driver, String url, String userName, String passWord)
			throws Exception {

		this.driver = driver;
		this.url = url;
		this.userName = userName;
		this.passWord = passWord;
		this.scriptFile = scriptFilePath;
	}

	/**
	 * Parameterized Constructor with connectionDetails object. initializes the
	 * appropriate attributes according the details provided
	 * 
	 * @param connectionDetails
	 */
	public SQLScriptExecutor(ConnectionDetails connectionDetails) {
		this.driver = connectionDetails.getDriverName();
		this.url = connectionDetails.getUrl();
		this.userName = connectionDetails.getUsername();
		this.passWord = connectionDetails.getPassword();
		this.scriptFile = connectionDetails.getScriptFile();
		this.delimiter = connectionDetails.getDelimiter();
	}

	/**
	 * Method Responsible for executing the database upgrade requested.
	 * 
	 * @return if upgrade was successfully updated or not
	 * @throws SQLScriptExecutionException
	 */
	public UpdateEnum executeSql() throws SQLScriptExecutionException {
		try {
			final class SqlExecuter extends SQLExec {

				public SqlExecuter() {
					Project project = new Project();
					project.init();
					setProject(project);
					setDelimiter(delimiter);
					setTaskType("Database activity");
					setTaskName("SQL script execution");
				}
			}

			SqlExecuter executer = new SqlExecuter();
			executer.getVersion();
			executer.setSrc(scriptFile);
			executer.setDriver(driver);
			executer.setUrl(url);
			executer.setUserid(userName);
			executer.setPassword(passWord);
			executer.execute();
		} catch (Exception e) {
			if (e.getMessage().contains("Invalid SQL type: sqlKind = UNINITIALIZED")) {
				LOGGER.error(
						"It may be possible that NO query or ILLEGAL query found. Please check your DBQueries.sql file for the same",
						e);
				return UpdateEnum.SUCCESSFUL;
			} else {
				LOGGER.info("current script execution failed");
				LOGGER.info("exiting");
				throw new SQLScriptExecutionException("Unable to execute queries. Please check the logs", e);
			}
		} finally {
			if (scriptFile.exists())
				scriptFile.delete();
		}
		return UpdateEnum.SUCCESSFUL;
	}
}
