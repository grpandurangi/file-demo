/********************************************************************************************************
 * Copyright (c) 2017 Global Payments
 * All rights reserved. 
 ********************************************************************************************************/

package com.grp.sample.fileparser.queuehandlers;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.grp.sample.fileparser.model.PubsubMessageWrapper;

/*
 * The PushHandler program exposes REST services to interact with google pubsub 
 */

@Path("/push-handlers")
public class PushHandler {

	private Logger log = Logger.getLogger(PushHandler.class.getName());

	/*
	 * This method accepts POST request from client and log received message.
	 * And returns 200 if message parsing succeeded.
	 * 
	 * @param pmessage This is object of type PubsubMessageWrapper which
	 * contains message and subscription details
	 * 
	 * @return 200 - Http status OK
	 * 
	 * @exception UnsupportedEncodingException on decoding message using UTF-8
	 */

	@POST
	@Path("/file-placed")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response receive(PubsubMessageWrapper pmessage) throws UnsupportedEncodingException {
		Gson gson = new Gson();
		log.info("Received message from in topic : " + gson.toJson(pmessage));
		String messageBody = new String(pmessage.getMessage().decodeData(), "UTF-8");
		log.info("Received data : " + messageBody);
		return Response.status(200).build();
	}
}
