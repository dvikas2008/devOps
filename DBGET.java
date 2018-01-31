package com.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBGET {
	
	public List<Map<String, Object>> getList(String sqlString,String[] params, int[] paramType){
		Map<String, Object> objmMap=new HashMap<String, Object>();
		ArrayList<Map<String, Object>> objmList=new ArrayList<Map<String,Object>>();
		Connection con=null;
		ResultSet rs=null;
		PreparedStatement stmt=null;
		ResultSetMetaData rsMetaData=null;
		try {
			System.out.println("Creating connection.........");
			String driver = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driver);
			String url="jdbc:oracle:thin:@localhost:1521:orcl";
			con = DriverManager.getConnection(url,"test","root$123");
			
			stmt=con.prepareStatement(sqlString);
			int countIndex=1;
			for (int i=0;i<params.length;i++) {
				switch (paramType[i]) {
					case ParamType.STRING_TYPE : stmt.setString(countIndex++, params[i]); break;
					case ParamType.INTEGER_TYPE : stmt.setInt(countIndex++, Integer.parseInt(params[i])); break;
					case ParamType.DOUBLE_TYPE : stmt.setDouble(countIndex++, Double.parseDouble(params[i])); break;
					case ParamType.FLOAT_TYPE : stmt.setFloat(countIndex++, Float.parseFloat(params[i])); break;
					case ParamType.BOOLEAN_TYPE : stmt.setBoolean(countIndex++, Boolean.parseBoolean(params[i])); break;
				}
			}
			rs=stmt.executeQuery();
			rsMetaData=rs.getMetaData();
			int columnCount=rsMetaData.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					if(rsMetaData.getColumnTypeName(i).toUpperCase().indexOf("DATE")!=-1)
						objmMap.put(rsMetaData.getColumnName(i), rs.getString(i));
					if(rsMetaData.getColumnTypeName(i).toUpperCase().indexOf("VARCHAR")!=-1)
						objmMap.put(rsMetaData.getColumnName(i), rs.getString(i));
					else if(rsMetaData.getColumnTypeName(i).toUpperCase().indexOf("NUMBER")!=-1 || rsMetaData.getColumnTypeName(1).toUpperCase().indexOf("INT")!=-1)
						if(rsMetaData.getScale(i) > 0)
							objmMap.put(rsMetaData.getColumnName(i), rs.getDouble(i));
						else
							objmMap.put(rsMetaData.getColumnName(i), rs.getInt(i));
					else if(rsMetaData.getColumnTypeName(i).toUpperCase().indexOf("BOOLEAN")!=-1 || rsMetaData.getColumnTypeName(1).toUpperCase().indexOf("TINYINT")!=-1)
						objmMap.put(rsMetaData.getColumnName(i), rs.getBoolean(i));
					else if(rsMetaData.getColumnTypeName(i).toUpperCase().indexOf("DOUBLE")!=-1)
						objmMap.put(rsMetaData.getColumnName(i), rs.getDouble(i));
					else if(rsMetaData.getColumnTypeName(i).toUpperCase().indexOf("FLOAT")!=-1)
						objmMap.put(rsMetaData.getColumnName(i), rs.getFloat(i));
				}
				objmList.add(objmMap);
				objmMap=new HashMap<String, Object>();
			}
			System.out.println("Data fectch complete.");
		} catch (Exception e) {	
			e.printStackTrace();
		}finally{
			try {
				if(rs!=null)
					rs.close();
				if(stmt!=null)
					stmt.close();
				if(con!=null)
					con.close();
				System.gc();
			} catch (Exception e) {
				 e.printStackTrace();
			}
		}
		return objmList;
	}

}
