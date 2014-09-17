package edu.mcdm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.mcdm.common.StatusCode;
import edu.mcdm.database.DatabaseDriver;
import edu.mcdm.database.DatabaseSynchronize;
import edu.mcdm.database.MSSqlDriver;
import edu.mcdm.database.SqliteDriver;
import edu.mcdm.database.DatabaseTable;

/*
DatabaseDriver sqlite = new SqliteDriver("D:\\016_Workspace\\MCDM_APP\\sample.db");
sqlite.onConnect();
sqlite.createTable(DatabaseTable.Hospital.create());
sqlite.createTable(DatabaseTable.CodeFile.create());
sqlite.createTable(DatabaseTable.Doctor.create());
sqlite.createTable(DatabaseTable.Department.create());
sqlite.createTable(DatabaseTable.DoctorSchedule.create());
sqlite.createTable(DatabaseTable.User.create());
sqlite.close();
*/

public class SlowSyncDatabase extends HttpServlet{
	
	/* for test */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
										throws ServletException, IOException {

		String authRule = "{\"DFTN\":\"SMEMU2CSMPOp_SMEMUOnAuth_SLM2MTMRRRsp\",\"STATUS\":\"50701000\",\"STATUS_DESCRIPTION\":\"GET LICENSE OK\",\"TOKEN\":\"1\","
				+ "\"SMEID\":\"sme79\",\"USERID\":\"sme79\",\"APPID\":\"app1\",\"CSP_ID\":\"csp82\","
				+ "\"MODE\":\"OFFLINE\",\"DATETIME\":\"2014-08-25T17:00:02\",\"MOBILETOTP\":\"1e2e70e0a03763a8139177cbb42234c9bdd867f1\","
				+ "\"START\":\"2010-10-10T02:02:02\",\"EXPIRE\":\"2014-10-10T02:02:02\",\"CSMPTOTP\":\"6ba3ff708976d22f1dcbd72f3710da7b47d32e4f\","
				+ "\"perMaxAccessTime\":\"10\","
				+ "\"RESULT\":[{\"dbname\":\"dbname2\",\"perDbRead\":\"Y\",\"perDbWrite\":\"N\","
				+ "\"tbname\":\"tbname2\",\"perTbRead\":\"Y\",\"perTbWrite\":\"Y\"},{\"dbname\":\"dbname1\",\"perDbRead\":\"N\",\"perDbWrite\":\"Y\",\"tbname\":\"users\",\"perTbRead\":\"N\",\"perTbWrite\":\"N\"}],"
				+ "\"API\":\"MAPM/mSetDevice\",\"IP\":\"10.1.1.220\"}";
		String syncRule =  
				 "["
				+ "{\"Var[0]\":\"sme79\",\"Var[1]\":\"hospitalNo\"},"
				+ "{\"TblName\":\"users\",\"TblSql\":\"SELECT t1.* FROM users t1 WHERE userid=\'$Var[0]\'\"},"
				+ "{\"TblName\":\"Hospital\",\"TblSql\":\"SELECT t2.* FROM users t1,Hospital t2 WHERE userid=\'$Var[0]\' AND t1.HospitalNo=t2.HospitalNo\"}," 
				+ "{\"TblName\":\"DorSchedule\",\"TblSql\":\"SELECT t2.* FROM users t1,DorSchedule t2 WHERE userid=\'$Var[0]\' AND t1.HospitalNo=t2.HospitalNo\"}," 
				+ "{\"TblName\":\"Department\",\"TblSql\":\"SELECT t2.* FROM users t1,Department t2 WHERE userid=\'$Var[0]\' AND t1.HospitalNo=t2.HospitalNo\"}," 
				+ "{\"TblName\":\"Doctor\",\"TblSql\":\"SELECT t2.* FROM users t1,Doctor t2 WHERE userid=\'$Var[0]\' AND t1.HospitalNo=t2.HospitalNo\"},"
				+ "{\"TblName\":\"CodeFile\",\"TblSql\":\"SELECT t2.* FROM users t1,CodeFile t2 WHERE userid=\'$Var[0]\' AND t1.HospitalNo=t2.HospitalNo\"}" 
				+ "]";
		
		//String syncDbId = "7a6c0e29-0a24-4f48-878b-799f03f55105";
		//String syncDbId = "8746fb8b-3835-e38b-c97f-39fb9178359c";
		String smeId= "sme79";
		String srcDbUrl = "sqlserver://175.99.86.134:1433;instance=Cscheduling_SQL;DatabaseName=cscheduling;charset=utf-8";
		String srcDbUser = "sa";
		String srcDbPass = "ptch@RS";
		String appId = "30055";
		String userId = "sme79";
		String result =  new DatabaseSynchronize()
		.slowSynchronize(srcDbUrl, srcDbUser, srcDbPass, appId, authRule, syncRule, userId, smeId);

				
		PrintWriter out = resp.getWriter();
		out.println(result);
        out.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
										throws ServletException, IOException {

		String srcDbUrl = req.getParameter("srcDbUrl");
		String srcDbUser = req.getParameter("srcDbUser");
		String srcDbPass = req.getParameter("srcDbPass");
		String syncRule = req.getParameter("syncRule");
		String authRule = req.getParameter("authRule");
		String smeId = req.getParameter("smeId");
		String appId = req.getParameter("appId");
		String userId = req.getParameter("userId");
		String result =  new DatabaseSynchronize()
		.slowSynchronize(srcDbUrl, srcDbUser, srcDbPass, appId, authRule, syncRule, userId, smeId);

		PrintWriter out = resp.getWriter();
		out.println(result);
        out.close();
		
	}
	
}

