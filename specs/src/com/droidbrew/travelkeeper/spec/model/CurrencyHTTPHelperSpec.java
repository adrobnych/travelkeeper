package com.droidbrew.travelkeeper.spec.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.droidbrew.travelkeeper.model.entity.TKCurrency;
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
		
		Map<String, TKCurrency> cMap = currencyHTTPHelper.buildCurrencyMap(xmlString);
		
		assertEquals(8233000, cMap.get("UAH").getCourse());
		assertEquals("Ukrainian Hryvnia", cMap.get("UAH").getName());
		assertEquals("Hungarian Forint", cMap.get("HUF").getName());
		assertEquals(75, cMap.keySet().size());
	}
		

}
