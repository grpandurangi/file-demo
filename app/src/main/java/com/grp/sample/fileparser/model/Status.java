/********************************************************************************************************
 * Copyright (c) 2017 Global Payments
 * All rights reserved. 
 ********************************************************************************************************/

package com.grp.sample.fileparser.model;

/*
 * The Status class provides getter and setter methods to convey response status to requesting client
 */

public class Status {

	private String statusValue;
	private String name;

	/*
	 * This method helps in retrieving name
	 */
	public String getName() {
		return name;
	}

	/*
	 * This method set name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * default constructor
	 */
	public Status() {
	}

	public Status(String statusValue) {
		this.statusValue = statusValue;
	}

	/*
	 * returns the status value
	 */
	public String getStatusValue() {
		return statusValue;
	}

	/*
	 * This method set the status value
	 */
	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}
}
