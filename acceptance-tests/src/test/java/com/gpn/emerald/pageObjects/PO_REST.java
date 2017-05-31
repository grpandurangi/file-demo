/********************************************************************************************************
 * Copyright (c) 2017 Global Payments
 * All rights reserved. 
 ********************************************************************************************************/

package com.grp.sample.pageObjects;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.grp.sample.Utilities.GenericFunctions;
import com.grp.sample.Utilities.Log;

/*
 * 
 */

/*
 * The PO_REST class contains method to test Http methods such as GET, POST, PUT hosted on server application
 */
public class PO_REST {
	private static final Logger LOG = Logger.getLogger(PO_REST.class.getName());

	public WebDriver driver;
	public static Logger logger = Logger.getLogger(Log.class.getName());

	public PO_REST(WebDriver driver) {
		this.driver = driver;
	}

	/*
	 * This method test GET request service implemented on server specified by
	 * URL
	 * 
	 * @param URL This is the url of server application hosting REST service
	 * 
	 * @param message This is string message passed as query parameter in GET
	 * request
	 */
	public void Test_Get1_JSON(String URL, String Message) throws Exception {

		// String url = "https://reqres.in/api/users/2";
		String USER_AGENT = "Mozilla/5.0";

		CloseableHttpClient client = HttpClientBuilder.create().build();

		String mURL = URL + Message;

		HttpGet request = new HttpGet(mURL);

		// add request header
		request.addHeader("User-Agent", USER_AGENT);

		HttpResponse response = client.execute(request);

		LOG.info("\nSending 'GET' request to URL : " + mURL);
		LOG.info("Response Code : " + response.getStatusLine().getStatusCode());

		/*
		 * BufferedReader rd = new BufferedReader(new
		 * InputStreamReader(response.getEntity().getContent()));
		 * 
		 * 
		 * StringBuffer result = new StringBuffer(); String line = ""; while
		 * ((line = rd.readLine()) != null) { result.append(line); }
		 * 
		 * LOG.info(result.toString());
		 */

		String getResult = EntityUtils.toString(response.getEntity());

		JSONObject jsonObject = null;

		try {
			jsonObject = (JSONObject) new JSONTokener(getResult).nextValue();
			// JSONObject jsonObject1 = jsonObject.getJSONObject("data");
			
			//Retrieving Actual State and Message from JSON response
			String ActualState = jsonObject.getString("statusValue");
			String ActualMessage = jsonObject.getString("name");

			//Writing the response details to logger
			LOG.info(response.getStatusLine());
			LOG.info("successful Response");
			LOG.info("statusValue " + ActualState);
			LOG.info("name " + ActualMessage);
			
			//The Expected State and Message reading from property file and assigning to variables
			String ExpectedState = GenericFunctions.readPropertyFile("statusValue", "Config");
			String ExpectedMessage = GenericFunctions.readPropertyFile("Message", "Config");
			
			//The Actual State, Message validation with Expected State, Message
			Assert.assertEquals(ActualState,ExpectedState);
			Assert.assertTrue(ActualMessage.contains(ExpectedMessage));

		} catch (JSONException jsonException) {
			// Handle JSON exception
		}

	}

}
