/* ITS553 - Secure Java Programming
 * Professor: Dr. Tu
 * Lab 1 - Part 2
 * @author: Steve Jia
 * @file: Job.java
 * @date: 6/17/2017
 * */
import java.util.Vector;
import java.util.Random;
import java.io.Serializable;
public class Job implements Serializable {
	private static final long serialVersionUID = 1L; //code recommendation for Serializable
	private int JobID;		 //ID of the job,
	private int OPNumber;    //number of operations
	private Vector<Operation> Ops; //set of operations in a vector
	private Boolean isDone;  //job has been done or not
	
	//constructor
	public Job(int newID, int maxNumberOfOperations){
		this.JobID = newID;
		
		Random rand = new Random();
		this.OPNumber = rand.nextInt(maxNumberOfOperations);
		
		this.Ops = new Vector<Operation>();
		//loop to add new operations to this job
		for(int i = 0; i < OPNumber; 
				Ops.add(new Operation(newID, rand.nextInt(3), i)), i++){
			/*empty body*/
		}//end for
	}//end constructor

	public int getJobID() {
		return JobID;
	}

	public void setJobID(int jobID) {
		JobID = jobID;
	}

	public int getOPNumber() {
		return OPNumber;
	}

	public void setOPNumber(int oPNumber) {
		OPNumber = oPNumber;
	}

	public Vector<Operation> getOps() {
		return Ops;
	}

	public void setOps(Vector<Operation> ops) {
		Ops = ops;
	}

	public Boolean getIsDone() {
		return isDone;
	}

	public void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}
	
	//perform all operations in this job
	public void doJobAll(){
		for(Operation op : Ops){
			op.performOperation();
		}
		this.setIsDone(true);
	}//end doJobSequential

}//end class
