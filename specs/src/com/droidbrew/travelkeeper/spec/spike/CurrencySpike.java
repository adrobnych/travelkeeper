package com.droidbrew.travelkeeper.spec.spike;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class CurrencySpike {

	public static Map<String, Currency> getAllCurrencies()
    {
        Map<String, Currency> currencyDictionary = new HashMap<String, Currency>();
        Locale[] locs = Locale.getAvailableLocales();

        for(Locale loc : locs) {

            	try {
					Currency currency = Currency.getInstance(loc);
					currencyDictionary.put(currency.getCurrencyCode(), currency);
				} catch (Exception e) {
					// hide a lot of exceptions 
					//e.printStackTrace();
				}

        }

        return currencyDictionary;
    }
	
	@Test
	public void testAllCurrencies() {
		Map<String, Currency> currencies = getAllCurrencies();
		//Currency c = currencies.iterator().next();
		//assertEquals("?", c.getDisplayName());
		//assertEquals("?", c.getCurrencyCode());
		assertEquals("Ukrainian Hryvnia", currencies.get("UAH").getDisplayName());
	}


}
