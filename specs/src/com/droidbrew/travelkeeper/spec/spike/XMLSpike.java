package com.droidbrew.travelkeeper.spec.spike;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLSpike {

	@Test
	public void test() {
		String xml= 
				"<list version=\"1.0\">" +
				"<meta>" +
				"<type>resource-list</type>" +
				"</meta>" +
				"<resources start=\"0\" count=\"168\">" +
				"<resource classname=\"Quote\">" +
				"<field name=\"name\">USD/KRW</field>" +
				"<field name=\"price\">1063.060059</field>" +
				"<field name=\"symbol\">KRW=X</field>" +
				"<field name=\"ts\">1385589920</field>" +
				"<field name=\"type\">currency</field>" +
				"<field name=\"utctime\">2013-11-27T22:05:20+0000</field>" +
				"<field name=\"volume\">0</field>" +
				"</resource><resource classname=\"Quote\">" +
				"<field name=\"name\">SILVER 1 OZ 999 NY</field>" +
				"<field name=\"price\">0.050935</field>" +
				"<field name=\"symbol\">XAG=X</field>" +
				"<field name=\"ts\">1385584940</field>" +
				"<field name=\"type\">currency</field>" +
				"<field name=\"utctime\">2013-11-27T20:42:20+0000</field>" +
				"<field name=\"volume\">123</field>" +
				"</resource><resource classname=\"Quote\">" +
				"<field name=\"name\">USD/VND</field>" +
				"<field name=\"price\">21101.000000</field>" +
				"<field name=\"symbol\">VND=X</field>" +
				"<field name=\"ts\">1385589920</field>" +
				"<field name=\"type\">currency</field>" +
				"<field name=\"utctime\">2013-11-27T22:05:20+0000</field>" +
				"<field name=\"volume\">0</field>" +
				"</resource><resource classname=\"Quote\">" +
				"<field name=\"name\">USD/BOB</field>" +
				"<field name=\"price\">6.910000</field>" +
				"<field name=\"symbol\">BOB=X</field>" +
				"<field name=\"ts\">1385589620</field>" +
				"<field name=\"type\">currency</field>" +
				"<field name=\"utctime\">2013-11-27T22:00:20+0000</field>" +
				"<field name=\"volume\">0</field>" +
				"</resource></resources>" +
				"</list>" +
				"<!-- iapi16.finance.bf1.yahoo.com compressed/chunked Wed Nov 27 14:09:24 PST 2013 -->";
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		InputSource is;

		try {
			builder = factory.newDocumentBuilder();
			is = new InputSource(new StringReader(xml));
			Document doc = builder.parse(is);
			NodeList list = doc.getElementsByTagName("resource");
			assertEquals("USD/KRW1063.060059KRW=X1385589920currency2013-11-27T22:05:20+00000", 
					list.item(0).getTextContent());
			assertEquals("USD/VND", list.item(2).getChildNodes().item(0).getTextContent());
			assertEquals("21101.000000", list.item(2).getChildNodes().item(1).getTextContent());

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

	}

}
