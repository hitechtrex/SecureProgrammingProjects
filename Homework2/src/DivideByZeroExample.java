/**
 * ITS553 - Secure Java Programming
 * Professor: Dr. Tu
 * @author Steve Jia
 * @version 2017-06-28
 * @filename DivideByZeroExample.java
 * Description: for CERT Rule 03. Numeric Types and Operations,
 * NUM02-J. ensure that division and remainder operations do not
 * result in divide-by-zero errors
 * */
public class DivideByZeroExample {
	private long num1 = 10;
	private long num2 = 0;
	
	public void noncompliant(){
		System.out.println("Non-Compliant:");
		System.out.println("Performing MOD of " + this.num1 + " and " + this.num2);
		try{
			long result = this.num1 % this.num2;
			System.out.println(result);
		}
		catch(Exception ex){
			System.out.println("Unhandled Exception " + ex.getMessage());
		}
	}
	
	public void compliant(){
		System.out.println("Compliant:");
		System.out.println("Performing MOD of " + this.num1 + " and " + this.num2);
		//check for divide by zero
		if(this.num2 == 0){
			System.out.println("Cannot Perform MOD on 0");
		}
		else{
			long result = this.num1 % this.num2;
			System.out.println(result);
		}
	}
}
