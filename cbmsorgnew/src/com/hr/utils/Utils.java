/**
 * 
 */
package com.hr.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONObject;

//import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.Unirest;
//import com.mashape.unirest.http.exceptions.UnirestException;



/**
 * @author Admin
 *
 */
public class Utils {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		

	}
	
	public static String sendSms_through_Gateway(String sToPhoneNo,String sMessage)
    {
       try 
       {
        // Construct data
        String data = "user=" + URLEncoder.encode("iadhoc", "UTF-8");
        data += "&password=" + URLEncoder.encode("iadhoc@#123", "UTF-8");
        data += "&message=" + URLEncoder.encode(sMessage, "UTF-8");
        data += "&sender=" + URLEncoder.encode("iAdHoc", "UTF-8");
        data += "&mobile=" + URLEncoder.encode(sToPhoneNo, "UTF-8");
        data += "&type=" + URLEncoder.encode("3", "UTF-8");
        // Send data
        URL url =new URL("http://login.bulksmsgateway.in/sendmessage.php?"+data);
//     		   new URL("http://www.bulksmsgateway .in/sendmessage.php");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();
        // Get the response
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        String sResult="";
        while ((line = rd.readLine()) != null) 
        {
              sResult=sResult+line+" ";
        }
        wr.close();
        rd.close();
        System.out.println(sResult);
        org.json.JSONObject jsobject=new org.json.JSONObject(sResult);
			String msg=jsobject.getString("status");
			
        return msg;
        } 
            catch (Exception e) 
            {
               System.out.println("Error SMS "+e);
               return "Error "+e;
            }
     }
    
	 /**
     * Converts string to SQL date format.
     */
public static Date convertStringToSqlDate(String startDate) throws ParseException {
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	java.util.Date date = sdf1.parse(startDate);
	java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
	return sqlStartDate;
}

public static String[] getuserAgent(HttpServletRequest request){
	String[] userAgentDetails=null;
	String  browserDetails  =   request.getHeader("User-Agent");
//    System.out.println("The user is using browser " + browserDetails + " - version " + browserDetails);
    String  userAgent       =   browserDetails;
    String  user            =   userAgent.toLowerCase();

    String os = "";
    String browser = "";

//    System.out.println("User Agent for the request is===>"+browserDetails);
    //=================OS=======================
     if (userAgent.toLowerCase().indexOf("windows") >= 0 )
     {
         os = "Windows";
     } else if(userAgent.toLowerCase().indexOf("mac") >= 0)
     {
         os = "Mac";
     } else if(userAgent.toLowerCase().indexOf("x11") >= 0)
     {
         os = "Unix";
     } else if(userAgent.toLowerCase().indexOf("android") >= 0)
     {
         os = "Android";
     } else if(userAgent.toLowerCase().indexOf("iphone") >= 0)
     {
         os = "IPhone";
     }else{
         os = "UnKnown, More-Info: "+userAgent;
     }
     //===============Browser===========================
    if (user.contains("msie"))
    {
        String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
        browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];
    } else if (user.contains("safari") && user.contains("version"))
    {
        browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
    } else if ( user.contains("opr") || user.contains("opera"))
    {
        if(user.contains("opera"))
            browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        else if(user.contains("opr"))
            browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
    } else if (user.contains("chrome"))
    {
        browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
    } else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)  || (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) || (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1) )
    {
        //browser=(userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0]).replace("/", "-");
        browser = "Netscape-?";

    } else if (user.contains("firefox"))
    {
        browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
    } else if(user.contains("rv"))
    {
        browser="IE-" + user.substring(user.indexOf("rv") + 3, user.indexOf(")"));
    } else
    {
        browser = "UnKnown, More-Info: "+userAgent;
    }
    
    String browserversion=browser.substring(browser.lastIndexOf("-")+1);
    browser=browser.substring(0,browser.lastIndexOf("-"));
//    System.out.println("Operating System======>"+os);
//    System.out.println("Browser Name==========>"+browser);
//    System.out.println("Browser version==========>"+browserversion);
  
    String[] response={os,browser,browserversion};
    
    return response;
}
public static String  getURI() {
	String uri=null;
	Properties prop=new Properties();
	String filename="dbconnection.properties";
	InputStream input=Utils.class.getClassLoader().getResourceAsStream(filename);
	try {
		prop.load(input);
	} catch (IOException e) {
		e.printStackTrace();
	}
	return uri=prop.getProperty("URI");
}
public static JSONObject getBase64EncodedImage(String imageURL) throws IOException {
	JSONObject jsonobject=new JSONObject();
	java.net.URL url = new java.net.URL(imageURL); 
	URLConnection urlConn = url.openConnection();
       urlConn.setConnectTimeout(15000);
       urlConn.setReadTimeout(15000);
       urlConn.setAllowUserInteraction(false);         
       urlConn.setDoOutput(true);
    InputStream is = url.openStream();  
    byte[] bytes = org.apache.commons.io.IOUtils.toByteArray(is);
    System.out.println(url.getFile().substring(url.getFile().lastIndexOf("/")+1));
    jsonobject.clear();
    jsonobject.put("base64", Base64.encodeBase64String(bytes));
    jsonobject.put("filename", url.getFile().substring(url.getFile().lastIndexOf("/")+1));
    jsonobject.put("filetype", "application/pdf");
    jsonobject.put("filesize", url.getFile().length());
    return jsonobject;
}
	
}
