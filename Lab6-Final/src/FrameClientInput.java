/* ITS553 - Secure Java Programming
 * Instructor: Dr. Michael Tu
 * Lab 6 - Final Project
 * @author: Steve Jia
 * @file: FrameClientInput.java
 * @version: 2017-07-22
 * @description: this class users JFrame to display two text fields
 * 	where the user can enter the job id and the operation number
 * 	before sending them to the server, with the operation number
 * 	encrypted before transmission
 * */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.SSLSocket;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;

public class FrameClientInput extends JFrame {
	private SSLSocket sslSocket;
	private String currentUser;

	private JPanel contentPane;
	private JTextField tfJobId;
	private JTextField tfOpNum;

	/**
	 * Create the frame.
	 */
	public FrameClientInput(String newUser, SSLSocket newSocket) {
		if(newSocket==null || newUser==null || newUser.isEmpty()) { throw new IllegalArgumentException(); }
		this.sslSocket = newSocket;
		this.currentUser = newUser;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 319);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 51, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblClientInput = new JLabel("Client Input");
		lblClientInput.setForeground(Color.WHITE);
		lblClientInput.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblClientInput.setHorizontalAlignment(SwingConstants.CENTER);
		lblClientInput.setBounds(18, 11, 400, 36);
		contentPane.add(lblClientInput);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendInfoToServer();
			}
		});
		btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 24));
		btnSubmit.setBounds(18, 223, 400, 40);
		contentPane.add(btnSubmit);
		
		JLabel lblJobId = new JLabel("Job ID");
		lblJobId.setHorizontalAlignment(SwingConstants.TRAILING);
		lblJobId.setForeground(Color.WHITE);
		lblJobId.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblJobId.setBounds(86, 97, 100, 36);
		contentPane.add(lblJobId);
		
		JLabel lblOpNumber = new JLabel("OP Number");
		lblOpNumber.setHorizontalAlignment(SwingConstants.TRAILING);
		lblOpNumber.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblOpNumber.setForeground(Color.WHITE);
		lblOpNumber.setBounds(66, 158, 120, 36);
		contentPane.add(lblOpNumber);
		
		tfJobId = new JTextField();
		tfJobId.setHorizontalAlignment(SwingConstants.CENTER);
		tfJobId.setFont(new Font("Tahoma", Font.BOLD, 21));
		tfJobId.setBounds(229, 98, 120, 36);
		contentPane.add(tfJobId);
		tfJobId.setColumns(10);
		
		tfOpNum = new JTextField();
		tfOpNum.setHorizontalAlignment(SwingConstants.CENTER);
		tfOpNum.setFont(new Font("Tahoma", Font.BOLD, 21));
		tfOpNum.setBounds(229, 159, 120, 36);
		contentPane.add(tfOpNum);
		tfOpNum.setColumns(10);
		
		JLabel lblCurrentUser = new JLabel("User: " + this.currentUser);
		lblCurrentUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentUser.setForeground(Color.WHITE);
		lblCurrentUser.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCurrentUser.setBounds(148, 58, 145, 28);
		contentPane.add(lblCurrentUser);
	}
	
	private void sendInfoToServer() {
		//check textfield inputs
		String jobId = this.tfJobId.getText();
		String opNum = this.tfOpNum.getText();
		
		try {
			//check username and password values inputed from the frame
			if(jobId == null || jobId.isEmpty()) {
				throw new IllegalArgumentException("Invalid Job ID");
			}
			
			if(opNum==null || opNum.isEmpty()) {
				throw new IllegalArgumentException("Invalid OP Number");
			}
			
			String clientMessage = "CLIENTINFO " + jobId + " " + AesCrypto.encrypt(opNum) + '\n';
			//String clientMessage = "CLIENTINFO 1 2";
			//send message to server
			OutputStream outputStream = sslSocket.getOutputStream();
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
			BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
			bufferedWriter.write(clientMessage);
			bufferedWriter.flush();
		} catch (IllegalArgumentException iae) {
			JOptionPane.showMessageDialog(this, iae.getMessage());
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(this, "Failed To Send To Server");
			ioe.printStackTrace();
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			JOptionPane.showMessageDialog(this, "Failed To Encrypt Data");
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end sendInfoToServer
}
