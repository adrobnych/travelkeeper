package com.droidbrew.travelkeeper.spec.model;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;


import com.droidbrew.travelkeeper.model.manager.CurrencyHTTPHelper;

public class CurrencyHTTPHelperSpec {
	
	private CurrencyHTTPHelper currencyHTTPHelper = new CurrencyHTTPHelper();

	@Test
	public void itCanGetRemoteListOfAllCurrenciesAndCourses() {
		String response = currencyHTTPHelper.getRemoteFullListAsXMLString();
		//assertEquals("?", response);
		assertTrue(response.indexOf("USD/UAH") != -1);
	}
	
	@Test
	public void itCanTransformXMLStringToMap() {
		String xmlString = null;
		try {
			xmlString = FileUtils.readFileToString(new File("/home/adrobnych/dev/TravelKeeper/TravelCheap/assets/quote.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(xmlString.indexOf("USD/UAH") != -1);
	}
		

}
