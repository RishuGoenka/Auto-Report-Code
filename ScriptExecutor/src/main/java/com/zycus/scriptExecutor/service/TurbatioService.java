package com.zycus.scriptExecutor.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.zycus.scriptExecutor.bo.ConnectionDetails;
import com.zycus.scriptExecutor.exception.InvalidDatabasePasswordException;
import com.zycus.scriptExecutor.exception.InvalidDriverNameException;
import com.zycus.scriptExecutor.exception.InvalidUrlException;

/**
 * Class to provide Connection details from Turbato.
 * 
 * @author harshvardhan.dudeja
 *
 */
public interface TurbatioService {

	/**
	 * Simple method to get all the Connection details from the turbato url
	 * specified.
	 * 
	 * @param turbatoURL
	 *            is the URL to hit and get content from.
	 * @return List of Connection Details(Object)
	 * @throws FileNotFoundException
	 *             when properties file is not found
	 * @throws InvalidUrlException
	 *             when the url specified in the argument is not correct
	 * @throws InvalidDriverNameException
	 *             when database name is not specified or not in the specified
	 *             format.
	 * @throws Exception
	 *             for all unforseeable scenarios
	 */
	public abstract List<ConnectionDetails> getDetailsFromTurbatio(
			String turbatoURL, String dbPasswordsInJSon)
			throws FileNotFoundException, IOException, InvalidUrlException,
			InvalidDriverNameException, InvalidDatabasePasswordException,
			Exception;

}