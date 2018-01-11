/* ITS553 - Secure Java Programming
 * Instructor: Dr. Michael Tu
 * Lab 5
 * @author: Dr. Michael Tu
 * @date: 7/13/2017
 * @description: this source code has been provided
 * 	by Dr. Tu. this Job class features synchronized
 *  methods
 * */

import java.util.Vector;
import java.util.Random;
import java.io.Serializable;

public class Job implements Serializable{
	
		private int JobID, OPNumber;	
		private Vector<Operation> OPs;
		private Random randGen;
		private boolean isDone = false;
		private boolean isTerminator = false;
		
		public Job(){}		
		public Job(int a, int b){
			this.JobID = a;
			this.OPNumber =b;
			this.OPs = new Vector<Operation>();
			this.randGen = new Random();
			//System.out.println("the job id is " + a + " and the job number is "+b);
			for (int i =0; i<b;i++){ // job index
				 int r = randGen.nextInt(3) + 1; // determine the op type
				Operation op = new Operation(this.JobID,r,i+1); // i+1 is simply the index
				this.OPs.add(op);
				}
		}		
		
		public Job(int jobId, String purpose) throws IllegalArgumentException {
			//input check
			if(purpose == null || 
					!purpose.equalsIgnoreCase("Terminate")) {
				throw new IllegalArgumentException();
			}//end if
			
			//this job's purpose is to terminate everything
			this.JobID = jobId;
			this.OPNumber = 1;
			this.OPs = new Vector<Operation>();
			Operation op = new Operation(this.JobID, 0, 0);
			this.OPs.add(op);
			this.isTerminator = true;
		}//end constructor
		
		public int getJobID(){return this.JobID;}
		public void setJobID(int d){this.JobID=d;}			
		public int getOPNumber(){return this.OPNumber;}
		public void setOPNumber(int d){this.OPNumber=d;}
		public Vector<Operation> getOPs(){return this.OPs;}
		public void setOPs(Vector<Operation> d){this.OPs=d;}
		public synchronized boolean isDone(){return this.isDone;}
		public synchronized void setIsDone(boolean d){this.isDone=d;}
		public boolean isTerminator() {return this.isTerminator;}
		
		public void print(String msg) {
			String threadName = Thread.currentThread().getName();
			System.out.println(threadName + ": "+msg);			
		}
}


