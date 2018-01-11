/* ITS553 - Secure Java Programming
 * Lab 6 - Final Project
 * Instructor: Dr. Michael Tu
 * @author: Steve Jia
 * @filename: FrameClientLogin.java
 * @version: 2017-07-20
 * @description: this class users JFrame to display two text fields
 * 	where the user can use to log into the server; the username
 * 	and the password are encrypted before sending to the server,
 * 	and the server will decrypt and validate against a database
 * */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.SSLSocket;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;

public class FrameClientLogin extends JFrame {
	private SSLSocket sslSocket;
	private JPanel contentPane;
	private JTextField tfUsername;
	private JPasswordField pfPassword;

	/**
	 * Create the frame.
	 */
	public FrameClientLogin(SSLSocket newSocket) {
	//public FrameClientLogin(BufferedWriter newWriter) {
		if(newSocket==null) { throw new IllegalArgumentException(); }
		this.sslSocket = newSocket;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblClientLogin = new JLabel("Client Login");
		lblClientLogin.setForeground(new Color(0, 0, 128));
		lblClientLogin.setBackground(Color.LIGHT_GRAY);
		lblClientLogin.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblClientLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblClientLogin.setBounds(5, 10, 424, 30);
		contentPane.add(lblClientLogin);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loginUser();
			}
		});
		btnLogin.setForeground(Color.LIGHT_GRAY);
		btnLogin.setBackground(new Color(25, 25, 112));
		btnLogin.setContentAreaFilled(false);
		btnLogin.setOpaque(true);
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnLogin.setBounds(18, 210, 400, 40);
		contentPane.add(btnLogin);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUsername.setBounds(78, 80, 100, 36);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(78, 131, 100, 36);
		contentPane.add(lblPassword);
		
		tfUsername = new JTextField();
		tfUsername.setHorizontalAlignment(SwingConstants.CENTER);
		tfUsername.setFont(new Font("Tahoma", Font.BOLD, 21));
		tfUsername.setBounds(206, 79, 150, 36);
		contentPane.add(tfUsername);
		tfUsername.setColumns(10);
		
		pfPassword = new JPasswordField();
		pfPassword.setHorizontalAlignment(SwingConstants.CENTER);
		pfPassword.setFont(new Font("Tahoma", Font.BOLD, 21));
		pfPassword.setBounds(206, 135, 150, 36);
		contentPane.add(pfPassword);
	}//end constructor
	
	private void loginUser() {
		try {
			String userName = tfUsername.getText();
			String password = String.valueOf(pfPassword.getPassword());
			
			//check username and password values inputed from the frame
			if(userName == null || userName.isEmpty()) {
				throw new IllegalArgumentException("Invalid Username");
			}
			
			if(password==null || password.isEmpty()) {
				throw new IllegalArgumentException("Invalid Password");
			}
			
			//form login message with encrypted username and password
			String clientMessage = "LOGIN " + AesCrypto.encrypt(userName + " " + password) + " \n";
			//send message to server
			OutputStream outputStream = sslSocket.getOutputStream();
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
			BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
			bufferedWriter.write(clientMessage);
			bufferedWriter.flush();
			
			InputStream inputStream = sslSocket.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String serverReply = bufferedReader.readLine();
			if(serverReply == null || serverReply.isEmpty()) {
				throw new IllegalStateException();
			}
			if(serverReply.equals("VALID")) {
				FrameClientInput clientInput = new FrameClientInput(userName, this.sslSocket);
				clientInput.setVisible(true);
				this.setVisible(false);
			}else {
				JOptionPane.showMessageDialog(this, "Invalid Login");
			}
			
		} catch (IllegalArgumentException iae) {
			JOptionPane.showMessageDialog(this, iae.getMessage());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (IllegalStateException ise) {
			JOptionPane.showMessageDialog(this, "Failed To Get Server's Reply");
		} catch (NoSuchAlgorithmException | 
					NoSuchPaddingException | 
					InvalidKeyException | 
					IllegalBlockSizeException |
					BadPaddingException 
					encryptException) {
			JOptionPane.showMessageDialog(this, "Failed To Encrypt Data");
			return;
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}//end loginUser
	
	
	
}
