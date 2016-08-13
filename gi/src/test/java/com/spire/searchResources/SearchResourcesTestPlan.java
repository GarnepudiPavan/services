package com.spire.searchResources;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;


import spire.commons.search.response.CandidateSummary;
import spire.talent.gi.beans.NoteBean;
import spire.talent.gi.beans.SearchInput;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.service.utils.NotesServicesUtil;
import com.spire.base.service.utils.SearchUtil;
import com.spire.service.consumers.CandidateNotesConsumers;
import com.spire.service.consumers.SearchResourcesConsumer;

public class SearchResourcesTestPlan<SearchCriteriaBean> extends TestPlan {

	String hostName;
	String userId;
	String password;

	SearchResourcesConsumer candConsumer = null;
	SearchInput SearchBeanRequest = null;
	static String Input = null;
	SearchUtil searchUtil = null;

	/** 
	 * Passing HostName,UserName and Password from the xml.
	 */

	@BeforeTest(alwaysRun = true)
	public void setUp() {
		hostName = (String) ContextManager.getThreadContext().getHostAddress();
		userId = (String) "tester@logica.com";
		password = (String) "spire@123";
		//userId = (String) ContextManager.getThreadContext().getUserid();
		//password = (String) ContextManager.getThreadContext().getPassword();
		Logging.log("Start :: Login with Username: " + userId + "Password: "
				+ password + "and hostName: " + hostName);
		
	}


	/**
	 *  Vasista - Get -Similler profiles 
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/  
	@Test(groups = { "sanity", "GetSimillerProfiles" })
	public void GetSimillerProfiles() throws ClientProtocolException,
			IOException {
		
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		suggestConsumer.getSemilarProfiles(hostName);  
		Response responsebody =suggestConsumer.getSemilarProfiles(hostName);  
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******"+response);
		Assert.assertTrue(response.contains("6002:6005:c7133f48171543998a8ad4190e1353eb"));
	}
	
	
	/**
	 *  Bhagyasree - Get suggestion when passing keyword
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	
	@Test(groups = { "sanity" , "verifySuggestRequest" })
	public void verifySuggestRequest()throws ClientProtocolException,
	IOException {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody =suggestConsumer.getSuggest(hostName);  
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******"+response);
		Assert.assertTrue(response.contains("java"));
		
				
	}
	
	/**
	 *  Bhagyasree - Get error code when keyword is blank
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	
	@Test(groups = { "sanity" , "verifySuggestValidation" })
	public void verifySuggestValidation()throws ClientProtocolException,
	IOException {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		suggestConsumer.suggestValidation(hostName);  
		
				
	}
	
	/**
	 *  Author - Bhagyasree 
	 *  Test case description - Get suggestion when passing keyword having multiple words(Like project planning, project management)
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity" , "verifySuggestForSkillwithMultipleWords" })
	public void verifySuggestForSkillwithMultipleWords()throws ClientProtocolException,
	IOException {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody =suggestConsumer.getSuggestForSkillwithMultipleWords(hostName);  
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******"+response);
		Assert.assertTrue(response.contains("project planning"));
		
				
	}
	
	/**
	 *  Author - Bhagyasree 
	 *  Test case description - Get suggestion when passing keyword having SpecialCharacters(Like C#, .Net, C++)
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity" , "verifySuggestForSkillwithSpecialCharacter" })
	public void verifySuggestForSkillwithSpecialCharacter()throws ClientProtocolException,
	IOException {
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody =suggestConsumer.getSuggestForSkillwithSpecialCharacter(hostName);  
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******"+response);
		Assert.assertTrue(response.contains(".net"));
		
				
	}

	/**
	 *  Vasista - Get -Similler profiles 
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/  
	@Test(groups = { "sanity", "GetSimillerProfilesNegetive" })
	public void GetSimillerProfilesNegetive() throws ClientProtocolException,
			IOException {
		
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		suggestConsumer.getSemilarProfilesNegi(hostName);  
		Response responsebody =suggestConsumer.getSemilarProfiles(hostName);  
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******"+response);
		Assert.assertTrue(response.contains(" "));
	
	}
 /**
     *
     *  Author - Bhagyasree 
	 *  Test case description - Search candidates for skill search
	 * 
	 * 
     */

  
    
	@Test(groups = {"sanity", "searchCandidatesWithSkill"})
    public void searchCandidatesWithSkill() throws ClientProtocolException,
	IOException  {

        System.out.println("Search candidates with skill");
        Logging.log("Search candidates with skill");
        searchUtil = new SearchUtil();
        com.spire.base.service.utils.SearchInputRequest inputBean = searchUtil.getSearchInputBeanWithSkill();
        Logging.log("inputBean " + inputBean);
        
        SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
        
		Response responsebody =suggestConsumer.searchCandidate(inputBean, hostName);
		System.out.println("***** RESPONSE : responsebody : ******"+responsebody);
		Logging.log("responsebody " + responsebody);
		
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE : response : ******"+response);
		Logging.log("response " + response);
		
		Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");
		
       /*List<CandidateSummary> skills = SearchResourcesConsumer
        for (CandidateSummary skill : skills) {
            Logging.log("Skills " + skill.getSkills());
            AssertJUnit.assertTrue(skill.getSkills().contains(skillMap), "Searched skill is not same as candidate displaying skill");
       */
		}
	
	@Test(groups = {"sanity", "searchCandidatesWithSkillAndLocation"})
    public void searchCandidatesWithSkillAndLocation() throws ClientProtocolException,
	IOException  {

        System.out.println("Search candidates with skill and location");
        Logging.log("Search candidates with skill and location");
        searchUtil = new SearchUtil();
        com.spire.base.service.utils.SearchInputRequest inputBean = searchUtil.getSearchInputBeanWithSkillAndLocation();
        Logging.log("inputBean " + inputBean);
        
        SearchResourcesConsumer suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
        
		Response responsebody =suggestConsumer.searchCandidate(inputBean, hostName);
		System.out.println("***** RESPONSE : responsebody : ******"+responsebody);
		Logging.log("responsebody " + responsebody);
		
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE : response : ******"+response);
		Logging.log("response " + response);
		
		Assertion.assertEquals(responsebody.getStatus(), 200, "Response not successful");
		
       /*List<CandidateSummary> skills = SearchResourcesConsumer
        for (CandidateSummary skill : skills) {
            Logging.log("Skills " + skill.getSkills());
            AssertJUnit.assertTrue(skill.getSkills().contains(skillMap), "Searched skill is not same as candidate displaying skill");
       */
		}
	
	
	/**
	 *  Bhagyasree - Get list of saved search
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "getSavedSearch" })
	public void getSavedSearch() throws ClientProtocolException,
			IOException {
		
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		suggestConsumer.getSavedSearch(hostName);  
		Response responsebody =suggestConsumer.getSavedSearch(hostName);  
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******"+response);
		
	}
	
	/**
	 *  Bhagyasree - Get particular saved search by ID that exist
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "getSavedSearchById" })
	public void getSavedSearchById() throws ClientProtocolException,
			IOException {
		
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		suggestConsumer.getSavedSearchById(hostName);  
		Response responsebody =suggestConsumer.getSavedSearchById(hostName);  
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******"+response);
	//	Response response1 = sugestConsumer.readEntity(inputBean, hostName);
	//	Assertion.assertEquals(response1.getStatus(), 200, "Response not successfull");
		
	}
	/**
	 *  Bhagyasree - Get particular saved search by ID that doesnt not exist
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 **/
	@Test(groups = { "sanity", "getSavedSearchByNonExistingId" })
	public void getSavedSearchByNonExistingId() throws ClientProtocolException,
			IOException {
		
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		suggestConsumer.getSavedSearchByNonExistingId(hostName);  
		Response responsebody =suggestConsumer.getSavedSearchByNonExistingId(hostName);  
		String response = responsebody.readEntity(String.class);
		System.out.println("***** RESPONSE ******"+response);
		
	}
	/**
	 * priti- Get autocomplete with full skill
	 */
	@Test(groups = { "sanity","getautocompletewithfullskillRequest" })
	public void getautocompletewithfullskillRequest()
	{
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody=suggestConsumer.getautocompletefullskillsearch(hostName);  
		Assertion.assertEquals(200, responsebody.getStatus(), "Response expected 200 but found as:"+responsebody.getStatus());
		 String response = responsebody.readEntity(String.class);
		 
		 Assertion.assertTrue(response.contains("skill"), "skill not found in the response.");
		 Assertion.assertTrue(response.contains("java script"), "full skill not found in response");
	}
	/**
	 * priti-Get autocomplete with partial skill
	 */
	@Test(groups = { "sanity","getautocompletewithpartialskillRequest" })
	public void getautocompletewithpartialskillRequest()
	{
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody=suggestConsumer.getautocompletepartialskillsearch(hostName);  
		Assertion.assertEquals(200, responsebody.getStatus(), "Response expected 200 but found as:"+responsebody.getStatus());
		 String response = responsebody.readEntity(String.class);
		 
		 Assertion.assertTrue(response.contains("skill"), "skill not found in the response.");
		 Assertion.assertTrue(response.contains("ja"), "partial skill search not found in response");
	}
	/**
	 * priti- Get autocomplete with full institute
	 */
	
	@Test(groups = { "sanity","getautocompletewithfullinstituteRequest" })
	public void getautocompletewithfullinstituteRequest()
	{
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody=suggestConsumer.getautocompletefullinstitutesearch(hostName);  
		Assertion.assertEquals(200, responsebody.getStatus(), "Response expected 200 but found as:"+responsebody.getStatus());
		 String response = responsebody.readEntity(String.class);
		 
		 Assertion.assertTrue(response.contains("institute"), "institute not found in the response.");
		 Assertion.assertTrue(response.contains("M.D. University"), "full institute not found in response");
	}
	
	/**
	 * priti- Get autocomplete with partial institute
	 */
	
	@Test(groups = { "sanity","getautocompletewithpartialinstituteRequest" })
	public void getautocompletewithpartialinstituteRequest()
	{
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody=suggestConsumer.getautocompletepartialinstitutesearch(hostName);  
		Assertion.assertEquals(200, responsebody.getStatus(), "Response expected 200 but found as:"+responsebody.getStatus());
		 String response = responsebody.readEntity(String.class);
		 
		 Assertion.assertTrue(response.contains("institute"), "institute not found in the response.");
		Assertion.assertTrue(response.contains("Harvard"), "partial institute not found in response");
	}
	/**
	 * priti-Get autocomplete with full education
	 */
	
	
	
	@Test(groups = { "sanity","getautocompletewithfulleducationRequest" })
	public void getautocompletewithfulleducationRequest()
	{
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody=suggestConsumer.getautocompletefulleducationsearch(hostName);  
		Assertion.assertEquals(200, responsebody.getStatus(), "Response expected 200 but found as:"+responsebody.getStatus());
		 String response = responsebody.readEntity(String.class);
		 
		 Assertion.assertTrue(response.contains("education"), "education not found in the response.");
		 Assertion.assertTrue(response.contains("Diploma in Computer Applications"), "full education not found in response");
	}
	/**
	 * priti-Get autocomplete with partial education
	 */
	
	
	
	@Test(groups = { "sanity","getautocompletewithpartialeducationRequest" })
	public void getautocompletewithpartialeducationRequest()
	{
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody=suggestConsumer.getautocompletepartialeducationsearch(hostName);  
		Assertion.assertEquals(200, responsebody.getStatus(), "Response expected 200 but found as:"+responsebody.getStatus());
		 String response = responsebody.readEntity(String.class);
		 
		 Assertion.assertTrue(response.contains("education"), "education not found in the response.");
		 Assertion.assertTrue(response.contains("Diploma"), "partial education not found in response");
	}
	/**
	 * priti-Get autocomplete with full employer
	 */
	
	
	
	@Test(groups = { "sanity","getautocompletewithfullemployerRequest" })
	public void getautocompletewithfullemployerRequest()
	{
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody=suggestConsumer.getautocompletefullemployersearch(hostName);  
		Assertion.assertEquals(200, responsebody.getStatus(), "Response expected 200 but found as:"+responsebody.getStatus());
		 String response = responsebody.readEntity(String.class);
		 
		 Assertion.assertTrue(response.contains("employer"), "employer not found in the response.");
		 Assertion.assertTrue(response.contains("WIPRO TECHNOLOGIES Bangalore"), "full employer not found in response");
	}
	/**
	 * priti-Get autocomplete with partial employer
	 */
	
	
	
	@Test(groups = { "sanity","getautocompletepartialemployerRequest" })
	public void getautocompletepartialemployerRequest()
	{
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody=suggestConsumer.getautocompletepartialemployersearch(hostName);  
		Assertion.assertEquals(200, responsebody.getStatus(), "Response expected 200 but found as:"+responsebody.getStatus());
		 String response = responsebody.readEntity(String.class);
		 
		 Assertion.assertTrue(response.contains("employer"), "employer not found in the response.");
		 Assertion.assertTrue(response.contains("WIPRO"), "partial employer not found in response");
	}
	/**
	 * priti-Get autocomplete with full location
	 */
	@Test(groups = { "sanity","getautocompletfulllocationRequest" })
	public void getautocompletfulllocationRequest()
	{
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody=suggestConsumer.getautocompletefulllocationsearch(hostName);  
		Assertion.assertEquals(200, responsebody.getStatus(), "Response expected 200 but found as:"+responsebody.getStatus());
		 String response = responsebody.readEntity(String.class);
		 
		 Assertion.assertTrue(response.contains("location"), "location not found in the response.");
		 Assertion.assertTrue(response.contains("Bangalore"), "full location not found in response");
	}
	/**
	 * priti-Get autocomplete with partial location
	 */
	@Test(groups = { "sanity","getautocompletpartiallocationRequest" })
	public void getautocompletpartiallocationRequest()
	{
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody=suggestConsumer.getautocompletepartiallocationsearch(hostName);  
		Assertion.assertEquals(200, responsebody.getStatus(), "Response expected 200 but found as:"+responsebody.getStatus());
		 String response = responsebody.readEntity(String.class);
		 
		 Assertion.assertTrue(response.contains("location"), "location not found in the response.");
		 Assertion.assertTrue(response.contains("ba"), "partial location not found in response");
	}
	
	/**
	 * priti-Get autocomplete with full sourcetype
	 */
	@Test(groups = { "sanity","getautocompletfullsourcetypeRequest" })
	public void getautocompletfullsourcetypeRequest()
	{
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody=suggestConsumer.getautocompletefullsourcetypesearch(hostName);  
		Assertion.assertEquals(200, responsebody.getStatus(), "Response expected 200 but found as:"+responsebody.getStatus());
		 String response = responsebody.readEntity(String.class);
		 
		 Assertion.assertTrue(response.contains("sourcetype"), "sourcetype not found in the response.");
		 Assertion.assertTrue(response.contains("Referral - Employee"), "full sourcetype not found in response");
	}
	
	/**
	 * priti-Get autocomplete with partial sourcetype
	 */
	@Test(groups = { "sanity","getautocompletpartialsourcetypeRequest" })
	public void getautocompletpartialsourcetypeRequest()
	{
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody=suggestConsumer.getautocompletepartialsourcetypesearch(hostName);  
		Assertion.assertEquals(200, responsebody.getStatus(), "Response expected 200 but found as:"+responsebody.getStatus());
		 String response = responsebody.readEntity(String.class);
		 
		 Assertion.assertTrue(response.contains("sourcetype"), "sourcetype not found in the response.");
		 Assertion.assertTrue(response.contains("Referral"), "partial sourcetype not found in response");
	}
	
	/**
	 * priti-Get autocomplete with full sourcename
	 */
	@Test(groups = { "sanity","getautocompletfullsourcenameRequest" })
	public void getautocompletfullsourcenameRequest()
	{
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody=suggestConsumer.getautocompletefullsourcenamesearch(hostName);  
		Assertion.assertEquals(200, responsebody.getStatus(), "Response expected 200 but found as:"+responsebody.getStatus());
		 String response = responsebody.readEntity(String.class);
		 
		 Assertion.assertTrue(response.contains("sourcename"), "sourcename not found in the response.");
		 Assertion.assertTrue(response.contains("Media - Alumni"), "full sourcename not found in response");
	}
	
	/**
	 * priti-Get autocomplete with partial sourcename
	 */
	@Test(groups = { "sanity","getautocompletpartialsourcenameRequest" })
	public void getautocompletpartialsourcenameRequest()
	{
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody=suggestConsumer.getautocompletepartialsourcenamesearch(hostName);  
		Assertion.assertEquals(200, responsebody.getStatus(), "Response expected 200 but found as:"+responsebody.getStatus());
		 String response = responsebody.readEntity(String.class);
		 
		 Assertion.assertTrue(response.contains("sourcename"), "sourcename not found in the response.");
		 Assertion.assertTrue(response.contains("Alumni"), "partial sourcename not found in response");
	}
	
	/**
	 * priti-Get autocomplete with full status
	 */
	@Test(groups = { "sanity","getautocompletfullstatusRequest" })
	public void getautocompletfullstatusRequest()
	{
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody=suggestConsumer.getautocompletefullstatussearch(hostName);  
		Assertion.assertEquals(200, responsebody.getStatus(), "Response expected 200 but found as:"+responsebody.getStatus());
		 String response = responsebody.readEntity(String.class);
		 
		 Assertion.assertTrue(response.contains("status"), "status not found in the response.");
		 Assertion.assertTrue(response.contains("Hr Interviewed"), "full status not found in response");
	}
	
	/**
	 * priti-Get autocomplete with partial status
	 */
	@Test(groups = { "sanity","getautocompletpartialstatusRequest" })
	public void getautocompletpartialstatusRequest()
	{
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody=suggestConsumer.getautocompletepartialstatussearch(hostName);  
		Assertion.assertEquals(200, responsebody.getStatus(), "Response expected 200 but found as:"+responsebody.getStatus());
		 String response = responsebody.readEntity(String.class);
		 
		 Assertion.assertTrue(response.contains("status"), "status not found in the response.");
		 Assertion.assertTrue(response.contains("Interviewed"), "partial status not found in response");
	}
	/**
	 * priti-Get autocomplete without keyword
	 */
	
	@Test(groups = { "sanity","getautocompletewithoutkeywordRequest" })
	public void getautocompletewithoutkeywordRequest()
	{
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody=suggestConsumer.getautocompletewithoutkey(hostName);  
		Assertion.assertEquals(200, responsebody.getStatus(), "Response expected 200 but found as:"+responsebody.getStatus());
		 String response = responsebody.readEntity(String.class);
		 
		 Assertion.assertTrue(response.contains("skill"), "skill not found in the response.");
		 
	}
	/**
	 * priti-Get autocomplete without type
	 */
	
	@Test(groups = { "sanity","getautocompletewithouttypeRequest" })
	public void getautocompletewithouttypeRequest()
	{
		SearchResourcesConsumer suggestConsumer = null;
		suggestConsumer = new SearchResourcesConsumer(userId, password, hostName);
		Response responsebody=suggestConsumer.getautocompletewithouttype(hostName);  
		//Assertion.assertEquals(200, responsebody.getStatus(), "Response expected 200 but found as:"+responsebody.getStatus());
		Assertion.assertTrue(responsebody.getStatus()!=200, "response is unsuccessfull");
		 String response = responsebody.readEntity(String.class);
		 
		 Assertion.assertTrue(response.contains("Search input cannot be null or empty"), "Search input cannot be null or empty not found in the response.");
		 //Assertion.assertTrue(response.contains("ja"), "full institute not found in response");
	}
	
	
	



	
}