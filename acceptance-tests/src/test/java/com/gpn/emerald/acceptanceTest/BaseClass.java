/********************************************************************************************************
 * Copyright (c) 2017 Global Payments
 * All rights reserved. 
 ********************************************************************************************************/

package com.grp.sample.acceptanceTest;

import java.io.IOException;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.grp.sample.pageObjects.*;

/*
 * The BaseClass defines the test flow by maintaining environment setup before running tests and cleaning up after completion of tests
 */

public class BaseClass {
	public static WebDriver driver;
	PO_Common Common;
	// public static Logger logger = Logger.getLogger(Log.class.getName());

	/*
	 * This method initialize environment for running tests
	 */
	@BeforeTest
	public void beforeTest() throws IOException

	{
		DOMConfigurator.configure("log4j.xml");
		driver = browserSetup("IE");
		Common = new PO_Common(driver);
		Common.appLaunch("http://www.google.com/");

	}

	/*
	 * This method cleans up environment after test suite completes
	 */
	@AfterTest
	public void afterTest() throws IOException {
		Common = new PO_Common(driver);
		Common.browserClose();

	}

	/*
	 * This method provides configuration for browser based setup
	 * 
	 * @param browserType This value specifies the browser type
	 */

	public static WebDriver browserSetup(String browserType) throws IOException {

		try {
			switch (browserType)

			{

			case "IE":

				System.setProperty("webdriver.ie.driver", "C:\\eclipse\\IEDriverServer\\IEDriverServer.exe");
				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
				ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
				// ieCapabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,
				// true);
				ieCapabilities.setCapability("requireWindowFocus", true);
				driver = new InternetExplorerDriver(ieCapabilities);
				driver.manage().window().maximize();
				break;

			case "Firefox":
				System.setProperty("webdriver.firefox.marionette", "C:\\eclipse\\geckodriver.exe");
				driver = new FirefoxDriver();
				driver.manage().window().maximize();

				break;

			case "Chrome":
				System.setProperty("webdriver.chrome.driver", "C:\\eclipse\\chromedriver_win32\\chromedriver.exe");
				driver = new ChromeDriver();
				driver.manage().window().maximize();
				break;

			}

		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
			System.out.println(e.getStackTrace().toString());
		}

		return driver;
	}

}
