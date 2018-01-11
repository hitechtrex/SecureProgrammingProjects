/* ITS553 - Secure Java Programming
 * Lab 6 - Final Project
 * Instructor: Dr. Michael Tu
 * @author: Steve Jia
 * @filename: FrameServerManager.java
 * @version: 2017-07-23
 * @description: this class users JFrame to display two text fields
 * 	that contain informatio received from the client, by default, the
 * 	operation number will be encrypted and the user can use the decrypt
 * 	button to show the original operation number that the client had send
 * */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;

public class FrameServerManager extends JFrame {
	private int userId = 0;
	
	private JPanel contentPane;
	private JTextField tfJobId;
	private JTextField tfOpNum;
	
	/**
	 * Create the frame.
	 */
	public FrameServerManager() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(153, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Client Job ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(55, 51, 120, 36);
		contentPane.add(lblNewLabel);
		
		JLabel lblClientOpNumber = new JLabel("Client OP Number");
		lblClientOpNumber.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblClientOpNumber.setHorizontalAlignment(SwingConstants.TRAILING);
		lblClientOpNumber.setBounds(10, 108, 165, 36);
		contentPane.add(lblClientOpNumber);
		
		tfJobId = new JTextField();
		tfJobId.setHorizontalAlignment(SwingConstants.CENTER);
		tfJobId.setFont(new Font("Tahoma", Font.BOLD, 21));
		tfJobId.setBounds(185, 51, 239, 36);
		contentPane.add(tfJobId);
		tfJobId.setColumns(10);
		
		tfOpNum = new JTextField();
		tfOpNum.setHorizontalAlignment(SwingConstants.CENTER);
		tfOpNum.setFont(new Font("Tahoma", Font.BOLD, 12));
		tfOpNum.setBounds(185, 108, 239, 36);
		contentPane.add(tfOpNum);
		tfOpNum.setColumns(10);
		
		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				decryptOpNum();
			}
		});
		btnDecrypt.setFont(new Font("Tahoma", Font.BOLD, 21));
		btnDecrypt.setBounds(55, 200, 120, 36);
		contentPane.add(btnDecrypt);
		
		JButton btnEncrypt = new JButton("Encrypt");
		btnEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				encryptOpNum();
			}
		});
		btnEncrypt.setFont(new Font("Tahoma", Font.BOLD, 21));
		btnEncrypt.setBounds(259, 200, 120, 36);
		contentPane.add(btnEncrypt);
		
		JLabel lblServer = new JLabel("Server");
		lblServer.setHorizontalAlignment(SwingConstants.CENTER);
		lblServer.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblServer.setBounds(5, 5, 424, 36);
		contentPane.add(lblServer);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void setJobId(String newJobIdText) {
		if(newJobIdText == null) {return;}
		this.tfJobId.setText(newJobIdText);
	}
	
	public void setOpNum(String newOpNumText) {
		if(newOpNumText == null) {return;}
		this.tfOpNum.setText(newOpNumText);
	}
	
	private void encryptOpNum() {
		try {
			this.tfOpNum.setText(AesCrypto.encrypt(this.tfOpNum.getText()));
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException 
				| IllegalBlockSizeException | BadPaddingException 
				| InvalidAlgorithmParameterException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void decryptOpNum() {
		try {
			this.tfOpNum.setText(AesCrypto.decrypt(this.tfOpNum.getText()));
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException 
				| BadPaddingException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
