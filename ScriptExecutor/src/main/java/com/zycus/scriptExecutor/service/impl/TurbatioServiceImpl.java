package com.zycus.scriptExecutor.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.zycus.scriptExecutor.bo.ConnectionDetails;
import com.zycus.scriptExecutor.constants.Constants;
import com.zycus.scriptExecutor.exception.InvalidContentInTurbatioException;
import com.zycus.scriptExecutor.exception.InvalidDatabasePasswordException;
import com.zycus.scriptExecutor.exception.InvalidDriverNameException;
import com.zycus.scriptExecutor.exception.InvalidUrlException;
import com.zycus.scriptExecutor.service.TurbatioService;
import com.zycus.scriptExecutor.utility.CommonUtil;

public class TurbatioServiceImpl implements TurbatioService {

	@Override
	public List<ConnectionDetails> getDetailsFromTurbatio(String turbatioURL, String dbPasswordsInJSon)
			throws IOException, InvalidUrlException, InvalidDriverNameException, URISyntaxException,
			InvalidDatabasePasswordException, Exception {
		List<ConnectionDetails> connectionDetails = new ArrayList<ConnectionDetails>();
		Map<Object, Object> jsonMap = getMapFromJSON(turbatioURL);
		if (jsonMap.containsKey(Constants.DETAILS)) {
			List<Map<Object, Object>> allConnectionDetails = getConnectionDetailsFromMap(jsonMap);
			for (Map<Object, Object> connection : allConnectionDetails) {
				ConnectionDetails connectionDetail = getConnectionDetails(connection, dbPasswordsInJSon);
				if (connectionDetail != null)
					connectionDetails.add(connectionDetail);
			}
			return connectionDetails;
		}
		return null;
	}

	/*
	 * (non-Javadoc) Simply parses the JSON file to convert into a MAP
	 */
	private Map<Object, Object> getMapFromJSON(String turbatioURL) throws IOException, Exception {
		String turbatioContent = getDataFromTurbatio(turbatioURL);
		try {
			JsonParser parser = new JsonParser();
			JsonArray jsonArray = (JsonArray) parser.parse(turbatioContent);
			JsonObject jsonObject = (JsonObject) jsonArray.get(0);
			Gson gson = new Gson();
			Type type = new TypeToken<Map<Object, Object>>() {
			}.getType();
			Map<Object, Object> jsonMap = gson.fromJson(jsonObject, type);
			return jsonMap;
		} catch (IndexOutOfBoundsException e) {
			throw new InvalidContentInTurbatioException("Unable to parse data from turbatio.", e);
		}
	}

	private String getDataFromTurbatio(String url) throws IOException, InvalidUrlException {
		StringBuilder result = new StringBuilder();
		HttpGet request = null;
		BufferedReader rd = null;
		try {
			final String USER_AGENT = "Mozilla/5.0";
			HttpClient client = HttpClientBuilder.create().build();

			request = new HttpGet(url);
			request.addHeader("User-Agent", USER_AGENT);
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new InvalidUrlException("Unable to connect turbatio. check ProductInformation.properties.");
			} else {
				rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String line = "";
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
				return result.toString();
			}
		} finally {
			if (rd != null)
				rd.close();
			if (request != null)
				request.releaseConnection();
		}
	}

	/*
	 * (non-Javadoc) Parses the JSON map to extract the connectionDetails and
	 * executes the upgrade scripts accordingly
	 */
	@SuppressWarnings("unchecked")
	private List<Map<Object, Object>> getConnectionDetailsFromMap(Map<Object, Object> jsonMap) {
		Map<Object, Object> detailsMap = (Map<Object, Object>) jsonMap.get(Constants.DETAILS);
		if (detailsMap.containsKey(Constants.SETUP_DETAILS)) {
			Map<Object, Object> setupDetailsMap = (Map<Object, Object>) detailsMap.get(Constants.SETUP_DETAILS);
			List<Map<Object, Object>> allConnectionDetails = new ArrayList<Map<Object, Object>>();
			if (setupDetailsMap.containsKey(Constants.DATABASE_DETAILS_LIST))
				allConnectionDetails = (List<Map<Object, Object>>) setupDetailsMap.get(Constants.DATABASE_DETAILS_LIST);
			return allConnectionDetails;
		}
		return null;
	}

	/*
	 * (non-Javadoc) method to create Connection Details object from properties
	 * file
	 */
	private ConnectionDetails getConnectionDetails(Map<Object, Object> connection, String dbPasswordsInJSon)
			throws InvalidDriverNameException, URISyntaxException, IOException, InvalidDatabasePasswordException {

		String databaseType = (String) connection.get(Constants.DATABASE_TYPE);
		String databaseHost = (String) connection.get(Constants.DATABASE_HOST);
		String databasePort = (String) connection.get(Constants.DATABASE_PORT);
		String databaseName = (String) connection.get(Constants.DATABASE_NAME);
		String databaseCategory = (String) connection.get(Constants.DATABASE_CATEGORY);
		String databaseUser = (String) connection.get(Constants.DATABASE_USER);
		Map<String, String> passwordMap = getMapFromJSon(dbPasswordsInJSon);
		String password = getPassword(passwordMap, databaseUser);
		if (!CommonUtil.isContainNullParameter(databaseType, databaseHost, databasePort, databaseName, databaseUser)) {
			ConnectionDetails connectionDetails = new ConnectionDetails(databaseType, databaseHost, databasePort,
					databaseName, databaseUser, password, databaseCategory, "GO");
			return connectionDetails;
		} else
			return null;
	}

	private Map<String, String> getMapFromJSon(String dbPasswordsInJSon) {
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = (JsonObject) parser.parse(dbPasswordsInJSon);
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, String>>() {
		}.getType();
		Map<String, String> passwordMap = gson.fromJson(jsonObject, type);
		return passwordMap;
	}

	private String getPassword(Map<String, String> passwordMap, String databaseUser)
			throws InvalidDatabasePasswordException {
		if (passwordMap.containsKey(databaseUser)) {
			String password = passwordMap.get(databaseUser);
			return password;
		} else
			throw new InvalidDatabasePasswordException(
					"Please mention all the database passwords in the correct format as arguments while using Turbatio");
	}

}
