/* ITS553 - Secure Java Programming
 * Professor: Dr. Tu
 * Lab 2
 * @author: Steve Jia
 * @file: Operation.java
 * @date: 6/21/2017
 * @description: THIS IS THE OPERATION CLASS FOR THE SERVER PROJECT
 * */
import java.io.Serializable;
public class Operation implements Serializable {
	private static final long serialVersionUID = 1L; //code recommendation for Serializable
	private int JobID; //job id for this operation
	private int OPID;  //operation id that defines the description
	private int index; //index of which the operation is in the vector
	private String JobDescription; //description of this operation
	private Boolean isDone; //status of completion
	
	//constructor
	public Operation(int jobID, int opID, int index){
		this.JobID = jobID;
		this.OPID = opID;
		this.index = index;
		this.setJobDescription(opID);
	}//end constructor

	//assign operation description based on the operation id
	//	changed opID value to be zero-based from the lab doc
	public void setJobDescription(int opID) {
		switch(opID){
			case 0:
				this.JobDescription = "print";
				break;
			case 1:
				this.JobDescription = "compute";
				break;
			case 2:
				this.JobDescription = "terminate";
				break;
		}//end switch
	}//end setJobDescription

	public int getJobID() {
		return JobID;
	}

	public void setJobID(int jobID) {
		JobID = jobID;
	}

	public int getOPID() {
		return OPID;
	}

	public void setOPID(int oPID) {
		OPID = oPID;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getJobDescription() {
		return JobDescription;
	}

	public void setJobDescription(String jobDescription) {
		JobDescription = jobDescription;
	}

	public Boolean getIsDone() {
		return isDone;
	}

	public void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}
	
	//perform the operation by printing out a message
	public void performOperation(){
		System.out.println("Job " + this.getJobID() + 
							", Op# "+ this.getIndex() + 
							" - " + this.getJobDescription());
		this.setIsDone(true);
	}//end performOperation()
}//end class
