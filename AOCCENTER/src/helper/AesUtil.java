package helper;

public class AesUtil {
	
	private static final String IV =   "494e56454e5430525931373131323941";
	   private static final String SALT = "494e56454e5430525931373131323941";
	 
	   private static final int KEY_SIZE = 128;
	   private static final int ITERATION_COUNT = 10000;
	   private static final String PASSPHRASE = "INVENT0RY171129A";

	public static String Encrypt(String PLAIN_TEXT) {
	      AesUtilHelper util = new AesUtilHelper(KEY_SIZE, ITERATION_COUNT);
	      String encrypt = util.encrypt(SALT, IV, PASSPHRASE, PLAIN_TEXT);        
	   //   System.out.println(encrypt);
	      return encrypt;
	  }
	  public static String  Decrypt(String CIPHER_TEXT) {
	      AesUtilHelper util = new AesUtilHelper(KEY_SIZE, ITERATION_COUNT);
	      String decrypt = util.decrypt(SALT, IV, PASSPHRASE, CIPHER_TEXT);
//	      System.out.println(decrypt);
	      return decrypt;
	  }
	  String hexToBinary(String hex) {
	      int i = Integer.parseInt(hex, 16);
	      String bin = Integer.toBinaryString(i);
	      return bin;
	  }
}
