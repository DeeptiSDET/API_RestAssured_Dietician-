package api.stepdefinition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.json.JSONObject;

import Utilities.ConfigReader;
import Utilities.ExcelReader;
import Utilities.LoggerLoad;
import api.endpoints.Morbidity;
import api.endpoints.UserLogin;
import api.request.UserLogin_request;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class MorbiditySteps extends BaseStep{
	
	Map<String, String> excelData;

	RequestSpecification request;
	Response response;
	static String morbidityTestName;
	static String DataKey;
	static int morbidityId;
    public static String bearerToken;

	
	
	@Given("User is the registered Dietician with a valid {string} and {string}")
	public void user_is_the_registered_dietician_with_a_valid_and(String password, String UserLoginEmail) throws IOException {
		
		String filePath = ConfigReader.getProperty("DSUserLogin");
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String jsonContent = reader.lines().reduce("", (line1, line2) -> line1 + line2);

        JSONObject jsonObject = new JSONObject(jsonContent);

        String passwor = jsonObject.optString("password");
        String userLoginEmail = jsonObject.optString("userLoginEmail");

        UserLogin_request Alogin = new UserLogin_request(passwor, userLoginEmail);
        response = UserLogin.UserLoginCredentials(Alogin);

        reader.close();
	}

	@When("User sends HTTP POST Request with User login with valid endpoint")
	public void user_sends_http_post_request_with_user_login_with_valid_endpoint() {
		response.then().statusCode(200);
        bearerToken = response.jsonPath().getString("token");
	}

	@Then("User receives a Bearer Token")
	public void user_receives_a_bearer_token() {
	    LoggerLoad.logInfo("BearerToken was created");

	}

	@Given("User creates GET Request for the Dietician API endpoint \\(no parameters)")
	public void user_creates_get_request_for_the_dietician_api_endpoint_no_parameters() {
		
				LoggerLoad.logInfo("GET all Morbidity");
			
	}

	@When("User sends HTTPS Request with {string}")
	public void user_sends_https_request_with() {
		try
		{
		response = Morbidity.GetAllMorbidity(DataKey);
		
		LoggerLoad.logInfo("GET all assignments request sent");
		} 
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Then("User receives Status Code  with response body for endpoint {string}")
	public void user_receives_status_code_with_response_body_for_endpoint(String string) {
		response.then().log().all();
		if(DataKey.equals("Valid") ) {
			response.then().statusCode(200);
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("200 status code json schema for GetAllMorbidity.json")));
		
		} else if(DataKey.equals("Invalid")) {
			response.then().statusCode(404);
			response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("404 status code json schema for GetAllMorbidity.json")));

		}
	}
	
	@Given("User creates GET Request for the Dietician API endpoint with morbidity test name.")
	public void user_creates_get_request_for_the_dietician_api_endpoint_with_morbidity_test_name() {
		try
		{
		  
		  //RestAssured.baseURI = baseUrl;
		  request = RestAssured.given().log().all();
			LoggerLoad.logInfo("GET all Morbidity");
		}
	  catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@When("User sends the HTTPS Request after setting of morbidity Test Name {string}")
	public void user_sends_the_https_request_after_setting_of_morbidity_test_name(String sheetName) throws Exception {
        excelData = ExcelReader.getData("Morbidity", sheetName);

		String morbidityTestName = excelData.get("morbidityTestName");
		
		//response = Morbidity.GetMorbidityByTestName(morbidityTestName,DataKey);
		try
		{
			switch(DataKey)
			{
				case "Valid" :
					response = Morbidity.GetMorbidityByTestName(morbidityTestName,DataKey);
					break;
					
				case "Invalid" :
					response = Morbidity.GetMorbidityByTestName(morbidityTestName,DataKey);
					break;
				
			}
	
			LoggerLoad.logInfo("Get patient morbidity details request sent for- " + DataKey);
		}
		catch (Exception ex) 
		{
			LoggerLoad.logInfo(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Then("User receives Status Code  with response body with morbidity test name endpoint  {string}")
	public void user_receives_status_code_with_response_body_with_morbidity_test_name_endpoint(String DataKey) {
		response.then().log().all();
		if(DataKey.equals("Valid") ) {
			response.then().statusCode(200);
			//response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("200 status code json schema for GetAllMorbidity.json")));
		
		} else if(DataKey.equals("Invalid")) {
			response.then().statusCode(404);
			//response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getClass().getClassLoader().getResourceAsStream("404 status code json schema for GetAllMorbidity.json")));

		}
	}
	
	
	
		
	}






