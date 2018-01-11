/* ITS553 - Secure Java Programming
 * Instructor: Dr. Michael Tu
 * Lab 2
 * @author: Steve Jia
 * @file: ThreadPrintServer.java
 * @date: 6/21/2017
 * @description: The ThreadPrintServer class can listen for a certain
 *    number of client connections on a specified port and handles
 *    each client request with separate threads
 * */
import java.net.*;
import java.io.*;
public class ThreadPrintServer implements Runnable{
	private int port;	//port that the server will listen on
	private int maxConnections;	//max number of client connections
	private ServerSocket listener = null;
	private Connection currentThread = null;
	
	public ThreadPrintServer(int port, int maxConnections){
		if(port <= 0 || maxConnections <= 0){
			throw new IllegalArgumentException();
		}
		this.port = port;
		this.maxConnections = maxConnections;
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
	
	public int getMaxConnections() {
		return maxConnections;
	}
	
	public void setMaxConnections(int maxConnections) {
		if(maxConnections <= 0){
			throw new IllegalArgumentException();
		}
		this.maxConnections = maxConnections;
	}//end setMaxConnections
	
	public ServerSocket getListener(){
		return this.listener;
	}
	
	public Connection getCurrentThread(){
		return this.currentThread;
	}
	
	
	/**
	 * the listen() method will utilize socket and serversocket to
	 * listen for client connections on a specific port, and will
	 * pass on the received connections to new threads for processing
	 * @throws IOException
	 */
	public void listen() throws IOException{
		int connectionIndex = 0;
		this.listener = new ServerSocket(this.port);
		Socket server = null;
		System.out.println("Waiting For Connection");
		while(connectionIndex++ < this.maxConnections){
			server = this.listener.accept();
			currentThread = new Connection(this, server, connectionIndex);
			currentThread.start();
		}//end while
	}//end listen()
	
	@Override
	public void run() {
		//Connection con = (Connection)Thread.currentThread();
		try{
			System.out.println("Server Starting");
			listen(); //start listening for client connections
		}
		catch(IOException ioe){
			System.out.println(ioe.getMessage());
			ioe.printStackTrace();
		}
	}//end run()
	

	/**
	 * main method for ThreadPrintServer
	 * @param args
	 */
	public static void main(String[] args){
		final int port = 8050;
		final int maxConnections = 20;
		ThreadPrintServer printServer = new ThreadPrintServer(port, maxConnections);
		//new Thread(printServer).start();
		printServer.run();
	}//end main()
}//end class ThreadPrintServer
