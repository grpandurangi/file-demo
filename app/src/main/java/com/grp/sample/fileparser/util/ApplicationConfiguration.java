/********************************************************************************************************
 * Copyright (c) 2017 Global Payments
 * All rights reserved. 
 ********************************************************************************************************/

package com.grp.sample.fileparser.util;

import java.io.IOException;
import java.util.Properties;

import com.google.gson.Gson;

/*
 * The ApplicationConfiguration class provides methods to read property file
 * and creation of JSON object
 */

public class ApplicationConfiguration {

	final static java.util.logging.Logger LOG = java.util.logging.Logger
			.getLogger(ApplicationConfiguration.class.getName());
	private static Properties properties;
	private static Object prop_lock = new Object();

	/*
	 * This method read property file from resource folder and initialize value
	 * that could be retrieved using key
	 * @return properties
	 */

	// Lazy Initialization
	private static Properties getConfiguration() throws IOException {
		if (properties == null) {
			synchronized (prop_lock) {
				if (properties == null) {
					properties = new Properties();
					properties.load(ApplicationConfiguration.class.getClassLoader()
							.getResourceAsStream("appconfig.properties"));
				}
			}
		}
		return properties;
	}
	
	/*
	* This method helps to retrieves value by specifying key
	* @param key This is input key
	* @return value This is the value corresponding to key passed as input
	*/
	private static String getValue(String key) {
		try {
			return getConfiguration().getProperty(key);
		} catch (IOException e) {
			return "";
		}
	}

	// list out the properties
	public static final String APPLICATION_NAME = getValue("APPLICATION_NAME");
	public static final String PROJECT_ID = getValue("PROJECT_ID");
	public static final Long DEFAULT_WAIT_TIME = Long.valueOf(getValue("DEFAULT_WAIT_TIME"));

	
	/*
	 * This method constructs POJO object from JSON string
	 * @param jsoninput this is stringified JSON
	 * @return object This is the POJO object generated using gson library
	 */
	public static Object loadObjectFromJSONString(String jsonInput, @SuppressWarnings("rawtypes") Class clazz) {
		Object POJOObject = null;
		try {
			LOG.severe("JSON INput while converting json input to POJO" + jsonInput);

			POJOObject = buildObjectFromJSON(jsonInput, clazz);
			LOG.severe("POJO Object is " + POJOObject);
		} catch (Exception e) {
			LOG.severe("Error occured while converting json input to POJO");
			LOG.severe(AppHelper.getStacktraceAsString(e));
		}
		return POJOObject;
	}

	/*
	 * This method uses gson library to generate JSON
	 */
	@SuppressWarnings("unchecked")
	private static Object buildObjectFromJSON(String jsonInput, @SuppressWarnings("rawtypes") Class clazz) {
		Object POJOObject = (new Gson()).fromJson(jsonInput, clazz);
		return POJOObject;
	}
}
