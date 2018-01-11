/* ITS553 - Secure Java Programming
 * Professor: Dr. Tu
 * Lab 2
 * @author: Steve Jia
 * @file: Job.java
 * @date: 6/21/2017
 * @description: THIS IS THE JOB CLASS FOR THE CLIENT PROJECT
 * */
import java.util.Vector;
import java.util.Random;
import java.io.Serializable;

//Job Class In Lab 2 Client Project
public class Job implements Serializable {
	private static final long serialVersionUID = 1L; //code recommendation for Serializable
	private int JobID;		 //ID of the job,
	private int OPNumber;    //number of operations
	private Vector<Operation> Ops; //set of operations in a vector
	private Boolean isDone;  //job has been done or not
	private String workerName;
	
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

	public Boolean isDone() {
		return isDone;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		if(workerName == null || workerName.isEmpty()){
			throw new IllegalArgumentException();
		}
		this.workerName = workerName;
	}

	//perform all operations in this job
	public void doJob(){
		for(Operation op : Ops){
			op.performOperation();
		}
		this.isDone = true;
	}//end doJobSequential

}//end class
