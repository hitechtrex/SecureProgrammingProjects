/**
 * ITS553 - Secure Java Programming
 * Professor: Dr. Tu
 * Lab 4
 * @author Steve Jia
 * @version 2017-07-06
 * @filename BankAccount.java
 * */

class Account{
	//maintains all banking related data such as
	// account balance
	private double balance = 100.0;
	
	boolean overdraft() throws IllegalAccessException{
		balance += 300.0; //add 300 in case of overdraft
		System.out.println("Added back-up amount. The balance is: " + balance);
		return true;
	}
	
	boolean withdraw(double amount) throws IllegalAccessException{
		if(balance - amount >= 0){
			balance -= amount;
			System.out.println("Withdrawal Successful. The Balance is :" + balance);
			return true;
		}
		return false;
	}
}

public class BankAccount extends Account{
	//subclass handles authentication
	@Override 
	boolean withdraw(double amount) throws IllegalAccessException{
		if(!securityCheck()){
			throw new IllegalAccessException();
		}
		return super.withdraw(amount);
	}
	
	private final boolean securityCheck(){
		//check that account management may proceed
		return true;
	}
	
	//override the overdraft method in subclass to prevent
	// any access to super class' overdraft method
	@Override
	boolean overdraft() throws IllegalAccessException{
		throw new IllegalAccessException();
	}
}
