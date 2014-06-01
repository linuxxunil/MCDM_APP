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
		
		DatabaseSynchronize sync = new DatabaseSynchronize();
		
		sync.wget(new URL("http://csmp.servehttp.com/index.php"), "D:\\016_Workspace\\MCDM_APP\\index.html");
		//sync.slowSynchronize(null, "AAAA", "AAA");

		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
										throws ServletException, IOException {
		
		//String srcDbPath = req.getParameter("sourceDB");
		String syncRules = req.getParameter("syncRules");
		String syncDbId = req.getParameter("syncDBId ");
		String smeId = req.getParameter("smeId ");
		
		String result =  new DatabaseSynchronize().
				slowSynchronize(null, syncRules, syncDbId, smeId);
		
		PrintWriter out = resp.getWriter();
		out.println(result);
        out.close();
		
	}
	
}

