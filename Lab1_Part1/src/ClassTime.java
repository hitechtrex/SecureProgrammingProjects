/* ITS553 - Secure Java Programming
 * Professor: Dr. Tu
 * Lab 1 - Part 1
 * @author: Steve Jia
 * @file: ClassTime.java
 * @date: 6/14/2017
 * @description: ClassTime has several members that defines the
 *    starting times and length of OS, Security, and Forensics
 * */

public class ClassTime extends Time{
	private int lengthOfOS; //class length of OS
	private int OSStartHour; //OS class start hour
	private int lengthOfSecurity; //class length of security
	private int SecurityStartHour; //security class start hour
	private int lengthOfForensics; //class length of forensics
	private int ForensicsStartHour; //forensics class start hour
	
	public int getLengthOfOS() {
		return lengthOfOS;
	}
	public void setLengthOfOS(int lengthOfOS) {
		this.lengthOfOS = lengthOfOS;
	}
	public int getOSStartHour() {
		return OSStartHour;
	}
	public void setOSStartHour(int oSStartHour) {
		OSStartHour = oSStartHour;
	}
	public int getLengthOfSecurity() {
		return lengthOfSecurity;
	}
	public void setLengthOfSecurity(int lengthOfSecurity) {
		this.lengthOfSecurity = lengthOfSecurity;
	}
	public int getSecurityStartHour() {
		return SecurityStartHour;
	}
	public void setSecurityStartHour(int securityStartHour) {
		SecurityStartHour = securityStartHour;
	}
	public int getLengthOfForensics() {
		return lengthOfForensics;
	}
	public void setLengthOfForensics(int lengthOfForensics) {
		this.lengthOfForensics = lengthOfForensics;
	}
	public int getForensicsStartHour() {
		return ForensicsStartHour;
	}
	public void setForensicsStartHour(int forensicsStartHour) {
		ForensicsStartHour = forensicsStartHour;
	}
	
	//check current time against class times
	public String checkClassTime(){
		String message = "";
		if(this.getHour() >= this.getOSStartHour() 
				&& this.getHour() <= this.getOSStartHour() + this.getLengthOfOS()){
			message = "You Should Be in OS Right Now.";
		}
		else if(this.getHour() >= this.getSecurityStartHour() 
				&& this.getHour() <= this.getSecurityStartHour() + this.getLengthOfSecurity()){
			message = "You Should Be in Security Right Now.";
		}
		else if(this.getHour() >= this.getForensicsStartHour()
				&& this.getHour() <= this.getForensicsStartHour() + this.getLengthOfForensics()){
			message = "You Should Be in Forensics Right Now.";
		}
		else{
			message = "There's No Class To Attend Right Now.";
		}
		
		return message;
	}//end checkClassTime()
}
