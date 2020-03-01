/**
 * 
 */
package com.hr.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



/**
 * @author Admin
 *
 */
public class Security {
	
	public static String key = "HRSBTS@S#SBTCBMS"; // 128 bit key      //teljag20171108v0
    public static String initVector = "HRSBTS@S#SBTCBMS"; // 16 bytes IV
    
	public static String encrypt(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes());
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encryptedByte = cipher.doFinal(value.getBytes());
    	    Base64.Encoder encoder = Base64.getEncoder();
    	    String encryptedText = encoder.encodeToString(encryptedByte);
    	    return encryptedText;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
	
	public static String decrypt(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes());
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

//            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
            Base64.Decoder decoder = Base64.getDecoder();
    	    byte[] encryptedTextByte = decoder.decode(encrypted);
//    	    cipher=Cipher.getInstance("AES","SunJCE");
//    	    cipher.init(Cipher.DECRYPT_MODE, secretKey);
    	    byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
    	    String decryptedText = new String(decryptedByte);
    	    return decryptedText;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
	

	public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
	
public static void main(String Args[]) {
	
	System.out.println("encrypt : "+encrypt(key,initVector,"123456"));
	System.out.println("encrypt : "+decrypt(key,initVector,"9qkq3YmbyxpwDhYMEbm/ZA=="));
	
}

}
