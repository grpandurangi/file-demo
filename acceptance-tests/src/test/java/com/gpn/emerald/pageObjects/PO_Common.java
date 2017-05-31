/********************************************************************************************************
 * Copyright (c) 2017 Global Payments
 * All rights reserved. 
 ********************************************************************************************************/

package com.grp.sample.pageObjects;

import java.io.IOException;
//import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import java.util.logging.Logger;;

//import com.grp.sample.Utilities.*;

/*
 * The PO_Common provides methods to initialize configuration and launch testing framework of application
 */

public class PO_Common {

	private static final Logger LOG = Logger.getLogger(PO_Common.class.getName());

	public WebDriver driver;

	/*
	 * Constructor
	 */

	public PO_Common(WebDriver driver) {
		this.driver = driver;
	}

	/*
	 * This method launch the application specified in URL
	 * 
	 * @param URL This is URL of application which should be tested
	 */

	public void appLaunch(String URL) throws IOException {
		try {
			// driver.get(GenericFunctions.readPropertyFile("appURL","Config"));
			driver.get(URL);
			driver.manage().timeouts().implicitlyWait(600, TimeUnit.SECONDS);
		} catch (Exception e) {
			LOG.info(e.getMessage().toString());
			LOG.info(e.getStackTrace().toString());
		}

	}

	/*
	 * This method stop testing framework execution and closes the browser
	 */
	public void browserClose() throws IOException {
		try {
			// driver.close();
			driver.quit();
		} catch (Exception e) {
			LOG.info(e.getMessage().toString());
			LOG.info(e.getStackTrace().toString());
		}
	}

	/*
	 * This method helps to identify the presence of element in UI
	 */
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

}
