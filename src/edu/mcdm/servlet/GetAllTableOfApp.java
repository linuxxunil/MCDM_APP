package edu.mcdm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.mcdm.database.DatabaseDriver;
import edu.mcdm.database.DatabaseSynchronize;
import edu.mcdm.database.SqliteDriver;
import edu.mcdm.database.DatabaseTable;


public class GetAllTableOfApp extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
										throws ServletException, IOException {
		
		
		String smeId = req.getParameter("smeId");
		String appId = req.getParameter("appId");
		
		String result =  new DatabaseSynchronize().getAllTableOfApp("Sme79", "aaa");
		
		PrintWriter out = resp.getWriter();
		out.println(result);
        out.close();
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
										throws ServletException, IOException {
		
		String smeId = req.getParameter("smeId");
		String appId = req.getParameter("appId");
		
		String result =  new DatabaseSynchronize().getAllTableOfApp(smeId, appId);
		
		PrintWriter out = resp.getWriter();
		out.println(result);
        out.close();
		
	}
	
}
