/********************************************************************************************************
 * Copyright (c) 2017 Global Payments
 * All rights reserved. 
 ********************************************************************************************************/

package com.grp.sample.acceptanceTest.FileParserFT;

import java.io.IOException;

import org.apache.log4j.Logger;
//import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.grp.sample.Utilities.GenericFunctions;
//import com.grp.sample.Utilities.Log;
//import test.java.com.grp.sample.Utilities.*;
import com.grp.sample.pageObjects.PO_REST;

/*
 * The FileParser_TS1 contains method to test REST service
 */
public class FileParser_TS1 {

	public static WebDriver driver;
	PO_REST REST;
	public static Logger logger = Logger.getLogger(Logger.class.getName());

	/*
	 * This method read property file to get URL and message and invoke test
	 * method
	 */

	@Test
	public void SendMessageFunctionalTest() throws IOException {

		try {
			//Initializing the WebDriver
			REST = new PO_REST(driver);
			
			//Retrieving StatusMessage GET API link and User Provided Message from framework.properties file
			String GetURL = GenericFunctions.readPropertyFile("restGetURL", "Config");
			String Message = GenericFunctions.readPropertyFile("Message", "Config");
			
			//-----Functional Testing of StatusMessage GET API---//
			
			//Invoke StatusMessage GET API, Retrieve the response to JSON object and validate against user provided message
			REST.Test_Get1_JSON(GetURL, Message);

		} catch (Exception e) {
			logger.error(e.getMessage().toString());
			logger.error(e.getStackTrace().toString());
		}

	}

}
