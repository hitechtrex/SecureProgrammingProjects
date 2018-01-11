/**
 * ITS553 - Secure Java Programming
 * Professor: Dr. Tu
 * @author Steve Jia
 * @version 2017-06-28
 * @filename Homework2Tester.java
 * */

public class Homework2Tester {
	
	public static void main(String[] args){
		
		//Rule00 : String Normalization and Validation
		System.out.println("Rule 0: String Normalization and Validation:");
		new NormalizeValidateStrings().noncompliant();
		new NormalizeValidateStrings().compliant();
		System.out.println();
		
		//Rule01 : Declaration and Initialization
		System.out.println("Rule 1: Declaration and Initialization:");
		new ForLoopCollectionModification().noncompliant();
		new ForLoopCollectionModification().compliant();
		System.out.println();
		
		//Rule02 : Expressions
		System.out.println("Rule 2: Expressions:");
		new BoxedPrimitiveComparisons().noncompliant();
		new BoxedPrimitiveComparisons().compliant();
		System.out.println();
		
		//Rule03 : Numeric Types and Operations
		System.out.println("Rule 3: Numeric Types and Operations");
		new DivideByZeroExample().noncompliant();
		new DivideByZeroExample().compliant();
		System.out.println();
		
		//Rule04 : Characters and Strings
		System.out.println("Rule 4: Characters and Strings");
		new NonCharacterDataEncoding().noncompliant();
		new NonCharacterDataEncoding().compliant();
		
	}//end main
}//end class
