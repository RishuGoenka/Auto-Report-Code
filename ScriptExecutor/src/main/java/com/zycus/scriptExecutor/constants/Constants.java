package com.zycus.scriptExecutor.constants;

/**
 * Class to store all CONSTANTS.
 * 
 * @author harshvardhan.dudeja
 *
 */
public class Constants {
	public static final String oracleDriverName = "oracle.jdbc.driver.OracleDriver";
	public static final String msSqlDriverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static final String ORACLE_URL = "jdbc:oracle:thin:@";
	public static final String MSSQL_URL = "jdbc:sqlserver://";

	public static final String CREATE_TABLE_QUERY = "CREATE TABLE SQL_SCRIPT_VERSION (current_version varchar(15) NOT NULL,update_type varchar(1), update_date varchar(15) NOT NULL)";
	public static final String GET_LAST_VERSION_QUERY = "SELECT current_version FROM SQL_SCRIPT_VERSION ORDER BY current_version DESC";
	public static final String GET_LAST_VERSION_WITH_TYPE_QUERY = "SELECT current_version FROM SQL_SCRIPT_VERSION WHERE update_type = ? ORDER BY current_version DESC";
	public static final String UPDATE_VERSION_QUERY = "insert into SQL_SCRIPT_VERSION values ('";

	public static final String PRODUCT_INFORMATION_PROPERTIES = "ProductInformation.properties";
	public static final String TURBATO_URL = "turbato_URL";
	public static final String PRODUCT_HOST = "product_host";
	public static final String PRODUCE_NAME = "product_name";
	public static final String IS_TURBATO_CONFIGURED = "isTurbatioConfigured";

	public static final String DATABASE_DETAILS_PROPERTIES = "databaseDetails.properties";
	public static final String DATABASE_TYPE = "database_type";
	public static final String DATABASE_USER = "database_user";
	public static final String DATABASE_HOST = "database_host";
	public static final String DATABASE_PORT = "database_port";
	public static final String DATABASE_NAME = "database_name";
	public static final String DATABASE_PASSWORD = "database_password";
	public static final String DATABASE_CATEGORY = "DatabaseCategory";
	public static final String CONNECTION_COUNT = "ConnectionCount";
	public static final String DELIMITER = "Delimiter";
	public static final String APPLICATION_NODE_TYPE = "Application_Node_Type";

	public static final String SETUP_DETAILS = "setup_details";
	public static final String DATABASE_DETAILS_LIST = "database_details_list";
	public static final String DETAILS = "details";

	public static final String YES = "Yes";
	public static final String COMPANY = "Company";
	public static final String DB_FILE_NAME = "DBQueries.sql";
}
