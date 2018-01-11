/* ITS553 - Secure Java Programming
 * Professor: Dr. Tu
 * Lab 1 - Part 2
 * @author: Steve Jia
 * @file: JobTester.java
 * @date: 6/17/2017
 * */
import java.util.Vector;
import java.util.Random;
public class JobTester {
	private static Vector<Job> jobs = new Vector<Job>(); //list of jobs
	private static int maxNumberOfJobs = 10; //bound for number of jobs
	private static int maxNumberOfOperations = 10; //bound for number of operations
	
	//main method
	public static void main(String[] args){
		Random rand = new Random();
		int numberOfJobs = rand.nextInt(maxNumberOfJobs);
		createNewJobs(numberOfJobs);
		
		//process each job sequentially, finish the job
		System.out.println("Performing First Batch Of Jobs");
		for (Job job : jobs){
			job.doJobAll();
			if(job.getIsDone()){
				System.out.println("Job " + job.getJobID() + " Is Done");
			}
		}//end for-loop
		
		//create some new jobs
		numberOfJobs = rand.nextInt(maxNumberOfJobs);
		createNewJobs(numberOfJobs);
		System.out.println("\nPerforming Second Batch Of Jobs");
		//process each job, one operation at a time
		for(int i = 0; i < maxJobLength(jobs); i++){
			for(Job job : jobs){
				if(job.getOps().size() > i){
					job.getOps().elementAt(i).performOperation();
				}//end if
			}//end for
		}//end for
		
	}//end main
	
	//initialize new jobs and add them to the vector
	private static void createNewJobs(int numberOfJobs){
		jobs.clear();
		for(int i = 0; i < numberOfJobs; 
				jobs.add(new Job(i,maxNumberOfOperations)), i++){
			/*empty body*/
		}
	}//end createNewJobs
	
	//find out the max number of operations in all the jobs
	private static int maxJobLength(Vector<Job> jobs){
		int length = 0;
		if(jobs!=null){
			for (Job job : jobs){
				if(job.getOps().size() > length){
					length = job.getOps().size();
				}//end if
			}//end for
		}//end if
		return length;
	}//end maxJobLength
	
}//end class
