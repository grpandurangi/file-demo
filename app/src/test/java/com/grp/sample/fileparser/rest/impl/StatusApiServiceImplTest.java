package com.grp.sample.fileparser.rest.impl;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;

import org.mockito.runners.MockitoJUnitRunner;

import com.grp.sample.fileparser.model.ApplicationConstants;
import com.grp.sample.fileparser.model.Status;
import com.grp.sample.fileparser.model.StatusEnum;
import com.grp.sample.fileparser.rest.api.StatusApiService;

@RunWith(MockitoJUnitRunner.class)
public class StatusApiServiceImplTest {

	final static java.util.logging.Logger LOG = java.util.logging.Logger
			.getLogger(StatusApiServiceImplTest.class.getName());

	@Test
	public void getStatus_checkStatusCode() throws Exception {
		StatusApiService statusApiService = new StatusApiServiceImpl();
		SecurityContext securityContextMock = mock(SecurityContext.class);
		Response response = statusApiService.getStatus(securityContextMock);
		assertEquals(200, response.getStatus());
		assertEquals(Status.class, response.getEntity().getClass());
		assertEquals(ApplicationConstants.APPLICATION_NAME,
				((Status) response.getEntity()).getName());
		assertEquals(StatusEnum.OK.status(),
				((Status) response.getEntity()).getStatusValue());
	}

	@Test
	public void getStatus_checkNullQueryParmeterMessage() throws Exception {
		StatusApiService statusApiService = new StatusApiServiceImpl();
		SecurityContext securityContextMock = mock(SecurityContext.class);
		// Message passed is null
		String message = null;
		Response response = statusApiService.getStatus(message,
				securityContextMock);
		assertEquals(400, response.getStatus());
		assertEquals(Status.class, response.getEntity().getClass());
		assertEquals(ApplicationConstants.APPLICATION_NAME + " - "
				+ ApplicationConstants.MISSING_MESSAGE_PARAMETER,
				((Status) response.getEntity()).getName());
		assertEquals(StatusEnum.WARN.status(),
				((Status) response.getEntity()).getStatusValue());
	}

	@Test
	public void getStatus_checkValidQueryParmeterMessage() throws Exception {
		StatusApiService statusApiService = new StatusApiServiceImpl();
		SecurityContext securityContextMock = mock(SecurityContext.class);
		String message = " This is a sample test message";
		Response response = statusApiService.getStatus(message,
				securityContextMock);
		assertEquals(200, response.getStatus());
		assertEquals(Status.class, response.getEntity().getClass());
		assertEquals(ApplicationConstants.APPLICATION_NAME + " - "
				+ ApplicationConstants.MESSAGE_ENTERED + ":" + message,
				((Status) response.getEntity()).getName());
		assertEquals(StatusEnum.OK.status(),
				((Status) response.getEntity()).getStatusValue());
	}

}
