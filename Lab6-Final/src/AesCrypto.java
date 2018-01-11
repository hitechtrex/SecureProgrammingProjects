/* ITS553 - Secure Java Programming
 * Instructor: Dr. Michael Tu
 * Lab 6 - Final Project
 * @author: Steve Jia
 * @file: AesCrypto.java
 * @version: 2017-07-28
 * @description: the AesCypto class contains two static methods
 * 	that can encrypt and decrypt strings using javax.crypto apis
 * */
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesCrypto {
	private static String key = "Bar12345Bar12345";
	private static String initVector = "RandomInitVector";
	
	
	public static String encrypt(String inputString) 
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, 
					IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, 
					UnsupportedEncodingException 
	{
		IvParameterSpec ivSpec = new IvParameterSpec(initVector.getBytes("UTF-8"));
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		
		Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		aesCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
		byte[] encryptedBytes = aesCipher.doFinal(inputString.getBytes());
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}//end encrypt
	
	
	public static String decrypt(String inputString) 
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, 
					InvalidAlgorithmParameterException, IllegalBlockSizeException, 
					BadPaddingException, UnsupportedEncodingException 
	{
		IvParameterSpec ivSpec = new IvParameterSpec(initVector.getBytes("UTF-8"));
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		
        Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		aesCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);
		byte[] decodedBytes = Base64.getDecoder().decode(inputString.getBytes());
		byte[] decryptedBytes = aesCipher.doFinal(decodedBytes);
		return new String(decryptedBytes);
	}//end decrypt

	
//	private static byte[] getRandomIv() {
//		SecureRandom sr = new SecureRandom();
//		byte[] output = new byte[16];
//		sr.nextBytes(output);
//		return output;
//	}
}//end class AesCrypto
