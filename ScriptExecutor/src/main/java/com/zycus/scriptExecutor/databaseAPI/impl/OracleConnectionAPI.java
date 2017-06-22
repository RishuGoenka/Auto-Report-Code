package com.zycus.scriptExecutor.databaseAPI.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zycus.scriptExecutor.bo.ConnectionDetails;
import com.zycus.scriptExecutor.constants.Constants;
import com.zycus.scriptExecutor.databaseAPI.DatabaseConnectionAPI;
import com.zycus.scriptExecutor.exception.InvalidConnectionDetailsException;
import com.zycus.scriptExecutor.exception.InvalidVersionDetailsException;
import com.zycus.scriptExecutor.utility.CommonUtil;
import com.zycus.scriptExecutor.utility.DatabaseConnectionUtil;

public class OracleConnectionAPI implements DatabaseConnectionAPI {

	public OracleConnectionAPI(ConnectionDetails connectionDetails)
			throws ClassNotFoundException, InvalidConnectionDetailsException {
		try {
			DatabaseConnectionUtil.setConnection(connectionDetails);
		} catch (InvalidConnectionDetailsException ex) {
			try {
				DatabaseConnectionUtil
						.setOracleServiceNameConnection(connectionDetails);
				connectionDetails.setUrl(connectionDetails.getServiceNameURL());
			} catch (InvalidConnectionDetailsException e) {
				throw new InvalidConnectionDetailsException(
						"Please check the Database connection details(Service name/SID).",
						e);
			}
		}
	}

	@Override
	public void createTable() throws SQLException {
		DatabaseConnectionUtil.getConnection()
				.prepareStatement(Constants.CREATE_TABLE_QUERY).executeQuery();
	}

	@Override
	public String getLastVersion(String upgradeType)
			throws InvalidVersionDetailsException {
		try {
			PreparedStatement statement = DatabaseConnectionUtil
					.getConnection().prepareStatement(
							Constants.GET_LAST_VERSION_WITH_TYPE_QUERY);
			statement.setString(1, upgradeType);
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next())
				return null;
			return resultSet.getString(1);
		} catch (Exception e) {
			throw new InvalidVersionDetailsException(
					"Unable to get version from db.", e);
		}
	}

	@Override
	public boolean isTableExists(String userName) throws SQLException {
		String query = "select count(*) from all_objects where object_type in ('TABLE') and object_name = 'SQL_SCRIPT_VERSION' and lower(OWNER) ='"
				+ userName.toLowerCase() + "'";
		ResultSet rs = DatabaseConnectionUtil.getConnection()
				.prepareStatement(query).executeQuery();
		rs.next();
		int count = rs.getInt(1);
		if (count == 0)
			return false;
		else
			return true;
	}

	@Override
	public void updateVersion(String currentVersion, String updateType)
			throws SQLException {
		StringBuilder builder = new StringBuilder();
		String query = builder
				.append(Constants.UPDATE_VERSION_QUERY)
				.append(currentVersion)
				.append("','")
				.append(updateType)
				.append("','")
				.append(CommonUtil.getDateString(new Date(new java.util.Date()
						.getTime()))).append("')").toString();
		DatabaseConnectionUtil.getConnection().prepareStatement(query)
				.executeQuery();
	}
}
