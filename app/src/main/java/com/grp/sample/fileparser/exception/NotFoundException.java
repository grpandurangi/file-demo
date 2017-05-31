/********************************************************************************************************
 * Copyright (c) 2017 Global Payments
 * All rights reserved. 
 ********************************************************************************************************/

package com.grp.sample.fileparser.exception;

/*
 * The NotFoundException helps to catch exception thrown by classes across application
 */

@SuppressWarnings("serial")
public class NotFoundException extends Exception {

	/*
	 * constructor
	 */
	public NotFoundException() {
		super();
	}

	public NotFoundException(String message) {
		super(message);
	}
}
