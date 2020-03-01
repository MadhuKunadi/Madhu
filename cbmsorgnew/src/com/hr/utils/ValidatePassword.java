/**
 * 
 */
package com.hr.utils;

import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Admin
 *
 */
public class ValidatePassword {
	private final static Pattern hasUppercase = Pattern.compile("[A-Z]");
	private final static Pattern hasLowercase = Pattern.compile("[a-z]");
	private final static Pattern hasNumber = Pattern.compile("\\d");
	private final static Pattern hasSpecialChar = Pattern.compile("[^a-zA-Z0-9 ]");
	private static Log logger=LogFactory.getLog(ValidatePassword.class);
	
	public static String validateNewPass(String pass1, String pass2) {
	    
	    StringBuilder retVal = new StringBuilder();

	    

	    if (pass1.equals(pass2)) {
	     //   logger.info(pass1 + " = " + pass2);

	        if (pass1.length() < 7) {
	          //  logger.info(pass1 + " is length < 7");
	            retVal.append(" Password is too short . Needs to have 7 characters ");
	        }

	        if (!hasUppercase.matcher(pass1).find()) {
	          //  logger.info(pass1 + " <-- needs uppercase");
	            retVal.append(" Password needs an uppercase");
	        }

	        if (!hasLowercase.matcher(pass1).find()) {
	           // logger.info(pass1 + " <-- needs lowercase");
	            retVal.append(" Password needs a lowercase ");
	        }

	        if (!hasNumber.matcher(pass1).find()) {
	           // logger.info(pass1 + "<-- needs a number");
	            retVal.append(" Password needs a number ");
	        }

	        if (!hasSpecialChar.matcher(pass1).find()) {
	           // logger.info(pass1 + "<-- needs a specail character");
	           // Password needs a special character i.e. !,@,#, etc.  
	            retVal.append(" Password needs a special character i.e. !,@,#, etc.");

	        }
	    } else {
	        //logger.info(pass1 + " != " + pass2);
	        retVal.append("Passwords don't match");
	    }
	    if (retVal.length() == 0) {
	        //logger.info("Password validates");
	        retVal.append("Success");
	    }

	    return retVal.toString();
	}
	
	public static void main(String Args[]) {
		System.out.println(validateNewPass("Akhil@1","Akhil@1"));
	}
}
