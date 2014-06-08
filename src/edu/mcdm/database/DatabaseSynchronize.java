package edu.mcdm.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import edu.mcdm.common.StatusCode;
import edu.mcdm.security.Encrypt;

public class DatabaseSynchronize {
	private String dbDir = System.getenv("db_dir");
	private String tmpDir =System.getenv("tmp_dir");
	private String downloadDir = System.getenv("download_dir");
	
	public DatabaseSynchronize() {
		if ( System.getenv("use_cloudfoundry") == null ) {
			dbDir = "D:\\016_Workspace\\MCDM_APP\\mcdm\\db\\";
			tmpDir ="D:\\016_Workspace\\MCDM_APP\\mcdm\\tmp\\";
			downloadDir = "D:\\016_Workspace\\MCDM_APP\\mcdm\\download\\";
		}
	}
	
	private String getSampleRules() { //for test
		return "[" 
				+ "{\"Var[0]\":\"a@a.com\",\"Var[1]\":\"hospitsssalNo\"},"
				+ "{\"TblName\":\"users\",\"TblSql\":\"SELECT t1.* FROM users t1 WHERE userid=\'$Var[0]\'\"},"
				+ "{\"TblName\":\"Hospital\",\"TblSql\":\"SELECT t2.* FROM users t1,Hospital t2 WHERE userid=\'$Var[0]\' AND t1.HospitalNo=t2.HospitalNo\"}," 
				+ "{\"TblName\":\"DorSchedule\",\"TblSql\":\"SELECT t2.* FROM users t1,DorSchedule t2 WHERE userid=\'$Var[0]\' AND t1.HospitalNo=t2.HospitalNo\"}," 
				+ "{\"TblName\":\"Department\",\"TblSql\":\"SELECT t2.* FROM users t1,Department t2 WHERE userid=\'$Var[0]\' AND t1.HospitalNo=t2.HospitalNo\"}," 
				+ "{\"TblName\":\"Doctor\",\"TblSql\":\"SELECT t2.* FROM users t1,Doctor t2 WHERE userid=\'$Var[0]\' AND t1.HospitalNo=t2.HospitalNo\"},"
				+ "{\"TblName\":\"CodeFile\",\"TblSql\":\"SELECT t2.* FROM users t1,CodeFile t2 WHERE userid=\'$Var[0]\' AND t1.HospitalNo=t2.HospitalNo\"}" 
				+ "]";
	}
		
	public String getDatabasePath(String smeId, String appId) {

		String fileName = Encrypt.MD5(smeId+appId);
		String randomFileName = UUID.randomUUID().toString(); 
		int ret = copyFile(dbDir+fileName,tmpDir+randomFileName) ;
		if ( ret == StatusCode.success ) 
			return randomFileName;
		else
			return null;
	}

	public String getAllTableOfApp(String smeId, String appId) {
		if ( smeId == null || smeId.isEmpty() ) {
			StatusCode.ERR_PARM_SMEID_IS_NULL(); 
			return "{\"STATUS:\"-103\",\"STATUS_DESCRIPTION\":\"SMEID Error\","
				  + "\"APP_DB_ID\":\"NULL\",\"APP_TABLES_LENGTH\":\"0\"}";
		} else if ( appId == null || appId.isEmpty() ) {
			StatusCode.ERR_PARM_APPID_IS_NULL(); 
			return "{\"STATUS:\"-102\",\"STATUS_DESCRIPTION\":\"APPID Error\","
			  + "\"APP_DB_ID\":\"NULL\",\"APP_TABLES_LENGTH\":\"0\"}";
		}
		
		String jsonFormat = null;
		try {
			
			String appDbPath = getDatabasePath(smeId, appId);
			
			if ( appDbPath == null ) {
				StatusCode.ERR_GET_DB_PATH_ERROR();
				return null;
			}
			
			DatabaseDriver db = 
					new SqliteDriver(tmpDir+appDbPath);
		
			db.onConnect();
		
			String[] tables = db.getTables();
			
			jsonFormat = String.format( "{"
					+ "\"STATUS:\"%s\"," 
					+ "\"STATUS_DESCRIPTION\":\"%s\","
					+ "\"APP_DB_ID\":\"%s\","
					+ "\"APP_TABLES_LENGTH\":\"%d\""
					,0,"OK",appDbPath,tables.length
					);
						
			
			for ( int i=0; i<tables.length; i++) {
				jsonFormat += ",\"APP_TABLE_NAME[" + i + "]\":\"" + tables[i] + "\"";
			}
			jsonFormat += "}";
			
			db.close();
		} catch ( Exception e ) {
			StatusCode.ERR_UNKOWN_ERROR();
			return "{\"STATUS:\"-999\",\"STATUS_DESCRIPTION\":\"UNKOWN Error\","
					  + "\"APP_DB_ID\":\"NULL\",\"APP_TABLES_LENGTH:\"0\"}";
		}
		return jsonFormat;
	}
	
	public String  slowSynchronize(String srcDbPath, String syncRules, 
											String syncDbId, String smeId) {
		String[][] rules = null;
		String syncDbPath = tmpDir + syncDbId;
		String downloadDbPath = downloadDir + syncDbId;
		File f = new File(syncDbPath);
		
		if ( srcDbPath != null ) {
			StatusCode.ERR_PARM_SOURCE_DB_ISNOT_ERROR(); 
			return "{\"STATUS:\"-105\",\"STATUS_DESCRIPTION\":\"Source DB Error\","
				  + "\"APP_DB_ID\":\"NULL\",\"APP_TABLES_LENGTH\":\"0\"}";
		} else if ((rules=getTableSyncRules(syncRules)) == null ) {
			return "{\"STATUS:\"-108\",\"STATUS_DESCRIPTION\":\"Sync Rules Error\","
					  + "\"APP_DB_ID\":\"NULL\",\"APP_TABLES_LENGTH\":\"0\"}";
		} else if ( syncDbId == null || syncDbId.isEmpty() || !f.exists() || !f.isFile()) {
			StatusCode.ERR_PARM_SYNC_DB_IS_NULL(); 
			return "{\"STATUS:\"-106\",\"STATUS_DESCRIPTION\":\"Sync DB ID Error\","
			  + "\"APP_DB_ID\":\"NULL\",\"APP_TABLES_LENGTH\":\"0\"}";
		} else if ( smeId == null || smeId.isEmpty() ) {
			StatusCode.ERR_PARM_SMEID_IS_NULL(); 
			return "{\"STATUS:\"-103\",\"STATUS_DESCRIPTION\":\"SMEID Error\","
			  + "\"APP_DB_ID\":\"NULL\",\"APP_TABLES_LENGTH\":\"0\"}";
		}

		for ( int i=0 ; i<rules[0].length; i++ ) {
			if ( execDataFromServerToSqlite(syncDbPath, rules[0][i], rules[1][i])
															!= StatusCode.success ) {
				deleteFile(syncDbPath);
				return "{\"STATUS:\"-201\",\"STATUS_DESCRIPTION\":\"Synchronized Error\","
				  + "\"APP_DB_ID\":\"NULL\",\"APP_TABLES_LENGTH\":\"0\"}";
			}
		}

		if ( moveFile(syncDbPath, downloadDbPath) != 0 )
			return "{\"STATUS:\"-201\",\"STATUS_DESCRIPTION\":\"Synchronized Error\","
			  + "\"APP_DB_ID\":\"NULL\",\"APP_TABLES_LENGTH\":\"0\"}";
		
		return "{\"STATUS:\"0\",\"STATUS_DESCRIPTION\":\"OK\","
				  + "\"DB_DOWNLOAD_LINK\":\""+syncDbId+"\"}";
	}	
	
	private String[][] getTableSyncRules(String jsonRules) {
		String[][] rules = null;
		System.out.println(jsonRules);
		try {
			JSONArray jsonArray = new JSONArray(
					new JSONTokener(jsonRules));
		
			// Get Value
			JSONObject jsonObj = jsonArray.getJSONObject(0);
			int varLen = jsonObj.length();

			String[] var = null;
			if ( varLen > 0 ) {
				var = new String[varLen];
				for ( int i=0; i<varLen; i++) {
					var[i] = (String)jsonObj.get("Var["+i+"]");
				}
			}
	
			 rules = new String[2][jsonArray.length()-1];
			//String[] tables = new String[jsonArray.length()-1];
			//String[] syncSql = new String[jsonArray.length()-1];
			String tmp = null;
		
			for ( int i=1; i<jsonArray.length(); i++ ) {
				jsonObj = jsonArray.getJSONObject(i);
				rules[0][i-1] = (String)jsonObj.get("TblName");
				tmp = (String)jsonObj.get("TblSql");
			
				for ( int j=0; j<varLen; j++ ) {
					tmp =tmp.replace("$Var["+j+"]", var[j]);
				}	
				rules[1][i-1] = tmp;
			}
		} catch (JSONException e ) {
			StatusCode.ERR_JSON_PARSER_ERROR(e.getMessage());
			return null;
		}
		return rules;
	}
	
	private int execDataFromServerToSqlite(String dbPath, String table,String syncSql) {
		DatabaseDriver ms = new MSSqlDriver();
		DatabaseDriver sqlite = new SqliteDriver(dbPath);
	
		int ret = StatusCode.success;
		ms.onConnect();
		sqlite.onConnect();
		
		try {
			ResultSet rs = ms.select(syncSql);

			if ( rs == null ) {
				return StatusCode.ERR_EXE_USER_RULES_ERROR();
			}
			
			ResultSetMetaData meta = rs.getMetaData();
			
			if ( meta == null || meta.getColumnCount() <= 0) {
				return StatusCode.ERR_COLS_NUMBER_ERROR();
			}
			
			// Get Column Name
			String columns = "(" + meta.getColumnName(1);
			for ( int i=2; i<=meta.getColumnCount(); i++ ) {
				columns += "," + meta.getColumnName(i) + "";
			}
			columns += ")";
	
			String sql = "INSERT INTO " + table + " " + columns + " VALUES ";
			String values = null;
			while ( rs.next() ) {
				values = "(\'" + rs.getString(1) + "\'";
				for ( int i=2; i<=meta.getColumnCount(); i++ )
					values += ",\'" + rs.getString(i) + "\'";
				values += ")";
				
				System.out.println(sql+values);
				if ( sqlite.inset(sql+values) != StatusCode.success ) {
					ret = StatusCode.ERR_EXE_USER_RULES_TO_DB_ERROR();
					break;
				}
			}
			
		} catch (SQLException e) {
			ret = StatusCode.ERR_SQL_SYNTAX_IS_ILLEGAL(e.getMessage());
		} finally {
			ms.close();
			sqlite.close();
		}
		
		return ret;
	}
	
	private int copyFile(String src, String dst) {

		InputStream im = null;
		OutputStream om = null;
		try {
			 im = new FileInputStream(new File(src));
			 
			//For Overwrite the file.
			 om = new FileOutputStream(new File(dst));

			byte[] buf = new byte[1024];
			int len;

			while ((len = im.read(buf)) > 0){
				om.write(buf, 0, len);
			}

		} catch(FileNotFoundException ex){
			return StatusCode.ERR_COPY_FILE_NOT_FOUND_ERROR();
		} catch(IOException e){
			return StatusCode.ERR_COPY_FILE_IO_ERROR();
		} finally {
			try {
				im.close();
				om.close();
			} catch ( Exception e1 ) {
				// noting
			}
		}
		return StatusCode.success;
	}
	
	private int deleteFile(String path ){
		// A File object to represent the filename
		File f = new File(path);
		
		// Make sure the file or directory exists and isn't write protected
		if (!f.exists())
			return StatusCode.ERR_COPY_FILE_NOT_FOUND_ERROR();

		if (!f.canWrite())
			return StatusCode.ERR_RM_FILE_CANNOT_WRITE_ERROR();

		// If it is a directory, make sure it is empt
		if (f.isDirectory()) {
			String[] files = f.list();
			if (files.length > 0)
				return StatusCode.ERR_RM_DIR_NOT_EMPTY_ERROR();
		}

		// Attempt to delete it
		if ( !f.delete() ) {
			return StatusCode.ERR_RM_FILE_ERROR();
		}
		return StatusCode.success;
	}
	
	private int moveFile(String src, String dst) {
		
		if ( copyFile(src,dst) != 0 ) 
			return StatusCode.ERR_MOVE_FILE_ERROR();
		if ( deleteFile(src) != 0 )
			return StatusCode.ERR_MOVE_FILE_ERROR();
		
		return StatusCode.success;
	}
	
	public int wget(URL url, String target) {
		URLConnection conn = null; 
		
		try {
			conn = url.openConnection();
			conn.connect();
			
			String type = conn.getContentType();

			if (type != null) {
				byte[] buffer = new byte[4 * 1024];
				int read;

				FileOutputStream os = new FileOutputStream(target);
				InputStream in = conn.getInputStream();

				while ((read = in.read(buffer)) > 0) {
					os.write(buffer, 0, read);
				}

				os.close();
				in.close();
				
			} else {
				return StatusCode.ERR_WGET_FILE_ERROR();
			}
			
		} catch (IOException e ) {
			return StatusCode.ERR_WGET_FILE_ERROR();
		}
		
		return StatusCode.success;
	}
}
