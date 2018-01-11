/* ITS553 - Secure Java Programming
 * Instructor: Dr. Michael Tu
 * Lab 6 - Final Project
 * @author: Steve Jia
 * @file: SecureServer.java
 * @date: 2017-07-28
 * @description: the SecureServer class uses a pre-generated keystore
 * 	file and creates a secure connection with the client; the server
 * 	handles different client requests and operate accordingly
 * */
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.swing.JOptionPane;

import java.awt.HeadlessException;
import java.io.*;
public class SecureServer {
	final static String keystoreFile = "C:\\ITS553Keystores\\server.jks";
	final static String password = "password";
	
	private final int port = 8088;	//port that the server will listen on
	private SSLServerSocket sslServer = null;

	public SSLServerSocket getSSLServer(){
		return this.sslServer;
	}

	/**
	 * the listen() method will utilize socket and serversocket to
	 * listen for client connections on a specific port, and will
	 * pass on the received connections to new threads for processing
	 * @throws IOException
	 */
	public void startSSLServer(){
		boolean debug = true;
		try {
			System.setProperty("javax.net.ssl.keyStore", keystoreFile);
			System.setProperty("javax.net.ssl.keyStorePassword", password);
			if(debug) {System.setProperty("javax.net.debug","all");}

			System.out.println("SSL Server Socket Starting...");
			SSLServerSocketFactory sslServFactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
			sslServer = (SSLServerSocket)sslServFactory.createServerSocket(this.port);
			System.out.println("Waiting For Connection...");
			SSLSocket sslSocket = (SSLSocket)sslServer.accept();
			
			FrameServerManager serverManager = new FrameServerManager();
			serverManager.setVisible(true);
			
			InputStream inputStream = sslSocket.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String inputString = null;
			while((inputString = bufferedReader.readLine())!=null) {
			//while(true) {
				//inputString = bufferedReader.readLine();
				System.out.println(inputString);
				System.out.flush();
				
				try {
					if(inputString.startsWith("LOGIN")) {
						String[] res = inputString.split(" ");
						String[] loginInfo = AesCrypto.decrypt(res[1]).split(" ");
						int id = new MySqlClient().validateLogin(loginInfo[0], loginInfo[1]);
						String messageBack = "INVALID";
						if(id > 0) {
							serverManager.setUserId(id);
							messageBack = "VALID";
						}
						messageBack += '\n';
						OutputStream outputStream = sslSocket.getOutputStream();
						OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
						BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
						bufferedWriter.write(messageBack);
						bufferedWriter.flush();
						//outputStream.close();
					}
					else if(inputString.startsWith("CLIENTINFO")) {
						String[] infoParts = inputString.split(" ");
						
						serverManager.setJobId(infoParts[1]);
						serverManager.setOpNum(infoParts[2]);
						
						int rows = new MySqlClient().saveClientValue(serverManager.getUserId(), infoParts[2]);
						if(rows <= 0) {
							JOptionPane.showMessageDialog(serverManager, "Update to DB was Unsuccessful");
						}
					}
				} catch (InvalidKeyException | NumberFormatException | HeadlessException | NoSuchAlgorithmException
						| NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
						| BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//inputStream.close();
			//this.sslServer.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}//end listen()
	
	


	/**
	 * main method for ThreadPrintServer
	 * @param args
	 */
	public static void main(String[] args) {
		new SecureServer().startSSLServer();
	}//end main
}//end class ThreadPrintServer

