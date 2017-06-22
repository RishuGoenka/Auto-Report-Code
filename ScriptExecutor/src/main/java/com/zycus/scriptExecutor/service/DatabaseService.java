package com.zycus.scriptExecutor.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.zycus.scriptExecutor.bo.ConnectionDetails;
import com.zycus.scriptExecutor.databaseAPI.DatabaseConnectionAPI;
import com.zycus.scriptExecutor.exception.InvalidApplicationServerPathException;
import com.zycus.scriptExecutor.exception.InvalidConnectionDetailsInFileException;
import com.zycus.scriptExecutor.exception.InvalidDatabasePasswordException;
import com.zycus.scriptExecutor.exception.InvalidDriverNameException;
import com.zycus.scriptExecutor.exception.InvalidUrlException;
import com.zycus.scriptExecutor.exception.InvalidVersionDetailsException;
import com.zycus.scriptExecutor.exception.InvalidVersionDetailsInDBException;
import com.zycus.scriptExecutor.exception.SQLScriptExecutionException;

/**
 * Class to provide functionalities related to Databases, Running Upgrade Files
 * and Executing Database Versioning as well.
 * 
 * @author harshvardhan.dudeja
 *
 */
public interface DatabaseService {

	/**
	 * Reads all the necessary connection details given either via the
	 * properties file given in the class path or via the turbato URL provided.
	 * Searches and stores the details in ConnectionDetails object.
	 * 
	 * @param appServerPath
	 *            is the application server path required.
	 * @return A list of the ConnectionDetails Objects as many connection
	 *         details are given
	 * @throws FileNotFoundException
	 *             when property file is not present at the correct location
	 * @throws InvalidUrlException
	 *             when the Turbatio url specified is incorrect
	 * @throws InvalidDriverNameException
	 *             when database name is not specified or not in the specified
	 *             format.
	 * @throws InvalidConnectionDetailsInFileException
	 *             when connection details provided in properties file is not
	 *             present or in incorrect format.
	 * @throws Exception
	 *             when unexpected error occurs
	 * @throws NumberFormatException
	 *             when Version specified in the file is in incorrect format
	 */
	public abstract List<ConnectionDetails> readDatabaseConnectionDetails(
			String appServerPath, String dbPasswordsInJSon)
			throws FileNotFoundException, IOException, InvalidUrlException,
			InvalidConnectionDetailsInFileException,
			InvalidDriverNameException, NumberFormatException,
			InvalidDatabasePasswordException,
			InvalidApplicationServerPathException, Exception;

	/**
	 * Checks if the Table exists in the Database, if not create it. If table is
	 * present, checks the current version with the last updated version to
	 * decide if the database upgrade script file should be executed or not.
	 * 
	 * @param connectionDetails
	 *            object to get all connection details
	 * @param connectionType
	 *            object to execute databse queries
	 * @param currentVersion
	 *            in the upgrade script file
	 * @return true if script file should be executed, false if not
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InvalidVersionDetailsException
	 * @throws InvalidVersionDetailsInDBException
	 */
	public abstract boolean isNewDBVersionFound(
			DatabaseConnectionAPI connectionType, String currentVersion,
			String upgradeType, String userName) throws SQLException,
			ClassNotFoundException, InvalidVersionDetailsException,
			InvalidVersionDetailsInDBException,
			InvalidApplicationServerPathException;

	/**
	 * Executes the upgrade file on the database details specified.
	 * 
	 * @param connectionDetails
	 *            object to get all connection details
	 * @param currentVersion
	 *            in the upgrade script file
	 * @return whether the upgrade was successfull or not
	 * @throws SQLException
	 *             when any SQL exception occurs in any unforced scenario
	 * @throws SQLScriptExecutionException
	 *             when Exception occurs while executing the upgrade file
	 */
	public abstract boolean excuteQueries(ConnectionDetails connectionDetails)
			throws SQLException, SQLScriptExecutionException;

	/**
	 * Updates the version specified in the version table in the database.
	 * 
	 * @param connectionType
	 *            object to execute databse queries
	 * @param versionNumber
	 *            is the version number to updated in the database
	 * @throws SQLException
	 *             when any SQL exception occurs in any usnforced scenario
	 */
	public abstract void updateSQLScriptVersion(
			DatabaseConnectionAPI connectionType, String versionNumber,
			String upgradeType) throws SQLException;

}