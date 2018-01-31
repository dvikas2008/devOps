package com.services;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path(value="/ords")
public class CustomerService {
	
	@GET
	@Path(value="/login")
	@Produces("application/json")
	public Map<String, Object> login(
			@QueryParam ("username") String username,
			@QueryParam("password") String password
			){
		Map<String, Object> objmMap=new HashMap<String, Object>();
		String params[]=new String[2];
		String paramType[]=new String[2];
		params[0]=username;
		params[1]=password;
		
		paramType[0]="0";
		paramType[1]="0";
		
		objmMap.put("items", new DBGET().getList(SQLService.login,params,paramType));
		return objmMap;
	}
	@GET
	@Path(value="/userProfile")
	@Produces("application/json")
	public Map<String, Object> userProfile(
			@QueryParam ("username") String username
			){
		Map<String, Object> objmMap=new HashMap<String, Object>();
		String params[]=new String[1];
		String paramType[]=new String[1];
		params[0]=username;
		
		paramType[0]="0";
		
		objmMap.put("items", new DBGET().getList(SQLService.userProfile,params,paramType));
		return objmMap;
	}
	@GET
	@Path(value="/getGenericData")
	@Produces("application/json")
	public Map<String, Object> getGenericData(
			@QueryParam ("id") String id,
			@QueryParam ("type") String type
			){
		Map<String, Object> objmMap=new HashMap<String, Object>();
		String params[]=new String[1];
		String paramType[]=new String[1];
		params[0]=id;
		paramType[0]="1";
		if(type.equals("cast"))
			objmMap.put("items", new DBGET().getList(SQLService.cast,params,paramType));
		else if(type.equals("sub_cast"))
			objmMap.put("items", new DBGET().getList(SQLService.subCast,params,paramType));
		else if(type.equals("profession"))
			objmMap.put("items", new DBGET().getList(SQLService.profession,params,paramType));
		else if(type.equals("education"))
			objmMap.put("items", new DBGET().getList(SQLService.education,params,paramType));
		else if(type.equals("marital_status"))
			objmMap.put("items", new DBGET().getList(SQLService.maritalStatus,params,paramType));
		else if(type.equals("state"))
			objmMap.put("items", new DBGET().getList(SQLService.state,params,paramType));
		return objmMap;
	}
}
