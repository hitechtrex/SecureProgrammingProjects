import java.util.Arrays;
import java.util.List;

/**
 * ITS553 - Secure Java Programming
 * Professor: Dr. Tu
 * @author Steve Jia
 * @version 2017-06-28
 * @filename ForLoopCollectionModification.java
 * Description: for CERT Rule 01. Declaration and Initialization,
 * DCL02-J. Do not modify the collection's elements
 * during an enhanced for statement
 * */
public class ForLoopCollectionModification {
	
	private List<Float> list = Arrays.asList(new Float[]{1.5f, 2.0f, 3.0f});
	private float multiplier = 2.0f;
	
	public void noncompliant(){
		System.out.println("Non-Compliant Version: ");
		System.out.println("Printing List...");
		boolean first = true;
		for(Float single : list){
			if(first){
				first = false;
				//modifies the first item in the list
				single = new Float(single.floatValue() * multiplier);
			}
			//now the first item prints out differently, but the original
			// list is not modified
			System.out.println("Number In List: " + single.toString());
		}
		
		printList();
	}
	
	public void compliant(){
		System.out.println("Compliant Version: ");
		System.out.println("Printing List...");
		boolean first = true;
		//use 'final' to prevent items being modified during iterating
		for(final Float single : list){
			Float temp = single;
			if(first){
				first = false;
				temp = new Float(temp.floatValue() * multiplier);
			}
			System.out.println("Number In List: " + single.toString());
		}
		
		printList();
	}
	
	private void printList(){
		System.out.println("Has The List Been Modified?");
		for(Float single : list){
			System.out.println("Number in List: " + single.toString());
		}
	}
}
