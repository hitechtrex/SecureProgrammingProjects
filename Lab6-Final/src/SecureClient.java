/* ITS553 - Secure Java Programming
 * Instructor: Dr. Michael Tu
 * Lab 6 - Final Project
 * @author: Steve Jia
 * @file: SecureClient.java
 * @date: 2017-07-28
 * @description: the SecureClient class uses a pre-generated keystore
 * 	file to establish a secure connection with the server; with a
 * 	successful connection, the client program will show the login jframe
 *  and allows the user to send other information to the server
 * */

import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import java.io.*;

public class SecureClient {
	
	final static String keystoreFile = "C:\\ITS553Keystores\\client.jks";
	final static String password = "password";
	static boolean debug = false;
	OutputStream output = null;
	
	private int ID; 	 //ThreadPrintClient's ID
	private final String host = "localhost"; //server name for the client to connect to
	private final int port = 8088;	 //server's listening port number
	private SSLSocket sslSocket = null;
	
	public int getPort() {
		return port;
	}

	public String getHost() {
		return host;
	}
	
	/**
	 * source code provided by class instructor
	 * @throws IOException
	 */
	protected void doClientSide() throws IOException{
		//System.setProperty("java.net.ssl.keyStore", keystoreFile);
		System.setProperty("javax.net.ssl.trustStore", keystoreFile);
		//System.setProperty("javax.net.ssl.keyStorePassword", password);
		System.setProperty("javax.net.ssl.trustStorePassword", password);
		boolean debug = true;
		if(debug) {System.setProperty("javax.net.debug", "all");}
		
		System.out.println("SSLsocket Factory Creating...");
		SSLSocketFactory sslFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
		System.out.println("SSLsocket Factory Created " + sslFactory);
		this.sslSocket = (SSLSocket)sslFactory.createSocket(this.host, this.port);
		System.out.println("SSLSock Created " + sslSocket);
		
		OutputStream outputStream = sslSocket.getOutputStream();
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		String string = "Hello From Client!";
		bufferedWriter.write(string + '\n');
		bufferedWriter.flush();
		
		try {
			FrameClientLogin login = new FrameClientLogin(this.sslSocket);
			login.setVisible(true);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//outputStream.close();
		
	}//end doClientSide()
	
	
	/**
	 * main method for this class
	 * @param args
	 */
	public static void main(String[] args){
		try {
			new SecureClient().doClientSide();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}//end main

}//end class ThreadPrintServer
