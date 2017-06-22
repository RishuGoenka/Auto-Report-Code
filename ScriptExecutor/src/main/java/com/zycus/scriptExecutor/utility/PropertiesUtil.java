package com.zycus.scriptExecutor.utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Utility to get properties from a file.
 * 
 * @author harshvardhan.dudeja
 *
 */
public class PropertiesUtil {

	private static Map<String, Properties> propertyCache = new HashMap<String, Properties>();

	/**
	 * Simply loads the contents of the File(given fileName assumed to be in
	 * classpath). Also uses cache system to avoid loading properties again and
	 * again
	 * 
	 * @param fileName
	 *            is the absolute path to load the properties of a file
	 * @return properties object with contents of the file
	 * @throws IOException
	 *             when error occurs to load the properties
	 */
	public static Properties getProperties(String fileName) throws IOException {
		if (propertyCache.containsKey(fileName) == false) {
			InputStream inputStream = null;
			try {
				Properties properties = new Properties();
				inputStream = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream(fileName);
				if (inputStream != null) {
					properties.load(inputStream);
					propertyCache.put(fileName, properties);
				} else {
					throw new FileNotFoundException(
							"Database Details property File Not Found");
				}
			} finally {
				if (inputStream != null)
					inputStream.close();
			}
		}
		return propertyCache.get(fileName);
	}
}
