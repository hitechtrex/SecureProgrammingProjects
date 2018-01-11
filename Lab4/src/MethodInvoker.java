/**
 * ITS553 - Secure Java Programming
 * Professor: Dr. Tu
 * Lab 4
 * @author Steve Jia
 * @version 2017-07-06
 * @filename MethodInvoker.java
 * */

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class MethodInvoker{
	public void invokeMethod(){
		AccessController.doPrivileged(
				new PrivilegedAction<Object>(){
					public Object run(){
						try{
							Class<?> thisClass = MethodInvoker.class;
							String methodName = getMethodName();
							
							/*Steve Jia: check the class type to be MethodInvoker
							/* to prevent subclasses from being used, thus 
							 * preventing overriden methods from being called*/
							Class<?> c = getClass();
							if(c != MethodInvoker.class){
								throw new Exception();
							}
							
							//Steve Jia: check if methodName is the expected name
							// to prevent attack
							if(!methodName.equals("someMethod")){
								throw new Exception();
							}
							
							/*Steve Jia: simply disallow polymorphism by calling
							/* the correct method; this did not work for me,
							 * MethodInvoker.this.getMethodName() always calls
							 * the subclass method when it's overriden*/
							//String methodName = MethodInvoker.this.getMethodName();
							
							Method method = thisClass.getMethod(methodName, null);
							method.invoke(new MethodInvoker(), null);
						}
						catch(Throwable t){
							//forward to handler
							System.out.println("Did Not Work, Access Denied");
						}
						return null;
					}
				});
	}//end invokeMethod()
	
	//Steve Jia: one way to prevent the attack is to make
	// the getMethodName() method private or static
	String getMethodName(){
		return "someMethod";
	}//end getMethodName()
	
	public void someMethod(){
		System.out.println("Safe Method Invoked.");
	}//end someMethod
	
	public void dangerousMethod(){
		System.out.println("Worked; Dangerous Method Invoked.");
	}//end someMethod
}//end class


