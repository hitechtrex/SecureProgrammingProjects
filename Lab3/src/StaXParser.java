//package week1.xml_inject.sample;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;


public class StaXParser {
     static final String PASSCODE = "passcode";
     static final String KEY = "key";
     static final String SECURITYCODE = "securitycode";
     static final String PIN = "pin";
     

     @SuppressWarnings({ "unchecked", "null" })
     public List<PassCode> readConfig(String configFile) {
    	 
             List<PassCode> PassCodes = new ArrayList<PassCode>();
             try {
                     // First, create a new XMLInputFactory
                     XMLInputFactory inputFactory = XMLInputFactory.newInstance();
                     // Setup a new eventReader
                     InputStream in = new FileInputStream(configFile);
                     XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
                     // read the XML document
                     PassCode PassCode = null;

                     while (eventReader.hasNext()) {
                             XMLEvent event = eventReader.nextEvent();

                             if (event.isStartElement()) {
                                     StartElement startElement = event.asStartElement();
                                     // If we have an PassCode element, we create a new PassCode
                                     if (startElement.getName().getLocalPart().equals(PASSCODE)) {
                                             PassCode = new PassCode();
                                             // We read the attributes from this tag and add the date
                                             // attribute to our object
                                     }

                                     if (event.isStartElement()) {
                                             if (event.asStartElement().getName().getLocalPart()
                                                             .equals(KEY)) {
                                                     event = eventReader.nextEvent();
                                                     PassCode.setKey(event.asCharacters().getData());
                                                     continue;
                                             }
                                     }
                                     if (event.asStartElement().getName().getLocalPart()
                                                     .equals(SECURITYCODE)) {
                                             event = eventReader.nextEvent();
                                             PassCode.setSecurityCode(event.asCharacters().getData());
                                             continue;
                                     }

                                     if (event.asStartElement().getName().getLocalPart()
                                                     .equals(PIN)) {
                                             event = eventReader.nextEvent();
                                             PassCode.setPin(event.asCharacters().getData());
                                             continue;
                                     }
                             }
                             // If we reach the end of an PassCode element, we add it to the list
                             if (event.isEndElement()) {
                                     EndElement endElement = event.asEndElement();
                                     if (endElement.getName().getLocalPart().equals(PASSCODE)) {
                                             PassCodes.add(PassCode);
                                     }
                             }

                     }
             } catch (FileNotFoundException e) {
                     e.printStackTrace();
             } catch (XMLStreamException e) {
                     e.printStackTrace();
             }
             return PassCodes;
     }

}

