package api.stepdefinition;


import static org.testng.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import Context.SharedContext;

import Utilities.ConfigReader;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import Utilities.LoggerLoad;
import api.endpoints.UserLogin;
import api.request.UserLogin_request;
import api.request.UserPatient_request;
import api.response.UserPatient_response;


public class UserPatientSteps extends BaseStep{
	Response response;
	UserPatient_request UserPatientreq;
	static int fileId;
	static UserPatient_response UserPatientFileAdded;
    public static String bearerToken;
    UserPatient_response UserPatientres;
    static SharedContext ContextSetup;
     public UserPatientSteps (SharedContext ContextSetup) {
		this.ContextSetup=ContextSetup;
}
     SharedContext sharedContext = new SharedContext();

	
	@Given("User is the registered patient with the valid {string} and {string}")
	public void user_is_the_registered_dietician_with_the_valid_and(String string, String string2) throws IOException {
		 
	        
	        String password = ConfigReader.getProperty("patientpassword");
	        String userLoginEmail = "Tyreek_Huel@gmail.com";
	        UserLogin_request Alogin = new UserLogin_request(password, userLoginEmail);
	        response = UserLogin.PatientLoginCredentials(Alogin);
        System.out.println("userLoginEmail = " + userLoginEmail);
	}


	@When("User sends HTTP POST Request for User login with valid endpoint for user patient")
	public void user_sends_http_post_request_for_user_login_with_valid_endpoint() {
		 response.then().statusCode(200);
	        bearerToken = response.jsonPath().getString("token");
	}

	@Then("User receives Bearer Token for user patient")
	public void user_receives_bearer_token() {
	    LoggerLoad.logInfo("BearerToken was created");

	}

	@Given("User creates GET Request for the patient endpoint with {string} scenario patientid for user patient")
	public void user_creates_get_request_for_the_patient_endpoint_with_scenario(String patientId) {
		
		
			LoggerLoad.logInfo("Get patient morbidity details request created for " + patientId);
		
	}

	@When("User sends {string} request with patientid for user patient")
	public void user_sends_request_with_patientid(String scenario) {
		try
		{
			switch(scenario)
			{
				case "ValidPatientIdMorbidityFileAttached" :
					response = userPatientEndpoints.GetPatientMorbidityDetails(SharedContext.getPatientIdValid());
					break;
					
				case "ValidPatientIdMorbidityFileMissing" :
					response = userPatientEndpoints.GetPatientMorbidityDetails(SharedContext.getPatientIdMissingFile());
					break;
				case "InvalidPatientIdMorbidityFileAttached" :
					response = userPatientEndpoints.GetPatientMorbidityDetails(000);
					break;
			}
	
			LoggerLoad.logInfo("Get patient morbidity details request sent for- " + scenario);
		}
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Then("User receives the status code and message based on {string} for user patient")
	public void user_receives_the_status_code_and_message_based_on(String scenario) {
		try
		{	
			UserPatient_response UserPatientResponse = null;
			
			switch(scenario)
			{
				case "ValidPatientIdMorbidityFileAttached" :
					response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_OK)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
							getClass().getClassLoader().getResourceAsStream("getpatientmorbiditydetailsjsonschema.json")));
					
					UserPatientResponse = response.getBody().as(UserPatient_response.class);
					break;
				
				case "ValidPatientIdMorbidityFileMissing" :
					response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_OK)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema("[]"));
					UserPatientResponse = response.getBody().as(UserPatient_response.class);
					break;
					
					
				case "InvalidPatientIdMorbidityFileAttached" :
					response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_NOT_FOUND)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
							getClass().getClassLoader().getResourceAsStream("400badrequestjsonschema.json")));
					
					
					JsonPath jsonPathEvaluator = response.jsonPath();
					List<UserPatient_response> UserPatientList = jsonPathEvaluator.getList("", UserPatient_response.class);
					UserPatientResponse = UserPatientList.get(0);
					break;
			}
	
			// Validate json response values
			
			assertEquals(UserPatientFileAdded.fileId, UserPatientResponse.fileId);
			assertEquals(UserPatientFileAdded.fileName, UserPatientResponse.fileName);
			assertEquals(UserPatientFileAdded.uploadDate, UserPatientResponse.uploadDate);
			assertEquals(UserPatientFileAdded.morbidConditions, UserPatientResponse.morbidConditions);
			assertEquals(UserPatientFileAdded.morbidConditionStr, UserPatientResponse.morbidConditionStr);
			
			LoggerLoad.logInfo("Get patient morbidity details response validated for- " + scenario);
		}
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	@Given("User creates GET Request for the Patient endpoint with {string} scenario fileid for user patient")
	public void user_creates_get_request_for_the_patient_endpoint_with_scenario_fileid(String fileId) {
		try
		{
			RestAssured.baseURI = ConfigReader.BaseURL();
			RequestSpecification request = RestAssured.given();
			
			LoggerLoad.logInfo("Get patient morbidity details request created for " + fileId);
		}
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@When("User sends {string} request with fileid for user patient")
	public void user_sends_request_with_fileid(String scenario) {
		try
		{
			switch(scenario)
			{
				case "ValidPatientIdMorbidityFileAttached" :
					response = userPatientEndpoints.GetPatientFileReport(fileId);
					break;
					
				case "ValidPatientIdMorbidityFileMissing" :
					response = userPatientEndpoints.GetPatientFileReport(fileId);
					break;
				case "InvalidPatientIdMorbidityFileAttached" :
					response = userPatientEndpoints.GetPatientFileReport(fileId);
					break;
			}
	
			LoggerLoad.logInfo("Get patient morbidity details request sent for- " + scenario);
		}
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}
	@Then("User receives the status code and message based on {string} with file report for user patient")
	public void user_receives_the_status_code_and_message_based_on_with_file_report(String scenario) {
		try
		{	
			UserPatient_response UserPatientResponse = null;
			File responsebodyfile=new File("src/test/resources/Hypo Thyroid-Report.pdf.pdf");
			
			switch(scenario)
			{
				case "ValidFileId" :
					response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_OK)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(responsebodyfile));
					
					UserPatientResponse = response.getBody().as(UserPatient_response.class);
					break;
					
					
				case "InvalidFileId" :
					response.then().assertThat()
						// Validate response status
						.statusCode(HttpStatus.SC_NOT_FOUND)
						// Validate content type
						.contentType(ContentType.JSON)
						// Validate json schema
						.body(JsonSchemaValidator.matchesJsonSchema(
							getClass().getClassLoader().getResourceAsStream("400badrequestjsonschema.json")));
					
					JsonPath jsonPathEvaluator = response.jsonPath();
					List<UserPatient_response> UserPatientList = jsonPathEvaluator.getList("", UserPatient_response.class);
					UserPatientResponse = UserPatientList.get(0);
					break;
			}
	
			// Validate json response values
			
			assertEquals(UserPatientFileAdded.MorbidityFile, UserPatientResponse.MorbidityFile);
			
			LoggerLoad.logInfo("Get patient file report validated for- " + scenario);
		}
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
		
	    
	} 

}
