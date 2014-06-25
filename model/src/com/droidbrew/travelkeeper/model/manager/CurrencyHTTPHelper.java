package com.droidbrew.travelkeeper.model.manager;


import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.yaml.snakeyaml.Yaml;

import com.droidbrew.travelkeeper.model.entity.TKCurrency;

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

	public Map<String, TKCurrency> buildCurrencyMap(String xmlString, String cNames) {
		
		Map<String, TKCurrency> cMap = new HashMap<String, TKCurrency>();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		InputSource is;
		
		Map<String, String> namesDictionary = buildCurrencyNamesMap(cNames);

		try {
			builder = factory.newDocumentBuilder();
			is = new InputSource(new StringReader(xmlString));
			Document doc = builder.parse(is);
			NodeList list = doc.getElementsByTagName("resource");
			
			
			for(int i=0; i<list.getLength(); i++){
				if((list.item(i).getTextContent().indexOf("USD/") != -1) 
						&& (namesDictionary.get(list.item(i).getChildNodes().item(5).getTextContent().substring(0,3)) != null)){
					
					String currencyCode = list.item(i).getChildNodes().item(5).getTextContent().substring(0,3);
					String course = list.item(i).getChildNodes().item(3).getTextContent().replace(".", "");

					cMap.put(currencyCode,
							new TKCurrency(currencyCode,
									namesDictionary.get(currencyCode), //.getDisplayName(Locale.getDefault()),
									new Long(course), false, false));
					
				}
			}
				

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cMap;
	}
	

	@SuppressWarnings("unchecked")
	public Map<String, String> buildCurrencyNamesMap(String cnamesString) {
		Yaml yaml = new Yaml();
        return (Map<String, String>) yaml.load(cnamesString);
	}


}
