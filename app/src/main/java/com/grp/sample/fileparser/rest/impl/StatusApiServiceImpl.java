/********************************************************************************************************
 * Copyright (c) 2017 Global Payments
 * All rights reserved. 
 ********************************************************************************************************/

package com.grp.sample.fileparser.rest.impl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.grp.sample.fileparser.exception.NotFoundException;
import com.grp.sample.fileparser.model.ApplicationConstants;
import com.grp.sample.fileparser.model.Status;
import com.grp.sample.fileparser.model.StatusEnum;
import com.grp.sample.fileparser.rest.api.StatusApiService;

import java.util.logging.Logger;


/*
 * The StatusApiServiceImpl class implements methods defined by StatusApiService 
 */



@Path("/status")
public class StatusApiServiceImpl implements StatusApiService {

private static final Logger logger=Logger.getLogger("StatusApiServiceImpl");
	/*
	 * Overloaded method that respond to incoming request with application name
	 * 
	 * @param no argument
	 * 
	 * @return JSON this is JSON containing application name
	 */
	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@JacksonFeatures(serializationEnable =  { SerializationFeature.INDENT_OUTPUT })
	public Response getStatus(SecurityContext securityContext) throws NotFoundException {
		Status status = new Status();
		status.setStatusValue(StatusEnum.OK.status());
                logger.info("Welcome to getStatus method ");
		status.setName(ApplicationConstants.APPLICATION_NAME);
		return Response.status(Response.Status.OK).entity(status).type(MediaType.APPLICATION_JSON).build();

	}

	/*
	 * This method retrieves incoming message and respond it with application
	 * name and entered message as JSON
	 * 
	 * @param message This is the message sent by a client in request body
	 * 
	 * @return response This is JSON response contains application name,
	 * received message
	 * 
	 * @exception 400 If incoming message is NULL
	 */

	@Override
	@GET
	@Path("/query")
	@Produces(MediaType.APPLICATION_JSON)
	@JacksonFeatures(serializationEnable =  { SerializationFeature.INDENT_OUTPUT })
	public Response getStatus(@QueryParam("message") String message, SecurityContext securityContext)
			throws NotFoundException {
		Status status = new Status();
                logger.info("Welcome to getStatus message method ");
		if (null == message) {
			status.setName(
					ApplicationConstants.APPLICATION_NAME + " - " + ApplicationConstants.MISSING_MESSAGE_PARAMETER);
			status.setStatusValue(StatusEnum.WARN.status());
			return Response.status(Response.Status.BAD_REQUEST).entity(status).type(MediaType.APPLICATION_JSON).build();
		} else {
			status.setName(ApplicationConstants.APPLICATION_NAME + " - " + ApplicationConstants.MESSAGE_ENTERED + ":"
					+ message);
			status.setStatusValue(StatusEnum.OK.status());
			return Response.status(Response.Status.OK).entity(status).type(MediaType.APPLICATION_JSON).build();
		}
	}

}
