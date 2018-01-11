/* ITS553 - Secure Java Programming
 * Instructor: Dr. Michael Tu
 * Lab 5
 * @author: Steve Jia
 * @file: ThreadPrintServer.java
 * @date: 7/11/2017
 * @description: The ThreadPrintServer class implements the Runnable interface.
 * 		This source code also contains two private classes, Computer and Printer,
 * 		each loops until stopped to process the jobQueue within the server. A
 * 		terminator job from the client will stop all three threads (main server
 * 		thread, computer thread, and the printer thread).
 * */
import java.net.*;
import java.util.Vector;
import java.io.*;
public class ThreadPrintServer implements Runnable{
	private int port;	//port that the server will listen on
	private ServerSocket listener = null;
	private Vector<Job> jobQueue = new Vector<Job>();
	
	private volatile boolean terminate = false; //boolean variable to stop the main thread
	
	//computer and printer reference variables, mainly used
	// for stopping and console displaying
	private Computer computer;
	private Printer printer;
	private Thread computeThread;
	private Thread printThread;
	
	public ThreadPrintServer(int port, int maxConnections){
		if(port <= 0 || maxConnections <= 0){
			throw new IllegalArgumentException();
		}
		this.port = port;
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
	
	public ServerSocket getListener(){
		return this.listener;
	}
	
	public synchronized Vector<Job> getJobQueue(){
		return jobQueue;
	}
	/**
	 * the listen() method will utilize socket and serversocket to
	 * listen for client connections on a specific port, and will
	 * pass on the received connections to new threads for processing
	 * @throws IOException
	 */
	public void listen(){
		System.out.println("Server is Running");
		try {
			//start the socket server
			this.listener = new ServerSocket(this.port);
			Socket server = null;
			System.out.println("Waiting For Connection");
			while(!terminate){
				//wait for clients until terminate
				server = this.listener.accept();
				handleConnectionObj(server);
			}//end while
			
			//termination received, stop all threads
			this.computer.stop();
			this.printer.stop();
			this.listener.close();
			//verify
			System.out.println("Is the computing thread still alive? " + computeThread.isAlive());
			System.out.println("Is the printing thread still alive? " + printThread.isAlive());
			System.out.println("Server Main Thread Has Been Terminated By Job");
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}//end listen()
	
	@Override
	public void run() {
		//start the computing thread
		this.computer = new Computer(this);
		this.computeThread = new Thread(computer);
		this.computeThread.start();
		//start the printing thread
		this.printer = new Printer(this);
		this.printThread = new Thread(printer);
		this.printThread.start();
		//start the socket server
		listen();
	}//end run()
	
	public void handleConnectionObj(Socket server) {
		try{
			//initialize new object streams
			ObjectInputStream objectInput = new ObjectInputStream(server.getInputStream());
			//receive a job object from a client
			Job clientJob = (Job) objectInput.readObject();
			if(clientJob != null) {
				//received new job, put in queue
				System.out.println("Received New Job, JobID: " + clientJob.getJobID());
				this.getJobQueue().add(clientJob);
			}
			//clean up
			objectInput.close();
			server.close();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace();
		}
	}//end run()
	
	public synchronized final void terminate() {
		terminate = true;
	}//end terminate

	/**
	 * main method for ThreadPrintServer
	 * @param args
	 */
	public static void main(String[] args) {
		final int port = 8050;
		final int maxConnections = 2000;
		ThreadPrintServer printServer = new ThreadPrintServer(port, maxConnections);
		printServer.run();
	}//end main
}//end class ThreadPrintServer


class Computer implements Runnable{
	private volatile boolean stop = false;
	private boolean allOpsInJobComplete = true; //assume true
	private int indexCurrentJob = 0;
	private ThreadPrintServer server;
	
	public Computer(ThreadPrintServer server) {
		this.server = server;
	}//end constructor
	
	public void stop() {
		stop = true;
	}
	
	@Override
	public void run() {
		System.out.println("Computing Thread Starting...");
		while(!stop) {
			while(!server.getJobQueue().isEmpty()) {
				if(stop) { break; }
				
				Vector<Job> jobQueue = server.getJobQueue();
				if(indexCurrentJob < jobQueue.size()) {
					//get the first unfinished job
					Job currentJob = jobQueue.elementAt(indexCurrentJob);
					if(currentJob!=null) {
						//check if current job is the terminator
						if(currentJob.isTerminator()) {
							this.stop = true;
							server.terminate();
							break;
						}
						
						//iterate current job's operation vector and check each operation
						boolean completedOneOperation = false;
						for(Operation op : currentJob.getOPs()) {
							if(op.getOPID() == 2 && !op.isDone()) { //this is an incomplete compute operation
								//do the computation step
								System.out.println("Computing Thread" + 
										": Job Id " + currentJob.getJobID() + 
										"; Op Id " + op.getOPID() + 
										"; Result " + (op.getJobID() * op.getOPID() * op.getIndex()));
								//mark the operation being done
								completedOneOperation = true;
								op.setIsDone(true);
								break; //did one operation, exit foreach operation loop
							}//end if compute job
						}//end for each op
						
	
						//check if the job is complete
						for(Operation op : currentJob.getOPs()) {
							allOpsInJobComplete &= op.isDone();
						}
						if(allOpsInJobComplete) 
						{
							currentJob.setIsDone(true);
							jobQueue.remove(currentJob);
							if(indexCurrentJob > 0) { indexCurrentJob--; }
							break;
						}
						
						//job is not yet complete, but there's no more compute operations
						if(!completedOneOperation && !allOpsInJobComplete) {
							indexCurrentJob++; //move onto the next job in queue
						}
					}//end if indexCurrentJob < jobqueue.size
				}//end if currentjob!=null
			}//end while !jobQueue.isEmpty()
		}//end while !stop
		//System.out.println(this.getName() + " Has Stopped");
	}//end run
}//end class Computer


class Printer implements Runnable{
	private volatile boolean stop = false;
	private boolean allOpsInJobComplete = true; //assume true
	private int indexCurrentJob = 0;
	private ThreadPrintServer server;
	
	public Printer(ThreadPrintServer server) {
		this.server = server;
	}//end constructor
	
	public void stop() {
		stop = true;
	}
	
	@Override
	public void run() {
		System.out.println("Printing Thread Starting...");
		while(!stop) {
			while(!server.getJobQueue().isEmpty()) {
				if(stop) { break; }
				
				Vector<Job> jobQueue = server.getJobQueue();
				if(indexCurrentJob < jobQueue.size()) {
					//get the first unfinished job
					Job currentJob = jobQueue.elementAt(indexCurrentJob);
					if(currentJob!=null) {
						//check if current job is the terminator
						if(currentJob.isTerminator()) {
							this.stop = true;
							server.terminate();
							break;
						}
						
						//iterate current job's operation vector and check each operation
						boolean completedOneOperation = false;
						for(Operation op : currentJob.getOPs()) {
							if(op.getOPID() == 1 && !op.isDone()) { //this is an incomplete print operation
								//do the computation step
								System.out.println("Printing Thread" + 
										": Job Id " + currentJob.getJobID() + 
										"; Op Id " + op.getOPID() + 
										"; Index " + op.getIndex());
								//mark the operation being done
								completedOneOperation = true;
								op.setIsDone(true);
								break; //did one operation, exit foreach operation loop
							}//end if compute job
						}//end for each op
						
	
						//check if the job is complete
						for(Operation op : currentJob.getOPs()) {
							allOpsInJobComplete &= op.isDone();
						}
						if(allOpsInJobComplete) 
						{
							currentJob.setIsDone(true);
							jobQueue.remove(currentJob);
							if(indexCurrentJob > 0) { indexCurrentJob--; }
							break;
						}
						
						//job is not yet complete, but there's no more compute operations
						if(!completedOneOperation && !allOpsInJobComplete) {
							indexCurrentJob++; //move onto the next job in queue
						}
					}//end if indexCurrentJob < jobqueue.size
				}//end if currentjob!=null
			}//end while !jobQueue.isEmpty()
		}//end while !stop
		//System.out.println(this.getName() + " Has Stopped");
	}//end run
}//end class Printer
