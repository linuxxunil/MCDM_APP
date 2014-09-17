package edu.mcdm.sqlparser;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;


public class sqlparser {
	
	public sqlparser()
	{
		
	}
	
	public String[] sqlParser(String sql)
	{
		try {
			CCJSqlParserManager pm = new CCJSqlParserManager();
			Statement statement = pm.parse(new StringReader(sql));
			if (statement instanceof Select) {
				Select selectStatement = (Select) statement;
				TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
				List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
				String[] s=new String[tableList.size()];
				for(int i=0;i<s.length;i++)
					s[i]=tableList.get(i);
				return s;
			}
		} catch (JSQLParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}