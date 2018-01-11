//package week1.xml_inject.schema;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

//import week1.xml_inject.sample.Attack;

public class CustomResolver extends Attack implements EntityResolver {
	// public void run(BufferedOutputStream outStream,
	// String quantity) throws IOException {
	// createXMLStream(outStream, quantity);
	// }
	//
	// @SuppressWarnings("unused")
	// private void createXMLStream(BufferedOutputStream outStream,
	// String quantity) throws IOException {
	// String xmlString;
	// xmlString = "<item>\n<description>Widget</description>\n" +
	// "<price>500.0</price>\n" +
	// "<quantity>" + quantity + "</quantity></item>";
	//
	// }

	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createXMLStream(BufferedOutputStream outStream, String pin) throws IOException {
		String xmlString;
		xmlString = "<passcode>\n\t<key>myPrivateKey</key>\n" +
				"\t<securitycode>57684</securitycode>\n" +
				"<pin>" + pin + "</pin></passcode>";
		InputSource xmlStream = new InputSource(new StringReader(xmlString));
		// Build a validating SAX parser using our schema
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		DefaultHandler defHandler = new DefaultHandler() {
			public void warning(SAXParseException s) throws SAXParseException {
				throw s;
			}

			public void error(SAXParseException s) throws SAXParseException {
				throw s;
			}

			public void fatalError(SAXParseException s) throws SAXParseException {
				throw s;
			}
		};
		StreamSource ss = new StreamSource(new File("schema.xsd"));
		try {
			Schema schema = sf.newSchema(ss);
			SAXParserFactory spf = SAXParserFactory.newInstance();
			spf.setSchema(schema);
			SAXParser saxParser = spf.newSAXParser();
			// To set the custom entity resolver,
			// an XML reader needs to be created
			XMLReader reader = saxParser.getXMLReader();
			reader.setEntityResolver(new CustomResolver());
			saxParser.parse(xmlStream, defHandler);
		} catch (ParserConfigurationException x) {
			throw new IOException("Unable to validate XML", x);
		} catch (SAXException x) {
			throw new IOException("Invalid pin", x);
		}
		// Our XML is valid, proceed
		outStream.write(xmlString.getBytes());
		outStream.flush();
	}
}
