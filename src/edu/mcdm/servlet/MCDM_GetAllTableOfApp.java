package edu.mcdm.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MCDM_GetAllTableOfApp extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
										throws ServletException, IOException {
		/*
		SmeInfo smeInfo = new SmeInfo();
		smeInfo.smeID = req.getParameter("smeID");
		smeInfo.ftpHost = req.getParameter("ftpHost");
		smeInfo.ftpPort = Integer.parseInt(req.getParameter("ftpPort"));
		smeInfo.soapHost = req.getParameter("soapHost");
		smeInfo.soapPort = Integer.parseInt(req.getParameter("soapPort"));
		smeInfo.blobHost = req.getParameter("blobHost");
		smeInfo.blobPort = Integer.parseInt(req.getParameter("blobPort"));
		smeInfo.user = req.getParameter("user");
		smeInfo.pass = req.getParameter("pass");
	
		handle(smeInfo);
		
		PrintWriter out = resp.getWriter();
		out.println("OK");
        out.close();	
        */
	}
	
}
