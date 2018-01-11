/**
 * ITS553 - Secure Java Programming
 * Professor: Dr. Tu
 * Lab 4
 * @author Steve Jia
 * @version 2017-07-06
 * @filename SubMethodInvoker.java
 * */

public class SubMethodInvoker extends MethodInvoker{
	@Override
	String getMethodName(){
		return "dangerousMethod";
	}//end getMethodName()
}
