/* ITS553 - Secure Java Programming
 * Professor: Dr. Tu
 * Lab 1 - Part 1
 * @author: Steve Jia
 * @file: TimeTester.java
 * @date: 6/14/2017
 * */
public class TimeTester {
	public static void main(String[] args)
	{
		//print out the current time	
		Time tm = new Time();
		System.out.println("Current Time is " + tm.ToString() + '\n');
		
		//define class time parameters
		final int classLength = 2;
		final int osStart = 9;
		final int securityStart = 13;
		final int forensicsStart = 16;
		
		//create ClassTime object
		ClassTime ct = new ClassTime();
		ct.setOSStartHour(osStart);
		ct.setLengthOfOS(classLength);
		ct.setSecurityStartHour(securityStart);
		ct.setLengthOfSecurity(classLength);
		ct.setForensicsStartHour(forensicsStart);
		ct.setLengthOfForensics(classLength);
		
		//use the ClassTime members to display each class' from and to times
		Time start = null;
		Time end = null;
		try{
			start = new Time(ct.getOSStartHour(), 0, 0);
			end = new Time(ct.getOSStartHour() + ct.getLengthOfOS(), 0, 0);
		}
		catch(IllegalArgumentException x){
			System.out.println(x.getMessage());
		}

		if(start!=null && end!=null){
			System.out.println("OS Class Time is From " + 
									start.ToString() + " To " + end.ToString());
			
			start.setHour(ct.getSecurityStartHour());
			end.setHour(ct.getSecurityStartHour() + ct.getLengthOfSecurity());
			System.out.println("Security Class Time is From " + 
									start.ToString() + " To " + end.ToString());
			
			start.setHour(ct.getForensicsStartHour());
			end.setHour(ct.getForensicsStartHour() + ct.getLengthOfForensics());
			System.out.println("Forensics Class Time is From " + 
									start.ToString() + " To " + end.ToString() + '\n');
		}//end if
		
		//check which class should the user be in
		System.out.print(ct.checkClassTime());
	}//end main
}//end class
