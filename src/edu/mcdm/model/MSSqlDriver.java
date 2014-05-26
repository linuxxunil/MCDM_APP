package edu.mcdm.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import edu.mcdm.common.StatusCode;


public class MSSqlDriver extends DatabaseDriver {

	private String UserName = "sa";
	private String Password = "ptch@RS";
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	@Override
	public int onConnect() {
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			con = DriverManager
					.getConnection(
							"jdbc:jtds:sqlserver://175.99.86.134:1433;instance=Cscheduling_SQL;DatabaseName=cscheduling;charset=utf-8",
							UserName, Password);
		} catch (ClassNotFoundException e1) {
			return StatusCode.ERR_JTDS_ERROR();
		} catch (SQLException e) {
			return StatusCode.ERR_MSSQL_CONNECT_ERROR();
		}
		return StatusCode.success;
	}

	@Override
	public int inset(String sql) {
		if (con == null)
			return StatusCode.ERR_INITIAL_DB_NOT_SUCCESS();
		else if (sql.isEmpty())
			return StatusCode.ERR_SQL_SYNTAX_IS_NULL();

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			StatusCode.ERR_SQL_SYNTAX_IS_ILLEGAL(sql);
		}
		return StatusCode.success;
	}

	@Override
	public int inset(String tblName, String colsName, String colsValue) {
		String sql = String.format("INSERT INTO '%s' (%s) VALUES (%s)",
				tblName, colsName, colsValue);
		return inset(sql);
	}

	@Override
	public int createTable(String sql) {
		if (con == null)
			return StatusCode.ERR_INITIAL_DB_NOT_SUCCESS();
		else if (sql.isEmpty())
			return StatusCode.ERR_SQL_SYNTAX_IS_NULL();

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			StatusCode.ERR_SQL_SYNTAX_IS_ILLEGAL(sql);
		}
		return StatusCode.success;
	}

	@Override
	public void beginTransaction() {
		// TODO Auto-generated method stub

	}

	@Override
	public void endTransaction() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTransactionSuccessful() {
		// TODO Auto-generated method stub

	}

	/*
	@Override
	public ResultSet selectMS(String sql) {
		if (con == null) {
			//StatusCode.ERR_INITIAL_DB_NOT_SUCCESS();
			return null;
		} else if (sql.isEmpty()) {
			//StatusCode.ERR_SQL_SYNTAX_IS_NULL();
			return null;
		}

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			StatusCode.ERR_SQL_SYNTAX_IS_ILLEGAL(sql);
		}
		return rs;

	}
	*/

	//@Override
	//public ResultCursor selectMS(String tblName, String cols, String whereExpr) {
	//	String sql = String.format("SELECT %s FROM %s", cols, tblName);

	//	if (whereExpr != null) {
	//		sql += " WHERE " + whereExpr;
	//	}
	//	return selectMS(sql);
	//}

	@Override
	public ResultCursor select(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultCursor select(String tblName, String cols, String whereExpr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(String sql) {
		if (con == null)
			return StatusCode.ERR_INITIAL_DB_NOT_SUCCESS();
		else if (sql.isEmpty())
			return StatusCode.ERR_SQL_SYNTAX_IS_NULL();

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			StatusCode.ERR_SQL_SYNTAX_IS_ILLEGAL(sql);
		}
		return StatusCode.success;
	}

	@Override
	public int update(String sql) {
		if (con == null)
			return StatusCode.ERR_INITIAL_DB_NOT_SUCCESS();
		else if (sql.isEmpty())
			return StatusCode.ERR_SQL_SYNTAX_IS_NULL();

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			StatusCode.ERR_SQL_SYNTAX_IS_ILLEGAL(sql);
		}
		return StatusCode.success;
	}

	@Override
	public int update(String table, String repValues, String whereExpr) {
		String sql = String.format("UPDATE '%s' SET %s WHERE %s", table, repValues, whereExpr);
		return update(sql);
	}

}
