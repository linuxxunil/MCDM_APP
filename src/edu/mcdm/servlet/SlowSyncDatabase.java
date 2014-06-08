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
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
										throws ServletException, IOException {
		
		String syncRules =  "[" 
				+ "{\"Var[0]\":\"a@a.com\",\"Var[1]\":\"hospitsssalNo\"},"
				+ "{\"TblName\":\"users\",\"TblSql\":\"SELECT t1.* FROM users t1 WHERE userid=\'$Var[0]\'\"},"
				+ "{\"TblName\":\"Hospital\",\"TblSql\":\"SELECT t2.* FROM users t1,Hospital t2 WHERE userid=\'$Var[0]\' AND t1.HospitalNo=t2.HospitalNo\"}," 
				+ "{\"TblName\":\"DorSchedule\",\"TblSql\":\"SELECT t2.* FROM users t1,DorSchedule t2 WHERE userid=\'$Var[0]\' AND t1.HospitalNo=t2.HospitalNo\"}," 
				+ "{\"TblName\":\"Department\",\"TblSql\":\"SELECT t2.* FROM users t1,Department t2 WHERE userid=\'$Var[0]\' AND t1.HospitalNo=t2.HospitalNo\"}," 
				+ "{\"TblName\":\"Doctor\",\"TblSql\":\"SELECT t2.* FROM users t1,Doctor t2 WHERE userid=\'$Var[0]\' AND t1.HospitalNo=t2.HospitalNo\"},"
				+ "{\"TblName\":\"CodeFile\",\"TblSql\":\"SELECT t2.* FROM users t1,CodeFile t2 WHERE userid=\'$Var[0]\' AND t1.HospitalNo=t2.HospitalNo\"}" 
				+ "]";
		
		System.out.println(syncRules);
		String syncDbId = "7a6c0e29-0a24-4f48-878b-799f03f55105";
		String smeId= "a@a.com";

		String result =  new DatabaseSynchronize().slowSynchronize(null,syncRules, syncDbId, smeId);
		
		PrintWriter out = resp.getWriter();
		out.println(result);
        out.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
										throws ServletException, IOException {
		
		//String srcDbPath = req.getParameter("sourceDB");
		String syncRules = req.getParameter("syncRules");
		String syncDbId = req.getParameter("syncDBId");
		String smeId = req.getParameter("smeId");
		
		String result =  new DatabaseSynchronize().
				slowSynchronize(null, syncRules, syncDbId, smeId);
		
		PrintWriter out = resp.getWriter();
		out.println(result);
        out.close();
		
	}
	
}

