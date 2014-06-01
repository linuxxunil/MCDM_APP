package edu.mcdm.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtility {
	/**
	 * 函數名稱 : getDirectory
	 * 函數說明 : 擷取檔案的路徑位置
	 * 函數範例 :  
	 * @param token
	 * @param path
	 * @return
	 */
	static public String getBasename(String token, String path) {
		String[] split = path.split(token);
		return split[split.length-1];
	}
	
	/**
	 * 函數名稱 : getDirectory
	 * 函數說明 : 擷取檔案的路徑位置
	 * 函數範例 :  
	 *  input  : /sdcard/data/cscheduling/data.db
	 *  output : /sdcard/data/cscheduling
	 * 
	 * @param path
	 * @return directory path
	 */
	static public String getDirectory(String path) {
		String[] split = path.split("/");
		String dir = "";
		for (int i=0; i<split.length-2; i++) {
			dir += split[i] + "/";		
		}
		return dir + split[split.length-2];
	}
	
	
	/**
	 * 函數名稱 : isMailAddress
	 * 函數說明 : 判斷是否是MailAdress
	 * 函數範例 :  
	 *  input  : jesse_lin@ismp.csie.ncku.edu.tw
	 *  output : true
	 * @param mail
	 * @return true, if input is mail address 
	 */
	static public boolean isMailAddress(String mail) {
		 String check = "^([a-z0-9A-Z]+[-|\\._]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		 Pattern regex = Pattern.compile(check);
		 Matcher matcher = regex.matcher(mail);
		 return matcher.matches();
	}
}
