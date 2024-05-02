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
import Utilities.LoggerLoad;
import api.endpoints.UserLogin;
import api.request.DeletePatient_request;
import api.request.UserLogin_request;
import api.response.DeletePatient_response;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetDeleteSteps extends BaseStep{
	Response response;
	DeletePatient_request deletePatientreq;
	static String fileId;
	static int patientId;
    public static String bearerToken;

	
	DeletePatient_response deletePatientres;
	@Given("User is the registered Dietician with the valid {string} and {string} for get all patient")
	public void user_is_the_registered_dietician_with_the_valid_and_for_get_all_patient(String string, String string2) throws IOException {
		String filePath = ConfigReader.getProperty("SGUserLogin");
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String jsonContent = reader.lines().reduce("", (line1, line2) -> line1 + line2);

        JSONObject jsonObject = new JSONObject(jsonContent);

        String passwor = jsonObject.optString("password");
        String userLoginEmail = jsonObject.optString("userLoginEmail");

        UserLogin_request Alogin = new UserLogin_request(passwor, userLoginEmail);
        response = UserLogin.UserLoginCredentials(Alogin);

        reader.close();
	}

	@When("User sends HTTP POST Request for User login with valid endpoint for get all patient")
	public void user_sends_http_post_request_for_user_login_with_valid_endpoint_for_get_all_patient() {
		response.then().statusCode(200);
        bearerToken = response.jsonPath().getString("token");
	}

	@Then("the bearer token is generated and is stored for future use for get all patient")
	public void the_bearer_token_is_generated_and_is_stored_for_future_use_for_get_all_patient() {
		LoggerLoad.logInfo("BearerToken was created");
	}
	
	@Given("User creates GET Request for the Patient endpoint with {string} to retrieve all patients")
	public void user_creates_get_request_for_the_patient_endpoint_with_to_retrieve_all_patients(String scenario) {
		try
		{
			RestAssured.baseURI = ConfigReader.BaseURL();
			RequestSpecification request = RestAssured.given();
			
			LoggerLoad.logInfo("Sending Get request for retrieving all patients");
		}
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@When("User sends {string} get request to retrieve all patients")
	public void user_sends_get_request_to_retrieve_all_patients(String scenario) {
		try
		{
			switch(scenario)
			{
				case "ValidPatientIdMorbidityFileAttached" :
					response = patientEndpoints.GetAllPatients();
					break;
					
				case "ValidPatientIdMorbidityFileMissing" :
					response = patientEndpoints.DeletePatientById(SharedContext.getPatientIdMissingFile());
					break;
			}
	
			LoggerLoad.logInfo("Get all patients- " + scenario);
		}
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	    
	}

	@Then("User receives the status code and message based on {string} for get all patients")
	public void user_receives_the_status_code_and_message_based_on_for_get_all_patients(String scenario) {
		
			if(scenario.equals("Valid")) {
				response.then().statusCode(200);
				JsonPath jsonPathEvaluator = response.jsonPath();
				List<DeletePatient_response> delPatientList = jsonPathEvaluator.getList("", DeletePatient_response.class);
				System.out.print(delPatientList.get(0).patientId);
				
				response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getallpatientvalidjsonschema.json")));
			
			} else if(scenario.equals("ValidPatientIdMorbidityFileMissing")) {
				if(response.statusCode() == 404 || response.statusCode() == 405) {
					response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("getallpatientmissingfileschema.json")));
				}
			} 
			
			LoggerLoad.logInfo("Get all patients details validated for- " + scenario);
		   
		}
		
	   
	


	@Given("User creates DELETE Request for the Patient endpoint with {string} scenario using patientid")
	public void user_creates_delete_request_for_the_patient_endpoint_with_scenario_patientid(String string) {
		try
		{
			RestAssured.baseURI = ConfigReader.BaseURL();
			RequestSpecification request = RestAssured.given();
			
			LoggerLoad.logInfo("DELETE patient by id request for scenario " + string + " created");
		}
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@When("User sends {string} request with patientid for delete request")
	public void user_sends_request_with_patientid_for_delete_request(String string) {
		try
		{
			response = patientEndpoints.DeletePatientById(patientId);
			
			LoggerLoad.logInfo("DELETE patient by id request sent");
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Then("User receives the status code and message based on {string} for delete patient")
	public void user_receives_the_status_code_and_message_based_on_for_delete_patient(String scenario) {
		response= response.then().log().all().extract().response();	
		if(scenario.equals("ValidPatientId")) {
			response.then().statusCode(200);
			JsonPath jsonPathEvaluator = response.jsonPath();
			List<DeletePatient_response> delPatientList = jsonPathEvaluator.getList("", DeletePatient_response.class);
			System.out.print(delPatientList.get(0).patientId);
			Cleanup();
		}else if(scenario.equalsIgnoreCase("InvalidPatientId")){
			response.then().statusCode(404);
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("404userdieticianschema.json")));
			
			
		}
		}
	private void Cleanup() 
	{
		try
		{
			// delete patient
			response = patientEndpoints.DeletePatientById(patientId);
			if(response.statusCode() == 200)
				LoggerLoad.logInfo("Patient deleted with id- " + patientId);
			else
				LoggerLoad.logInfo("Patient not deleted for assignment module");
			
		}
		catch(Exception ex)
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

}
