/********************************************************************************************************
 * Copyright (c) 2017 Global Payments
 * All rights reserved. 
 ********************************************************************************************************/

package com.grp.sample.fileparser.rest.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.grp.sample.fileparser.exception.NotFoundException;

/*
 * The StatusApiService interface define methods to check the health of application 
 */

public interface StatusApiService {
  public Response getStatus(String message, SecurityContext securityContext)
      throws NotFoundException;
 /*
  * method definition to respond requesting client with application name
  * @exception NotFoundException this is custom exception that throws message to super class Exception
  */
  public Response getStatus(SecurityContext securityContext) throws NotFoundException;
}
