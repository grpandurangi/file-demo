/********************************************************************************************************
 * Copyright (c) 2017 Global Payments
 * All rights reserved. 
 ********************************************************************************************************/

package com.grp.sample.Utilities;

import org.apache.log4j.Logger;

/*
 * The Log class provides wrappers to apache log4j methods
 */

public class Log {

	/*
	 * Initialize Log4j logs
	 */

	private static Logger Log = Logger.getLogger(Log.class.getName());//

	/*
	 * This is to print log for the beginning of the test case, as we usually
	 * run so many test cases as a test suite
	 */

	public static void startTestCase(String sTestCaseName) {

		Log.info("****************************************************************************************");

		Log.info("****************************************************************************************");

		Log.info("$$$$$$$$$$$$$$$$$$$$$                 " + sTestCaseName + "       $$$$$$$$$$$$$$$$$$$$$$$$$");

		Log.info("****************************************************************************************");

		Log.info("****************************************************************************************");

	}

	/*
	 * This is to print log for the ending of the test case
	 */

	public static void endTestCase(String sTestCaseName) {

		Log.info("XXXXXXXXXXXXXXXXXXXXXXX             " + "-E---N---D-" + "             XXXXXXXXXXXXXXXXXXXXXX");

		Log.info("X");

		Log.info("X");

		Log.info("X");

		Log.info("X");

	}

	/*
	 * Wrapper for info method provided by Log4j
	 */

	public static void info(String message) {

		Log.info(message);

	}

	/*
	 * Wrapper for warn method provided by Log4j
	 */
	public static void warn(String message) {

		Log.warn(message);

	}

	/*
	 * Wrapper for error method provided by Log4j
	 */
	public static void error(String message) {

		Log.error(message);

	}

	/*
	 * Wrapper for fatal method provided by Log4j
	 */
	public static void fatal(String message) {

		Log.fatal(message);

	}

	/*
	 * Wrapper for debug method provided by Log4j
	 */
	public static void debug(String message) {

		Log.debug(message);

	}

}