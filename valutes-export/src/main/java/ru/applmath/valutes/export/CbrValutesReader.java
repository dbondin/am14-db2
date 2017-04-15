package ru.applmath.valutes.export;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class CbrValutesReader implements ValutesReader {

	// http://www.cbr.ru/scripts/XML_daily.asp?date_req=09/04/2017

	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

	private String getUrl(Date d) {
		return "http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + // 09/04/2017
				DATE_FORMAT.format(d);
	}

	private List<Valute> parse(InputStream is) {
		
		final List<Valute> result = new ArrayList<Valute>();
		
		try {
			XMLReader xmlr = XMLReaderFactory.createXMLReader();
			InputSource isrc = new InputSource(is);
			xmlr.setContentHandler(new DefaultHandler() {
				
				private Valute currentValute = null;
				private StringBuilder sb = new StringBuilder();
				
				@Override
				public void startElement(String uri, String localName, String qName, Attributes attributes)
						throws SAXException {
					//System.out.println(">> " + localName);
					if("Valute".equals(localName)) {
						currentValute = new Valute();
						currentValute.setId(attributes.getValue("ID"));
						result.add(currentValute);
					}
					sb.setLength(0);
				}
				
				@Override
				public void endElement(String uri, String localName, String qName) throws SAXException {
					if("CharCode".equals(localName)) {
						currentValute.setCharCode(sb.toString());
					}
					else if("Name".equals(localName)) {
						currentValute.setName(sb.toString());
					}
					else if("Value".equals(localName)) {
						currentValute.setValue(Double.parseDouble(sb.toString().replace(',', '.')));
					}
				}
				
				@Override
				public void characters(char[] ch, int start, int length) throws SAXException {
					sb.append(ch, start, length);
				}
			});
			xmlr.parse(isrc);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
		
		return result;
	}
	
	public List<Valute> getValutes(Date d) {

		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(getUrl(d));

			// add request header
			request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0");
			HttpResponse response = client.execute(request);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new Exception("Bad response from server " + response.getStatusLine().getStatusCode() + " : "
						+ response.getStatusLine().getReasonPhrase());
			}

			InputStream is = response.getEntity().getContent();
//			while (true) {
//				int b = is.read();
//				if (b == -1) {
//					break;
//				}
//				System.out.write(b);
//			}

			return parse(is);
			
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
