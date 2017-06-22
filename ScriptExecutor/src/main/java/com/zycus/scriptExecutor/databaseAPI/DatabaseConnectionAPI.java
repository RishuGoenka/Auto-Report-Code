package com.zycus.scriptExecutor.databaseAPI;

import java.sql.SQLException;

import com.zycus.scriptExecutor.exception.InvalidVersionDetailsException;
import com.zycus.scriptExecutor.exception.InvalidVersionDetailsInDBException;

/**
 * Provides basic database functionalities to be performed on the Database of
 * any TYPE.
 * 
 * @author harshvardhan.dudeja
 *
 */
public interface DatabaseConnectionAPI {

	/**
	 * Simply create the Version table in the database
	 * 
	 * @throws SQLException
	 */
	public void createTable() throws SQLException;

	/**
	 * Finds out the last updated version from the version table
	 * 
	 * @param upgradeType
	 * 
	 * @return the last updated version
	 * @throws SQLException
	 * @throws InvalidVersionDetailsException
	 * @throws InvalidVersionDetailsInDBException
	 */
	public String getLastVersion(String upgradeType)
			throws InvalidVersionDetailsException,
			InvalidVersionDetailsInDBException;

	/**
	 * Checks if the Version Table exists in the Database
	 * 
	 * @param dbMetaData
	 * @return true if the table exists, false if it doesn't
	 * @throws SQLException
	 */
	public boolean isTableExists(String userName) throws SQLException;

	/**
	 * Updates the Version Table to reflect current updated version
	 * 
	 * @param currentVersion
	 * @throws SQLException
	 */
	public void updateVersion(String currentVersion, String updateType)
			throws SQLException;

}