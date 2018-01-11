import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ITS553 - Secure Java Programming
 * Professor: Dr. Tu
 * @author Steve Jia
 * @version 2017-06-28
 * @filename NormalizeValidateStrings.java
 * Description: for CERT Rule 00. Input Validation
 * and Data Sanitization, IDS01-J. Normalize strings
 * before validating them
 * */

public class NormalizeValidateStrings {
	private String userString = "10100101001" + "\uFE64" + "virus" + "\uFE65" + "0100101001";
	
	public void noncompliant(){
		//non-compliant is validate before normalize
		try{
			System.out.print("Non-Compliant Version: ");
			validate();
			normalize();
		}
		catch(IllegalStateException ise){
			System.out.println("Illegal State Exception; Non-compliant Input Found");
		}
	}//end Noncompliant
	
	public void compliant(){
		//compliant is to normalize before validate
		try{
			System.out.print("Compliant Version: ");
			normalize();
			validate();
		}
		catch(IllegalStateException ise){
			System.out.println("Illegal State Exception; Non-compliant Input Found");
		}
	}//end Compliant
	
	private void normalize(){
		this.userString = Normalizer.normalize(userString, Form.NFKC);
	}//end normalize
	
	private void validate(){
		Pattern pattern  = Pattern.compile("[<>]");
		Matcher matcher = pattern.matcher(this.userString);
		if(matcher.find()){
			throw new IllegalStateException();
		}
		else{
			System.out.println("Processing User String: " + this.userString);
		}
	}//end validate
}//end class
