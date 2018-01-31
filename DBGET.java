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
	
	public List<Map<String, String>> getList(String sqlString,String[] params, String[] paramType){
		Map<String, String> objmMap=new HashMap<String, String>();
		ArrayList<Map<String, String>> objmList=new ArrayList<Map<String,String>>();
		Connection con=null;
		ResultSet rs=null;
		PreparedStatement stmt=null;
		ResultSetMetaData rsMetaData=null;
		try {
			System.out.println("Creating connection.........");
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?","?","?");
			stmt=con.prepareStatement(sqlString);
			int countIndex=1;
			for (int i=0;i<params.length;i++) {
				switch (paramType[i]) {
					case "0" : stmt.setString(countIndex++, params[i]); break;
					case "1" : stmt.setInt(countIndex++, Integer.parseInt(params[i])); break;
					case "2" : stmt.setDouble(countIndex++, Double.parseDouble(params[i])); break;
				}
			}
			rs=stmt.executeQuery();
			rsMetaData=rs.getMetaData();
			int columnCount=rsMetaData.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					objmMap.put(rsMetaData.getColumnName(i), rs.getString(i));
				}
				objmList.add(objmMap);
				objmMap=new HashMap<String, String>();
			}
		} catch (Exception e) {	
			e.printStackTrace();
		}finally{
			try {
				if(rs!=null)
					rs.close();
				if(stmt!=null)
					stmt.close();
			} catch (Exception e) {
				 e.printStackTrace();
			}
		}
		return objmList;
	}
}
