package com.zycus.scriptExecutor.service.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.zycus.scriptExecutor.bo.ConnectionDetails;
import com.zycus.scriptExecutor.constants.Constants;
import com.zycus.scriptExecutor.exception.InvalidConnectionDetailsInFileException;
import com.zycus.scriptExecutor.exception.InvalidDriverNameException;
import com.zycus.scriptExecutor.service.FileService;
import com.zycus.scriptExecutor.utility.CommonUtil;
import com.zycus.scriptExecutor.utility.EncryptionUtil;
import com.zycus.scriptExecutor.utility.PropertiesUtil;

public class FileServiceImpl implements FileService {

	@Override
	public List<ConnectionDetails> getDetailsFromFile() throws IOException,
			InvalidConnectionDetailsInFileException,
			InvalidDriverNameException, NumberFormatException,
			URISyntaxException, InvalidKeyException, NoSuchPaddingException,
			NoSuchAlgorithmException, BadPaddingException,
			IllegalBlockSizeException {
		List<ConnectionDetails> connectionDetails = new ArrayList<ConnectionDetails>();
		int noOfDb = getConnectionCount();
		for (int i = 1; i <= noOfDb; i++)
			connectionDetails.add(getConnectionDetails(i));
		return connectionDetails;
	}

	private int getConnectionCount() throws IOException, NumberFormatException {
		Properties properties = PropertiesUtil
				.getProperties(Constants.DATABASE_DETAILS_PROPERTIES);
		return Integer.parseInt(properties
				.getProperty(Constants.CONNECTION_COUNT));
	}

	private ConnectionDetails getConnectionDetails(int index)
			throws IOException, InvalidConnectionDetailsInFileException,
			InvalidDriverNameException, URISyntaxException {
		try {
			Properties properties = PropertiesUtil
					.getProperties(Constants.DATABASE_DETAILS_PROPERTIES);
			String databaseType = properties
					.getProperty(Constants.DATABASE_TYPE + "_" + index);
			String databaseHost = properties
					.getProperty(Constants.DATABASE_HOST + "_" + index);
			String databasePort = properties
					.getProperty(Constants.DATABASE_PORT + "_" + index);
			String databaseName = properties
					.getProperty(Constants.DATABASE_NAME + "_" + index);
			String databaseCategory = properties
					.getProperty(Constants.DATABASE_CATEGORY + "_" + index);
			String databaseUser = properties
					.getProperty(Constants.DATABASE_USER + "_" + index);
			String applicationNodeType = properties
					.getProperty(Constants.APPLICATION_NODE_TYPE);
			if (applicationNodeType == null
					|| ("").equalsIgnoreCase(applicationNodeType))
				applicationNodeType = "tomcat";
			String password = getPassword(
					properties.getProperty(Constants.DATABASE_PASSWORD + "_"
							+ index), applicationNodeType);

			String delimiter = properties.getProperty(Constants.DELIMITER);

			if (!CommonUtil.isContainNullParameter(databaseType, databaseHost,
					databasePort, databaseName, databaseUser)) {
				ConnectionDetails connectionDetails = new ConnectionDetails(
						databaseType, databaseHost, databasePort, databaseName,
						databaseUser, password, databaseCategory, delimiter);
				return connectionDetails;
			} else
				throw new InvalidConnectionDetailsInFileException(
						"Check Connection details in File at number: " + index);
		} catch (NullPointerException e) {
			throw new InvalidConnectionDetailsInFileException(
					"Check Connection details in File at number: " + index, e);
		}
	}

	private String getPassword(String password, String applicationNodeType)
			throws InvalidConnectionDetailsInFileException {
		String decryptedPassword;
		try {
			if ("jboss".equalsIgnoreCase(applicationNodeType))
				decryptedPassword = EncryptionUtil.decodeForJBoss(password);
			else
				decryptedPassword = EncryptionUtil.decodeForTomcat(password);
		} catch (Exception e) {
			throw new InvalidConnectionDetailsInFileException(
					"Failed to decrypt, make sure password is correct and encrypted",
					e);
		}
		return decryptedPassword;
	}

}
