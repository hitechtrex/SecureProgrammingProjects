//package week1.xml_inject.inputValidation;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

//import week1.xml_inject.sample.Attack;

public class DefendbyWhitelisting extends Attack{
	
	@Override
	public void createXMLStream(BufferedOutputStream outStream, String pin) throws IOException{
		String key = "";
		String securityCode = "";
		if(!Pattern.matches("[0-9]", pin)){
			System.out.println("attack detected!");
			key = "***";
			securityCode="***";
			pin = "***";
		}
		String xmlString;
		xmlString = "<passcode>\n<key>"+key+"</key>\n" +
					"<securitycode>"+securityCode+"</securitycode>\n" +
					"<pin>" + pin + "</pin></passcode>";
		outStream.write(xmlString.getBytes());
		outStream.flush();
	}
}
