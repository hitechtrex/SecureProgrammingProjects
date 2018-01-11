/* ITS553 - Secure Java Programming
 * Instructor: Dr. Michael Tu
 * Lab 5
 * @author: Dr. Michael Tu
 * @date: 7/13/2017
 * @description: this source code has been provided
 * 	by Dr. Tu. this Operation class features synchronized
 *  methods
 * */

import java.io.Serializable;
public class Operation implements Serializable {
	
		private int JobID, OPID, index;		
		private String JobDescription;
		private boolean isDone = false;
		
		public Operation(){}		
		public Operation(int a, int b, int c){
			this.JobID = a;
			this.OPID = b;
			this.index = c;
		this.setJobDescription(b);
		}	
		
		public void setJobDescription(int r){
			 if (r==2) {  // maybe computing				 
				this.JobDescription = "please compute the  a= "  + this.JobID +" + "+ this.OPID +" + "+ this.index; // you can make it simple to compute some simple equations
				 }
			 else if (r==1) {  // printing job				
				 this.JobDescription = "please print out the job id = " + this.JobID+ " operation id = " +this.OPID
				 +" operation index = "+ this.index;
			 }
			 else if (r==0){
				 this.JobDescription = "terminate everything; job id = " + this.JobID + ", operation id = " + this.OPID;
			 }
		}
		public int getJobID(){return this.JobID;}
		public void setJobID(int d){this.JobID=d;}
		public synchronized boolean isDone(){return this.isDone;}
		public synchronized void setIsDone(boolean d){this.isDone=d;}
		public int getOPID(){return this.OPID;}
		public void setOPID(int d){this.OPID=d;}
		public String getJobDescription(){return this.JobDescription;}
		public void setJobDescription(String d){this.JobDescription=d;}
		public int getIndex() {return this.index;}
	
		public void print(String msg) {
			String threadName = Thread.currentThread().getName();
			System.out.println(threadName + ": "+msg);			
		}
}


