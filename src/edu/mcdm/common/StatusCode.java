package edu.mcdm.common;

/**
 * StatusCode Definition 
 *  Status Code 是從 00000000~99999999 and -00000001~-99999999
 *  的一組數值，0:表示成功 / 正: Information / 負: Error 
 *  
 *  第 1 digital(MSB) = Log Type ( Developer Log (0) / User Log(1) )
 *  第 2 digital(MSB) = Package Number
 *  第 3~4 digital(MSB) = Class Number
 *  第 5~6 digital(MSB) = Function Number
 *  第 7~8 digital(MSB) = Status Code
 *  
 *  0x0 0 0 00 00 00
 *  1 2  3  4  5
 *  
 *  
 * @author jesse
 *
 */

public class StatusCode {
	public static final int success = 0;
	
	/**
	 * 
	 * @param errCode
	 * @param errDescription
	 * @return
	 */
	
	private static int log(String errCode, String errDescription) {
		System.out.println("ErrCode: " + errCode + ", ErrDescription: " + errDescription);
		return Integer.valueOf(errCode);
	}
	
	
	
	
	public static int model_DatabaseDriver = 0x01020000;
	public static int model_MSSqlDriver    = 0x01030000;
	
	// SqliteDriver
	public static int ERR_OPEN_DIR(String path) {
		
		return log("-101","Open sqlite dir error");
	}
		
	public static int ERR_OPEN_SQLITE_FILE(String path) {
		return log("-102","Open Sqlite file error");
	}
	
	public static int ERR_INITIAL_DB_NOT_SUCCESS() {
		return log("-103","Inital DB don't success");
	}
	
	public static int ERR_SQL_SYNTAX_IS_NULL() {
		return log("-104","SQL syntax is null");
	}
	
	public static int ERR_SQL_SYNTAX_IS_ILLEGAL(String sql) {
		return log("-105","SQL syntax is illegal");
	}
	
	public static int ERR_MSSQL_CONNECT_ERROR() {
		return log("-106","Cannot connect to MSSQL ");
	}
	
	public static int ERR_JTDS_ERROR() {
		return log("-107","Cannot found JTDS or version error ");
	}
	
	// Account
	public static int WAR_USERID_NULL_OR_EMPTY() {
		return log("101","userid is null or empty");
	}
	
	public static int WAR_USERNAME_NULL_OR_EMPTY() {
		return log("101","username is null or empty");
	}
	
	public static int WAR_USERPASSWD_NULL_OR_EMPTY() {
		return log("-101","userpasswd is null or empty");
	}
	
	public static int WAR_REGISTER_FAIL() {
		return log("101","Register error");
	}
	
	public static int WAR_LOGIN_FAIL() {
		return log("101","Login error");
	}
}
