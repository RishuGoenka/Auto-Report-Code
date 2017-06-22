package com.zycus.scriptExecutor.bo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;

import com.zycus.scriptExecutor.constants.Constants;
import com.zycus.scriptExecutor.exception.InvalidDriverNameException;

/**
 * Basic POJO for retrieving all details required to create a connection to a
 * database. Also stores the scriptFile path to be executed for further use.
 * Allows only Parameterized constructor with all details necessary. Generates
 * Driver Name and Database Url by basic parameters.
 * 
 * @author harshvardhan.dudeja
 *
 */
public class ConnectionDetails {

	private String driverName;
	private String url;
	private String serviceNameURL;
	private String username;
	private String password;
	private File scriptFile;
	private String delimiter;
	private String databaseCategory;
	private static int count = 0;

	public ConnectionDetails(String databaseType, String databaseHost,
			String databasePort, String databaseName, String databaseUser,
			String password, String databaseCategory, String delimiter)
			throws InvalidDriverNameException, URISyntaxException, IOException {
		this.driverName = getDriver(databaseType);
		this.url = getURL(databaseHost, databasePort, databaseName,
				databaseType);
		this.serviceNameURL = getServiceNameURL(databaseHost, databasePort,
				databaseName, databaseType);
		this.username = databaseUser;
		this.password = password == null ? databaseUser : password;
		this.scriptFile = getScriptFile(databaseCategory);
		this.delimiter = (delimiter == null || delimiter.isEmpty()) ? "GO"
				: delimiter;
		this.databaseCategory = (databaseCategory == null) ? Constants.COMPANY
				: databaseCategory;
	}

	private File getScriptFile(String databaseCategory)
			throws URISyntaxException, IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		File scriptFile = new File("tempDbFile" + ++count + ".sql");
		try {
			if (databaseCategory == null) {
				inputStream = Thread
						.currentThread()
						.getContextClassLoader()
						.getResourceAsStream(
								Constants.COMPANY + File.separator
										+ Constants.DB_FILE_NAME);
			} else {
				inputStream = Thread
						.currentThread()
						.getContextClassLoader()
						.getResourceAsStream(
								databaseCategory + File.separator
										+ Constants.DB_FILE_NAME);
			}
			if (scriptFile.exists() == false) {
				scriptFile.createNewFile();
			}
			outputStream = new FileOutputStream(scriptFile);
			int read = 0;
			byte[] bytes = new byte[1024];
			if (inputStream != null) {
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
			} else {
				outputStream.write("/*version=0.0.0.0*/".getBytes());
			}
		} finally {
			if (outputStream != null)
				outputStream.close();
			if (inputStream != null)
				inputStream.close();
		}
		scriptFile.deleteOnExit();
		return scriptFile;
	}

	public String getDatabaseCategory() {
		return databaseCategory;
	}

	public void setDatabaseCategory(String databaseCategory) {
		this.databaseCategory = databaseCategory;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public File getScriptFile() {
		return scriptFile;
	}

	public void setScriptFile(File scriptFile) {
		this.scriptFile = scriptFile;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String databaseType)
			throws InvalidDriverNameException {
		this.driverName = getDriver(databaseType);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getServiceNameURL() {
		return serviceNameURL;
	}

	public void setServiceNameURL(String serviceNameURL) {
		this.serviceNameURL = serviceNameURL;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String getDriver(String databaseName)
			throws InvalidDriverNameException {
		if (("oracle").equalsIgnoreCase(databaseName))
			return Constants.oracleDriverName;
		else if (("mssql").equalsIgnoreCase(databaseName))
			return Constants.msSqlDriverName;
		throw new InvalidDriverNameException();
	}

	private String getURL(String databaseHost, String databasePort,
			String databaseName, String databaseType) {
		StringBuilder builder = new StringBuilder();
		if (("oracle").equals(databaseType)) {
			return builder.append(Constants.ORACLE_URL).append(databaseHost)
					.append(":").append(databasePort).append(":")
					.append(databaseName).toString();
		} else {
			return builder.append(Constants.MSSQL_URL).append(databaseHost)
					.append(":").append(databasePort).append(";DatabaseName=")
					.append(databaseName).toString();
		}
	}

	private String getServiceNameURL(String databaseHost, String databasePort,
			String databaseName, String databaseType) {
		StringBuilder builder = new StringBuilder();
		if (("oracle").equals(databaseType)) {
			return builder.append(Constants.ORACLE_URL).append(databaseHost)
					.append(":").append(databasePort).append("/")
					.append(databaseName).toString();
		} else {
			return builder.append(Constants.MSSQL_URL).append(databaseHost)
					.append(":").append(databasePort).append(";DatabaseName=")
					.append(databaseName).toString();
		}
	}

	@Override
	public String toString() {
		return "ConnectionDetails [driverName=" + driverName + ", url=" + url
				+ ", username=" + username + ", password=" + password
				+ ", scriptFile=" + scriptFile + "]";
	}

}
