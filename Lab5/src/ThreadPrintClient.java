/* ITS553 - Secure Java Programming
 * Instructor: Dr. Michael Tu
 * Lab 5
 * @author: Steve Jia
 * @file: ThreadPrintClient.java
 * @date: 7/11/2017
 * @description: The ThreadPrintClient implements the Runnable interface.
 *   each client thread will sleep for a random amount of time before
 *   it uses java.net.Socket to connect to a server at a specified port.
 *   With a randomly pre-determined terminator job id, a terminator job
 *   will stop the main client thread. 
 * */

import java.net.Socket;
import java.util.Random;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ThreadPrintClient implements Runnable{
	private int ID; 	 //ThreadPrintClient's ID
	private String host; //server name for the client to connect to
	private int port;	 //server's listening port number
	private long sleepDuration = 0L;
	private boolean isTerminator = false;
	
	/**
	 * Initializes an instance of the ThreadPrintClient and member values
	 * @param id	new id for the client
	 * @param host	new server host to connect to
	 * @param port	new server's port
	 * @param startSignal
	 */
	public ThreadPrintClient(int id, String host, int port, 
			long sleepDuration, boolean isTerminator){
		if(id < 0 || host == null || host.isEmpty() || port <= 0){
			throw new IllegalArgumentException();
		}
		this.ID = id;
		this.host = host;
		this.port = port;
		this.sleepDuration = sleepDuration;
		this.isTerminator = isTerminator;
	}//end constructor
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		if(port <= 0){
			throw new IllegalArgumentException();
		}
		this.port = port;
	}//end setPort

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		if(host==null || host.isEmpty()){
			throw new IllegalArgumentException();
		}
		this.host = host;
	}//end setHost
	
	
	/**
	 * Create a new instance of Socket, connect to the server,
	 * and handle any objects
	 */
	public void connect(){
		try{
			Socket client = new Socket(this.host, this.port);
			handleConnectionObj(client);
		}
		catch(IOException ioe){
			System.out.println("Client " + this.ID + " Cannot Connect To Server: " + ioe.getMessage());
			//ioe.printStackTrace();
		}
	}//end connect
	
	
	/**
	 * utilizes object streams to send a Job object for the server to process
	 * and then receives it when the server sends it back
	 * @param socket	connected server socket
	 * @throws IOException	java.io.IOException instance
	 */
	public void handleConnectionObj(Socket socket) throws IOException{
		//initialize object streams
		ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
		//ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
		System.out.println("Print Client " + this.ID + " Sending Job(s) To Server");
		
		
		int maxNumberOfOperations = 3; //define numbers of operations allowed per job
		//create a new job and send it to the server
		Job newJob;
		if(isTerminator) {
			newJob = new Job(this.ID, "Terminate");
		}
		else {
			newJob = new Job(this.ID, maxNumberOfOperations);
		}
		objectOutput.writeObject(newJob);
		
		if(objectOutput != null){ objectOutput.close(); }
		if(socket != null){ socket.close(); }
		System.out.println("Print Client " + this.ID + " Socket Has Been Closed");
	}//end handleConnectionObj()
	
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try{
			System.out.println("Client " + this.ID + " Sleeping For " + this.sleepDuration + "ms... ");
			Thread.sleep(this.sleepDuration);
			System.out.println("Client " + this.ID + " Starting");
			connect();
		}
		catch(InterruptedException ie){
			//do nothing
		}
	}//end run
	
	/**
	 * main method for this class
	 * @param args
	 */
	public static void main(String[] args){
		final String host = "localhost";
		final int port = 8050;
		boolean terminate = false;
		
		int terminateJobIdOffset = 9950; //use this to change terminator's job ID
		
		Random randJobId = new Random();
		int jobIdThatTerminates = randJobId.nextInt(10) + terminateJobIdOffset; //terminator's jobId is in the 950s range
		System.out.println("Terminator's JobID Is " + jobIdThatTerminates);
		Random randSleep = new Random();
		int clientID = 0;
		while(!terminate) {
			if(clientID == jobIdThatTerminates) {
				terminate = true;
			}
			//ThreadPrintClient printClient = new ThreadPrintClient(
			//		clientID, host, port, randSleep.nextInt(10000), terminate);
			ThreadPrintClient printClient = new ThreadPrintClient(
					clientID, host, port, 0, terminate);
			new Thread(printClient).start();
			clientID++;
			//try {
				//Thread.sleep(1000L);
			//} catch (InterruptedException e) {
			//	e.printStackTrace();
			//}
		}//end for-loop
		System.out.println("Client Main Thread Has Been Terminated By Job");
	}//end main

}//end class ThreadPrintServer
