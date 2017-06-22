package com.zycus.scriptExecutor.factory;

import com.zycus.scriptExecutor.bo.ConnectionDetails;
import com.zycus.scriptExecutor.constants.Constants;
import com.zycus.scriptExecutor.databaseAPI.DatabaseConnectionAPI;
import com.zycus.scriptExecutor.databaseAPI.impl.MsSqlConnectionAPI;
import com.zycus.scriptExecutor.databaseAPI.impl.OracleConnectionAPI;
import com.zycus.scriptExecutor.exception.InvalidConnectionDetailsException;

/**
 * Factory to distinguish Database Connection TYPE.
 * 
 * @author harshvardhan.dudeja
 *
 */
public class ConnectionFactory {

	/**
	 * Identifies the Database being used based on the driver specified
	 * 
	 * @param driverName
	 *            is the type of the database.
	 * @return Oracle or MsSql Connection Object used to carry queries to the
	 *         Database.
	 * @throws ClassNotFoundException
	 *             when Driver class is not found
	 * @throws InvalidConnectionDetailsException
	 *             when connection details provided are incorrect and cannot
	 *             connect to the database
	 */
	public static DatabaseConnectionAPI getConnection(
			ConnectionDetails connectionDetails) throws ClassNotFoundException,
			InvalidConnectionDetailsException {
		if (Constants.oracleDriverName
				.equals(connectionDetails.getDriverName()))
			return new OracleConnectionAPI(connectionDetails);
		else if (Constants.msSqlDriverName.equals(connectionDetails
				.getDriverName()))
			return new MsSqlConnectionAPI(connectionDetails);
		return null;
	}
}
