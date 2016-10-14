package com.spire.service.consumers;

import javax.ws.rs.core.Response;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.Logging;
import com.spire.base.service.BaseServiceConsumerNew;
import com.spire.base.service.ReadingServiceEndPointsProperties;

public class LookUpResourcesConsumer extends BaseServiceConsumerNew {
	
	public LookUpResourcesConsumer(String username, String password, String hostName) {
		Logging.log("Inside of Login");
		System.out.println("Inside of Login");
		getUserToken(username, password, hostName);
	}

	String lookUp = getServiceEndPoint("LOOK_UP");
	String getlistlookupwithspecialcharacter=getServiceEndPoint("LOOK_UP_WITH_SPECIAL_CHARACTER");
	public static String LOOKUP_FILTER = getServiceEndPoint("LOOKUP_FILTER");
	public static String LOOKUP_FILTER_MATCH = getServiceEndPoint("LOOKUP_FILTER_MATCH");
	
	public Response getLookupFilterMatchByTypeKeywordEntityType(String hostName, String type, String keyword, String entityType, String offset, String limit) {
		String serviceEndPoint = LOOKUP_FILTER_MATCH.replaceAll("hostAddress", hostName)
			+"?type="+type+"&keyword="+keyword+"&entityType="+entityType
			+"&offset="+offset+"&limit="+limit;
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		return executeGET(serviceEndPoint);
	}
	
	public Response getLookupFilterByTypeAndEntityType(String hostName, String type, String entityType) {
		String serviceEndPoint = LOOKUP_FILTER.replaceAll("hostAddress", hostName)+"?type="+type+"&entityType="+entityType;
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		return executeGET(serviceEndPoint);
	}

	public Response getListOfDemandFilter(String hostName) {
		String serviceEndPoint = lookUp.replaceAll("hostAddress", hostName)+"?type=REQUISITION_STATUS";
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		return executeGET(serviceEndPoint);
	}
	
	public Response getListOfDemandFilterByTypeNKeyword(String hostName) {
		String serviceEndPoint = lookUp.replaceAll("hostAddress", hostName)+"/match?type=REQUISITION_STATUS&keyword=O";
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		return response;
	}
	
	public Response verifyListOfDemandFilterWithoutType(String hostName){
		String serviceEndPoint = lookUp.replaceAll("hostAddress", hostName);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		return response;
	}
	
	public Response verifyListOfDemandFilterWithInvalidType(String hostName){
		String serviceEndPoint = lookUp.replaceAll("hostAddress", hostName)+"?type=agAH";
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		return response;
	}
	
	public Response getListOfDemandFilterByBlankTypeNBlankKeyword(String hostName){
		String serviceEndPoint = lookUp.replaceAll("hostAddress", hostName)+"/match";
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		return response;
	}
	
	public Response getListOfDemandFilterByPrimarySKillTypeNKeyword(String hostName) {
		String serviceEndPoint = lookUp.replaceAll("hostAddress", hostName)+"/match?type=PRIMARY_SKILL&keyword="+getServiceEndPoint("lookup_Skill_keyword");
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		System.out.println(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		return response;
	}
	
	public Response getListOfDemandFilterByBlankTypeNKeyword(String hostName){
		String serviceEndPoint = lookUp.replaceAll("hostAddress", hostName)+"match?keyword="+getServiceEndPoint("lookup_Skill_keyword");
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		return response;
	}
	
	public Response getListOfDemandFilterByTypeNBlankKeyword(String hostName){
		String serviceEndPoint = lookUp.replaceAll("hostAddress", hostName)+"match?type=REQUISITION_STATUS";
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		return response;
	}
	public Response getListOfDemandFilterWithSpecialCharacter(String hostName)
	{
		String serviceEndPoint = getlistlookupwithspecialcharacter.replaceAll("hostAddress", hostName);
		Logging.log(" EndPoint URL >>" + serviceEndPoint);
		Response response = executeGET(serviceEndPoint);
		return response;
		
		
	}
}
