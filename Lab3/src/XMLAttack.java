//package week1.xml_inject.sample;

import java.io.BufferedOutputStream;
import java.io.IOException;



public class XMLAttack extends Attack{
	
	@Override
	public void createXMLStream(BufferedOutputStream outStream, String pin) throws IOException{
		String xmlString;
		xmlString = "<passcode>\n\t<key>myPrivateKey</key>\n" +
					"\t<securitycode>57684</securitycode>\n" +
					"<pin>" + pin + "</pin></passcode>";
		outStream.write(xmlString.getBytes());
		outStream.flush();
	}
	
	
}





