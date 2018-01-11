/* ITS553 - Secure Java Programming
 * Professor: Dr. Tu
 * Lab 1 - Part 1
 * @author: Steve Jia
 * @file: Time.java
 * @date: 6/14/2017
 * @description: the Time class defines hour, minute, and second
 * */
import java.util.*;
public class Time {
	private int hour;
	private int minute;
	private int second;
	
	//constructor
	public Time(){
		//use current time to assign to the members
		Calendar cal = Calendar.getInstance();
		this.hour = cal.get(Calendar.HOUR_OF_DAY);
		this.minute = cal.get(Calendar.MINUTE);
		this.second = cal.get(Calendar.SECOND);
	}
	
	//constructor
	public Time(int hour, int minute, int second)
		throws IllegalArgumentException{
		//check input parameters
		if(hour < 0 || hour >= 24 || minute < 0 
				|| minute >= 60 ||second < 0 || second >=60)
		{
			throw new IllegalArgumentException("Invalid Input Time Parameters");
		}
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}//end constructor
	
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	
	public String ToString(){
		//print the time in a nice format
		return String.format("%02d:%02d:%02d", hour, minute, second);
	}
}
