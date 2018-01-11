/* ITS553 - Secure Java Programming
 * Instructor: Dr. Michael Tu
 * Lab 2
 * @author: Steve Jia
 * @file: ThreadPrintClient.java
 * @date: 6/21/2017
 * @description: The ThreadPrintClient implements the Runnable interface
 *    and uses java.net.Socket to connect to a server at a specified port.
 *    The client can send and receive Job objects to and from the server. 
 * */

import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ThreadPrintClient implements Runnable{
	private int ID; 	 //ThreadPrintClient's ID
	private String host; //server name for the client to connect to
	private int port;	 //server's listening port number
	private final CountDownLatch startSignal; //start signal for client connection
	
	/**
	 * Initializes an instance of the ThreadPrintClient and member values
	 * @param id	new id for the client
	 * @param host	new server host to connect to
	 * @param port	new server's port
	 * @param startSignal
	 */
	public ThreadPrintClient(int id, String host, int port, CountDownLatch startSignal){
		if(id < 0 || host == null || host.isEmpty() || port <= 0){
			throw new IllegalArgumentException();
		}
		this.ID = id;
		this.host = host;
		this.port = port;
		this.startSignal = startSignal;
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
		ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
		System.out.println("Print Client " + this.ID + " Sending Job(s) To Server");
		
		
		int maxNumberOfOperations = 5; //define numbers of operations allowed per job
		//create a new job and send it to the server
		objectOutput.writeObject(new Job(this.ID, maxNumberOfOperations));
		
		System.out.println("Print Client " + this.ID + " Waiting For Server To Finish Job(s)");
		try{
			//receive the job back from the server, and check to see if it's done
			Job returnedJob = (Job) objectInput.readObject();
			System.out.println(returnedJob.getWorkerName() + 
					" Worked On Job " + returnedJob.getJobID());
			System.out.println("Job " + returnedJob.getJobID() + 
					(returnedJob.isDone() ? " Is Completed" : " Is Not Completed"));
		}
		catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace();
		}
		finally{
			//cleaning up
			if(objectOutput != null){ objectOutput.close(); }
			if(objectInput != null){ objectInput.close(); }
			if(socket != null){ socket.close(); }
			System.out.println("Print Client " + this.ID + " Socket Has Been Closed");
		}
	}//end handleConnectionObj()
	
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try{
			if(startSignal != null){
				//wait for the start signal before connecting to server
				this.startSignal.await();
			}
			System.out.println("Client "+ this.ID +" Starting");
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
		//create a count down latch with the start set at one count down
		CountDownLatch startSignal = new CountDownLatch(1);
		
		for(int clientID = 0; clientID < 10; clientID++){
			ThreadPrintClient printClient = new ThreadPrintClient(clientID,host,port,startSignal);
			//printClient.run();
			new Thread(printClient).start();
		}//end for-loop
		//trigger the count down latch so that all client threads will connect at once
		startSignal.countDown();
	}//end main

}//end class ThreadPrintServer
