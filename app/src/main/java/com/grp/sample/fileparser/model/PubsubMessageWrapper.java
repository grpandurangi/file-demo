/********************************************************************************************************
 * Copyright (c) 2017 Global Payments
 * All rights reserved. 
 ********************************************************************************************************/

package com.grp.sample.fileparser.model;

import com.google.api.services.pubsub.model.PubsubMessage;

/*
 * The PubsubMessageWrapper implements getter and setter method for subscription and PubsubMessage.
 */

public class PubsubMessageWrapper {
	PubsubMessage message;
	String subscription;

	/*
	 * return pubsub message
	 */
	public PubsubMessage getMessage() {
		return message;
	}

	/*
	 * This method set incoming pubsub message
	 * 
	 * @param message Incoming message of type PubsubMessage
	 */
	public void setMessage(PubsubMessage message) {
		this.message = message;
	}

	/*
	 * This method retrieves subscription name to be used
	 */
	public String getSubscription() {
		return subscription;
	}

	/*
	 * This method set subscription name
	 * 
	 * @param subscrition
	 */
	public void setSubscription(String subscription) {
		this.subscription = subscription;
	}
}
