import java.math.BigInteger;

/**
 * ITS553 - Secure Java Programming
 * Professor: Dr. Tu
 * @author Steve Jia
 * @version 2017-06-28
 * @filename NonCharacterDataEncoding.java
 * Description: for CERT Rule 04. Characters and Strings,
 * STR03-J. Do not encode noncharacter data as a string
 * */

public class NonCharacterDataEncoding {
	
	BigInteger x = new BigInteger("530500452766");
	
	public void noncompliant(){
		System.out.println("Non-Compliant:");
		System.out.println("Big Integer " + x.toString());
		//convert to byteArray first and then to string
		byte[] byteArray = x.toByteArray();
		String s = new String(byteArray);
		System.out.println("Converted To String: " + s);
		byteArray = s.getBytes();
		x = new BigInteger(byteArray);
		System.out.println("Converted Back To Big Integer: " + x.toString());
	}
	
	public void compliant(){
		System.out.println("Compliant:");
		System.out.println("Big Integer " + x.toString());
		//directly convert to string using the toString() method
		String s = x.toString();  // Valid character data
		System.out.println("Converted To String: " + s);
		byte[] byteArray = s.getBytes();
		String ns = new String(byteArray); 
		x = new BigInteger(ns);
		System.out.println("Converted Back To Big Integer: " + x.toString());
	}
}
