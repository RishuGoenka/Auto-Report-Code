package com.zycus.scriptExecutor.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.zycus.scriptExecutor.bo.ConnectionDetails;
import com.zycus.scriptExecutor.exception.InvalidConnectionDetailsException;

/**
 * Utility with everything related to Database Connection object
 * 
 * @author harshvardhan.dudeja
 *
 */
public class DatabaseConnectionUtil {
	private static Connection connection;

	public static Connection getConnection() {
		return connection;
	}

	public static void closeConnection() {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			throw new RuntimeException("Unable to close connection", e);
		}
	}

	public static void setConnection(ConnectionDetails connectionDetails)
			throws ClassNotFoundException, InvalidConnectionDetailsException {
		try {
			Class.forName(connectionDetails.getDriverName());
			connection = DriverManager.getConnection(
					connectionDetails.getUrl(),
					connectionDetails.getUsername(),
					connectionDetails.getPassword());
		} catch (SQLException ex) {
			throw new InvalidConnectionDetailsException(
					"Please check the Database connection details.", ex);
		}
	}

	public static void setOracleServiceNameConnection(
			ConnectionDetails connectionDetails) throws ClassNotFoundException,
			InvalidConnectionDetailsException {
		try {
			Class.forName(connectionDetails.getDriverName());
			connection = DriverManager.getConnection(
					connectionDetails.getServiceNameURL(),
					connectionDetails.getUsername(),
					connectionDetails.getPassword());
		} catch (SQLException e) {
			throw new InvalidConnectionDetailsException(
					"Please check the Database connection details.", e);
		}
	}

}
