package com.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONTokener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.postgresql.util.PGobject;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class Helper {
	
	 static String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
     
 
    
public static boolean emailCheck(String email) {
	 Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(email);	
    System.out.println("matcher: "+matcher);
    System.out.println("pattern: "+pattern);
    if(!matcher.matches())
	return false;
    else
    	return true;
	
	
}
public static PGobject convertToPGObject(org.json.JSONObject json) throws SQLException{
	
	PGobject pGobject = new PGobject();
	pGobject.setType("json");
	pGobject.setValue(json.toString());
	return pGobject;
}

public static void main(String[] args) throws WriterException, IOException,
NotFoundException {
String qrCodeData = "Hello World!";
String filePath = "QRCode.png";
String charset = "UTF-8"; // or "ISO-8859-1"
Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

createQRCode(qrCodeData, filePath, charset, hintMap, 200, 200);
System.out.println("QR Code image created successfully!");

System.out.println("Data read from QR Code: "
	+ readQRCode(filePath, charset, hintMap));

}

public static void createQRCode(String qrCodeData, String filePath,
String charset, Map hintMap, int qrCodeheight, int qrCodewidth)
throws WriterException, IOException {
BitMatrix matrix = new MultiFormatWriter().encode(
	new String(qrCodeData.getBytes(charset), charset),
	BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
	.lastIndexOf('.') + 1), new File(filePath));
}

public static String readQRCode(String filePath, String charset, Map hintMap)
throws FileNotFoundException, IOException, NotFoundException {
BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
	new BufferedImageLuminanceSource(
			ImageIO.read(new FileInputStream(filePath)))));
Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap,
	hintMap);
return qrCodeResult.getText();
}

public static JSONObject objectToJSONObject(Object object){
    Object json = null;
    JSONObject jsonObject = null;
    try {
        json = new JSONTokener(object.toString()).nextValue();
    } catch (JSONException e) {
        e.printStackTrace();
    }
    if (json instanceof JSONObject) {
        jsonObject = (JSONObject) json;
    }
    return jsonObject;
}

public static JSONArray objectToJSONArray(Object object){
    Object json = null;
    JSONArray jsonArray = null;
    try {
        json = new JSONTokener(object.toString()).nextValue();
    } catch (JSONException e) {
        e.printStackTrace();
    }
    if (json instanceof JSONArray) {
        jsonArray = (JSONArray) json;
    }
    return jsonArray;
}

@SuppressWarnings({ "rawtypes", "unchecked" })
public static ArrayList convertResultSetIntoJSON(ResultSet resultSet) throws Exception {
	ArrayList resultsetArray=new ArrayList(); 
	while (resultSet.next()) {
		int total_rows = resultSet.getMetaData().getColumnCount();
		Map map=new HashMap();
		for (int i = 0; i <total_rows; i++) {
			String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
			Object columnValue = resultSet.getObject(i + 1);
			map.put(columnName,columnValue);
		}
		resultsetArray.add(map);
	}
	System.out.println(resultsetArray);
	return resultsetArray;
}


}
