/********************************************************************************************************
 * Copyright (c) 2017 Global Payments
 * All rights reserved. 
 ********************************************************************************************************/

package com.grp.sample.fileparser.model;

/*
 * The StatusEnum sets status as OK, WARN and FATAL
 */

public enum StatusEnum {
	OK("OK"), WARN("WARN"), FATAL("FATAL");
	private String status;

	/*
	 * Construction to initialize status
	 */
	StatusEnum(String status) {
		this.status = status;
	}

	/*
	 * This method returns status set to one of the enum values
	 */
	public String status() {
		return status;
	}
}
