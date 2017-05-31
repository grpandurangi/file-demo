/********************************************************************************************************
 * Copyright (c) 2017 Global Payments
 * All rights reserved. 
 ********************************************************************************************************/

package com.grp.sample.fileparser.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/*
 * The AppHelper class provides method to convert error stack trace into string 
 * 
 */

public class AppHelper {

	final static java.util.logging.Logger LOG = java.util.logging.Logger
			.getLogger(AppHelper.class.getName());
	
	/*
	 * This method converts error stack trace to string
	 * @param exception
	 * @return error This is string value of converted stack trace
	 */
	
	public static String getStacktraceAsString(Exception e){
		
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		return errors.toString();
		
	}
}
