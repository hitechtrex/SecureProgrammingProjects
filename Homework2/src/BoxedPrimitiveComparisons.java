import java.util.Comparator;
/**
 * ITS553 - Secure Java Programming
 * Professor: Dr. Tu
 * @author Steve Jia
 * @version 2017-06-28
 * @filename BoxedPrimitiveComparisons.java
 * Description: for CERT Rule 02. Expressions,
 * EXP03-J. do not use the equality operators when comparing
 * values of boxed primitives
 * */

public class BoxedPrimitiveComparisons {
	
	private Float number1 = new Float(1.5f);
	private Float number2 = new Float(1.5f);
	
	public void noncompliant(){
		Comparator<Float> comp = new Comparator<Float>(){
			public int compare(Float i, Float j){
				//the == comparison in this case actually compares the
				//  objects instead of the values
				return i < j ? -1 : (i == j ? 0 : 1);
			}
		};//end comparator
		System.out.println("Non-Compliant Version:");
		System.out.println("Comparing " + this.number1.toString() + 
							" with " + this.number2.toString());
		System.out.println("Comparison Result is: " + comp.compare(this.number1, this.number2));
	}//end noncompliant()
	
	public void compliant(){
		Comparator<Float> comp = new Comparator<Float>(){
			public int compare(Float i, Float j){
				//this way the comparison is always of the values
				return i < j ? -1 : (i > j ? 1 : 0);
			}
		};//end comparator 
		System.out.println("Compliant Version:");
		System.out.println("Comparing " + this.number1.toString() + 
				" with " + this.number2.toString());
		System.out.println("Comparison Result is: " + comp.compare(this.number1, this.number2));
	}//end compliant()
}
