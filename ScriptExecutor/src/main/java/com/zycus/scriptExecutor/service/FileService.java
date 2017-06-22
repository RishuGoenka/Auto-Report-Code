package com.zycus.scriptExecutor.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.zycus.scriptExecutor.bo.ConnectionDetails;
import com.zycus.scriptExecutor.exception.InvalidConnectionDetailsInFileException;
import com.zycus.scriptExecutor.exception.InvalidDriverNameException;

/**
 * Class to provide Connection details from file.
 * 
 * @author harshvardhan.dudeja
 *
 */
public interface FileService {

	/**
	 * Simple method to get all the Connection details from the files in the
	 * classpath.
	 * 
	 * @return List of Connection Details(Object)
	 * @throws IOException
	 *             if for some reason file cannot be read.
	 * @throws InvalidConnectionDetailsInFileException
	 *             when connection details provided in properties file is not
	 *             present or in incorrect format.
	 * @throws InvalidDriverNameException
	 *             when database name is not specified or not in the specified
	 *             format.
	 * @throws URISyntaxException
	 *             if the syntax for the uri is invalid
	 * @throws NumberFormatException
	 *             if Connection Count in the file is not in the proper format
	 * @throws IllegalBlockSizeException
	 *             when error while decoding
	 * @throws BadPaddingException
	 *             when error while decoding
	 * @throws NoSuchAlgorithmException
	 *             when error while decoding
	 * @throws NoSuchPaddingExceptionwhen
	 *             error while decoding
	 * @throws InvalidKeyException
	 *             when error while decoding
	 */
	public abstract List<ConnectionDetails> getDetailsFromFile()
			throws IOException, InvalidConnectionDetailsInFileException,
			InvalidDriverNameException, NumberFormatException,
			URISyntaxException, InvalidKeyException, NoSuchPaddingException,
			NoSuchAlgorithmException, BadPaddingException,
			IllegalBlockSizeException;

}