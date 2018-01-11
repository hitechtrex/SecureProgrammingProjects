/* ITS553 - Secure Java Programming
 * Instructor: Dr. Michael Tu
 * Lab 2
 * @author: Steve Jia
 * @file: Connection.java
 * @date: 6/21/2017
 * @description:
 * */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection extends Thread{
	private Socket server;
	//private String threadName;
	
	public Connection(Runnable serverObject, Socket socket, int id){
		super(serverObject, "Server Thread " + id);
		//this.threadName = "Server Thread " + id;
		this.server = socket;
	}//end Connection

	public Socket getSocket() {
		return server;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 * Overriden method will use object streams to process client objects
	 *    and send it back to the client
	 */
	@Override
	public void run(){
		//handleConnectionObj()
		try{
			//initialize new object streams
			ObjectInputStream objectInput = new ObjectInputStream(server.getInputStream());
			ObjectOutputStream objectOutput = new ObjectOutputStream(server.getOutputStream());
			
			//receive a job object from a client
			Job clientJob = (Job) objectInput.readObject();
			System.out.println(super.getName() + 
					" Received A New Job From Client " + 
					this.server.getInetAddress().getHostName());
			//process the job
			clientJob.doJob();
			clientJob.setWorkerName(super.getName()); //mark which server thread worked on this
			System.out.println(super.getName() + " Has Completed Job " + clientJob.getJobID());
			//send the job object back to the client
			objectOutput.writeObject(clientJob);
			
			//clean up
			objectInput.close();
			objectOutput.close();
			server.close();
			System.out.println(super.getName() + " Socket Has Been Closed");
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace();
		}
	}//end run()
	
}//end class Connection
