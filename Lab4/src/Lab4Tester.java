/**
 * ITS553 - Secure Java Programming
 * Professor: Dr. Tu
 * Lab 4
 * @author Steve Jia
 * @version 2017-07-06
 * @filename Lab4Tester.java
 * */
public class Lab4Tester {
	
	public static void main(String[] args){
		//MethodInvoker mi = new MethodInvoker();
		//mi.invokeMethod();
		SubMethodInvoker smi = new SubMethodInvoker();
		System.out.println("Did the overriden getMethodName() method work?");
		smi.invokeMethod();
		
		System.out.println();
		
		//normal behavior
		Account account = new BankAccount();
		//enforce security manager check
		System.out.println("Did Normal Withdraw Work?");
		boolean result;
		try {
			result = account.withdraw(200.0);
			System.out.println(result + ", Insufficient Funds");
		} catch (IllegalAccessException e) {
			System.out.println("Illegal Access to Bank Account");
		}
		
		System.out.println();
		
		//malicious attack
		Account maliciousAccount = new BankAccount();
		//no security check performed
		System.out.println("Did Malicious Overdraft Work?");
		boolean maliciousResult;
		try {
			maliciousResult = maliciousAccount.overdraft();
			System.out.println("Withdrawal Successful? " + maliciousResult);
		} catch (IllegalAccessException e) {
			System.out.println("No, Illegal Access");
		}

	}//end main()
}
