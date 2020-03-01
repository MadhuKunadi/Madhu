package com.cs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {
	public static Pattern regexPattern;
	public static Matcher regMatcher;
	public static String validateEmailAddress(String emailAddress) {

	    regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
	    regMatcher   = regexPattern.matcher(emailAddress);
	    if(regMatcher.matches()){
	        return "Valid";
	    } else {
	    return "Invalid";
	    }
	}
	public static boolean UUIDValidator(String uuid){
		final Pattern UUID_Format = Pattern.compile("([0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12})");
		Matcher match=UUID_Format.matcher(uuid);
		return match.matches();
	}

}
