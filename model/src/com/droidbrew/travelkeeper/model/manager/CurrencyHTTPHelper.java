package com.droidbrew.travelkeeper.model.manager;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.commons.io.IOUtils;



public class CurrencyHTTPHelper {

	private String url = "http://finance.yahoo.com/webservice/v1/symbols/allcurrencies/quote";
	private static HttpClient httpClient = new DefaultHttpClient();

	public String getRemoteFullListAsXMLString() {


		HttpGet request = new HttpGet(url);
		HttpResponse get_response = null;
		try {
			get_response = httpClient.execute(request);
		}   catch (IOException e) {
			e.printStackTrace();
		}

		InputStream iStream = null;
		String result = null;

		try {
			iStream = get_response.getEntity().getContent();
			result = IOUtils.toString(iStream, "UTF-8");
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}


}
