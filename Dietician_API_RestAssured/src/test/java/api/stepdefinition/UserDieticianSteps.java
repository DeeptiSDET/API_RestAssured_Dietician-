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
import api.request.UserDietician_request;
import api.request.UserLogin_request;
import api.response.UserDietician_response;


public class UserDieticianSteps extends BaseStep{
	Response response;
	UserDietician_request userDieticianreq;
	static int fileId;
	static UserDietician_response userDieticianFileAdded;
    public static String bearerToken;

	UserDietician_response userDieticianres;
	@Given("User is the registered Dietician with valid {string} and {string}")
	public void user_is_the_registered_dietician_with_valid_and(String string, String string2) throws IOException {
		 String filePath = ConfigReader.getProperty("DRUserlogin");
	        BufferedReader reader = new BufferedReader(new FileReader(filePath));

	        String jsonContent = reader.lines().reduce("", (line1, line2) -> line1 + line2);

	        JSONObject jsonObject = new JSONObject(jsonContent);

	        String passwor = jsonObject.optString("password");
	        String userLoginEmail = jsonObject.optString("userLoginEmail");

	        UserLogin_request Alogin = new UserLogin_request(passwor, userLoginEmail);
	        response = UserLogin.UserLoginCredentials(Alogin);

	        reader.close();
	}
	 @When("User sends HTTP POST Request for User login with a valid endpoint")
	    public void user_sends_http_post_request_for_user_login_with_a_valid_endpoint() {
	        response.then().statusCode(200);
	        bearerToken = response.jsonPath().getString("token");
	    }
	    @Then("User receives the Bearer Token")
		public void user_receives_the_bearer_token() {
		    LoggerLoad.logInfo("BearerToken was created");

		}


	@Given("User creates GET Request for the Patient endpoint with {string} scenario patientid")
	public void user_creates_get_request_for_the_patient_endpoint_with_scenario(String patientId) {
		
			LoggerLoad.logInfo("Get patient morbidity details request created for " + patientId);
		}

	@When("User sends {string} request with patientid")
	public void user_sends_request_with_patientid(String scenario) {
		try
		{
			switch(scenario)
			{
				case "ValidPatientIdMorbidityFileAttached" :
					response = userDieticianEndpoints.GetPatientMorbidityDetails(SharedContext.getPatientIdValid());
					break;
					
				case "ValidPatientIdMorbidityFileMissing" :
					response = userDieticianEndpoints.GetPatientMorbidityDetails(SharedContext.getPatientIdMissingFile());
					break;
				case "InvalidPatientIdMorbidityFileAttached" :
					response = userDieticianEndpoints.GetPatientMorbidityDetails(000);
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

	@Then("User receives the status code and message based on {string}")
	public void user_receives_the_status_code_and_message_based_on(String scenario) {
		try
		{	
			UserDietician_response userDieticianResponse = null;
			
			switch(scenario)
			{
				case "ValidPatientIdMorbidityFileAttached" :
					response.then().assertThat()
						.statusCode(HttpStatus.SC_OK)
						.contentType(ContentType.JSON)
						.body(JsonSchemaValidator.matchesJsonSchema(
							getClass().getClassLoader().getResourceAsStream("getpatientmorbiditydetailsjsonschema.json")));
					
					userDieticianResponse = response.getBody().as(UserDietician_response.class);
					break;
				
				case "ValidPatientIdMorbidityFileMissing" :
					response.then().assertThat()
						.statusCode(HttpStatus.SC_OK)
						.contentType(ContentType.JSON)
						.body(JsonSchemaValidator.matchesJsonSchema("[]"));
					userDieticianResponse = response.getBody().as(UserDietician_response.class);
					break;
					
					
				case "InvalidPatientIdMorbidityFileAttached" :
//					if(scenario.equals("InvalidPatientIdMorbidityFileAttached")) {
					if(response.statusCode() == 404 || response.statusCode() == 405) {
						
						response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("404userdieticianschema.json")));
					}
				}
					
			
	
			
			assertEquals(userDieticianFileAdded.fileId, userDieticianResponse.fileId);
			assertEquals(userDieticianFileAdded.fileName, userDieticianResponse.fileName);
			assertEquals(userDieticianFileAdded.uploadDate, userDieticianResponse.uploadDate);
			assertEquals(userDieticianFileAdded.morbidConditions, userDieticianResponse.morbidConditions);
			assertEquals(userDieticianFileAdded.morbidConditionStr, userDieticianResponse.morbidConditionStr);
			
			LoggerLoad.logInfo("Get patient morbidity details response validated for- " + scenario);
		}
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	@Given("User creates GET Request for the Patient endpoint with {string} scenario fileid")
	public void user_creates_get_request_for_the_patient_endpoint_with_scenario_fileid(String fileId) {
//		try
//		{
//			RestAssured.baseURI = ConfigReader.BaseURL();
//			RequestSpecification request = RestAssured.given();
//			
			LoggerLoad.logInfo("Get patient morbidity details request created for " + fileId);
//		}
//		catch (Exception ex) 
//		{
//			LoggerLoad.logInfo(ex.getMessage());
//			ex.printStackTrace();
//		}
	}

	@When("User sends {string} request with fileid")
	public void user_sends_request_with_fileid(String scenario) {
		try
		{
			switch(scenario)
			{
				case "ValidPatientIdMorbidityFileAttached" :
					response = userDieticianEndpoints.GetPatientFileReport(SharedContext.getFileId());
					break;
					
				case "ValidPatientIdMorbidityFileMissing" :
					response = userDieticianEndpoints.GetPatientFileReport(SharedContext.getFileId());
					break;
				case "InvalidPatientIdMorbidityFileAttached" :
					response = userDieticianEndpoints.GetPatientFileReport(SharedContext.getFileId());
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
	@Then("User receives the status code and message based on {string} with file report")
	public void user_receives_the_status_code_and_message_based_on_with_file_report(String scenario) {
		try
		{	
			UserDietician_response userDieticianResponse = null;
			File responsebodyfile=new File("src/test/resources/Hypo Thyroid-Report.pdf.pdf");
			
			switch(scenario)
			{
				case "ValidFileId" :
					response.then().assertThat()
						.statusCode(HttpStatus.SC_OK)
						.contentType(ContentType.JSON)
						.body(JsonSchemaValidator.matchesJsonSchema(responsebodyfile));
					
					userDieticianResponse = response.getBody().as(UserDietician_response.class);
					break;
					
					
				case "InvalidFileId" :
					if(scenario.equals("InvalidFileId")) {
						if(response.statusCode() == 404 || response.statusCode() == 405) {
												response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("404userdieticianschema.json")));
							}
						}
			}
	
			
			assertEquals(userDieticianFileAdded.MorbidityFile, userDieticianResponse.MorbidityFile);
			
			LoggerLoad.logInfo("Get patient file report validated for- " + scenario);
		}
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
		
	    
	}
	
	
	
	 

}
