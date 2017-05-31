/********************************************************************************************************
 * Copyright (c) 2017 Global Payments
 * All rights reserved. 
 ********************************************************************************************************/

package com.grp.sample.Utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/*
 * The GenericFunctions will be used as utility to provide operations such as reading property files
 */

public class GenericFunctions {
	/*
	 * This method used to retrieve and initialize properties from framework and
	 * object property file.
	 * 
	 * @param propertyName This is key of property
	 * 
	 * @param propertyType This is type of property that specifies whether it is
	 * object or config
	 * 
	 */
	private static final Logger LOG = Logger.getLogger(GenericFunctions.class.getName());

	@SuppressWarnings("unused")
	public static String readPropertyFile(String propertyName, String propertyType) {
		String propertyValue = null;
		Properties prop = new Properties();
		InputStream input = null;

		try {

			if (propertyType == "Object")

			{

				String filename = System.getProperty("user.dir") + "//src//test//resources//objProp.properties";
				// input =
				// App.class.getClassLoader().getResourceAsStream(filename);
				input = new FileInputStream(filename);

				if (input == null) {
					LOG.info("Sorry, unable to find " + filename);
					return propertyValue;
				}

				// load a properties file from class path, inside static method
				prop.load(input);
				propertyValue = prop.getProperty(propertyName);
				// get the property value and print it out
				LOG.info(prop.getProperty(propertyName));
			}

			else if (propertyType == "Config")

			{

				String filename = System.getProperty("user.dir") + "/src/test/resources/framework.properties";
				// input =
				// App.class.getClassLoader().getResourceAsStream(filename);
				input = new FileInputStream(filename);

				if (input == null) {
					LOG.info("Sorry, unable to find " + filename);
					return propertyValue;
				}

				// load a properties file from class path, inside static method
				prop.load(input);
				propertyValue = prop.getProperty(propertyName);
				// get the property value and print it out
				LOG.info(prop.getProperty(propertyName));
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return propertyValue;
	}
}
