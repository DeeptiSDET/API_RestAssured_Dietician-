package api.stepdefinition;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import Context.SharedContext;
import Utilities.ConfigReader;
import Utilities.DynamicValues;
import Utilities.ExcelReader;
import api.endpoints.PostPatient;
import api.endpoints.PutPatient;
import api.endpoints.UserLogin;
import api.request.PostPatient_request;
import api.request.PutPatient_request;
import api.request.UserLogin_request;
import api.response.PostPatient_response;
import io.cucumber.java.en.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import Utilities.LoggerLoad;


public class Put_StepDefinition extends BaseStep{
	Map<String, String> excelData;
	int patientId = 0;
	String sheetName;
	String header;
	PutPatient_request patientRequest;
	Response response;
    public static String bearerToken;

    @Given("User is a registered Dietician with the valid {string} and {string}")
    public void user_is_a_registered_dietician_with_the_valid_and(String password, String UserLoginEmail)
            throws IOException {

        String filePath = ConfigReader.getProperty("STUserLogin");
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String jsonContent = reader.lines().reduce("", (line1, line2) -> line1 + line2);

        JSONObject jsonObject = new JSONObject(jsonContent);

        String passwor = jsonObject.optString("password");
        String userLoginEmail = jsonObject.optString("userLoginEmail");

        UserLogin_request Alogin = new UserLogin_request(passwor, userLoginEmail);
        response = UserLogin.UserLoginCredentials(Alogin);

        reader.close();
    }

    @When("User sends HTTP POST Request for the User login with valid endpoint")
    public void user_sends_http_post_request_for_the_user_login_with_valid_endpoint() {
        response.then().statusCode(200);
        bearerToken = response.jsonPath().getString("token");
    }
    @Then("User receives Bearer Token in response")
	public void user_receives_bearer_token_in_response() {
	    LoggerLoad.logInfo("BearerToken was created");

	}
	@Given("A valid existing Patient Id with request values from {string} row {string}")
	public void user_creates_put_request_for_the_dietician_api_endpoint(String sheetName, String header) throws Exception
	{
		patientRequest = GetUpdatePatientRequest(sheetName, header);
		
		
		LoggerLoad.logInfo("Patient PUT request object created for- " + header);
	}

	@When("Submit PUT request with {string} and {string}")
	public void user_sends_http_request_and_request_body_with_fields_and_files_from_with(String sheetName, String header) {

		response = PutPatient.UpdatePatient(patientRequest,SharedContext.getPatientIdValid());

		LoggerLoad.logInfo("Patient PUT request sent for- " + header);
	}
	
	@Then("User receives response for PUT {string} with {string}")
	public void user_receives_response_for_put_request(String sheetName, String header ) {
		VerifyResponse(sheetName, header);
		LoggerLoad.logInfo("Patient PUT request sent for- " + header);
	}

	private void VerifyResponse(String sheetName, String header) {
		switch (header) {
        case "Put_Patient_Valid":
        	response.then().statusCode(200);
        	PostPatient_response patient = response.getBody().as(PostPatient_response.class);

            assertTrue(patient.patientId != 0);

            assertEquals(patientRequest.FirstName, patient.FirstName);
            assertEquals(patientRequest.LastName, patient.LastName);
            assertEquals(patientRequest.Email, patient.Email);
            assertEquals(patientRequest.ContactNumber, patient.ContactNumber);
            assertEquals(patientRequest.DateOfBirth, patient.DateOfBirth);
            assertEquals(patientRequest.Allergy, patient.Allergy);
            assertEquals(patientRequest.FoodCategory, patient.FoodCategory);
            break;
        default:
        	File responsebodyfile=new File(ConfigReader.BadRequestSchema());
        	
            response.then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body(JsonSchemaValidator.matchesJsonSchema(responsebodyfile)); 
            break;
		}
	}
	
	            
        private PutPatient_request GetUpdatePatientRequest(String sheetName, String header) throws Exception {
            excelData = ExcelReader.getData(header, sheetName);

           

                String firstName = excelData.get("FirstName");
                String lastName = excelData.get("LastName");
                String email = excelData.get("Email");
                String contactNumber = Long.toString(DynamicValues.PhoneNumber());

                String dateOfBirthExcel = excelData.get("DateOfBirth");
                String dateOfBirth = convertExcelDate(Integer.parseInt(dateOfBirthExcel));
                String allergy = excelData.get("Allergy");
                String foodCategory = excelData.get("FoodCategory");

                switch (header) {
                    case "Put_Patient_Valid":
                    case "Put_Patient_Missing_FirstName":
                    case "Put_Patient_NonAcceptedFoodCategory":
                                         
                    case "Put_Patient_Missing_LastName":
                   
                        break;
                    case "Put_Patient_Invalid_ContactNumber":
                        contactNumber = excelData.get("ContactNumber");
                        break;
                    case "Put_Patient_Missing_DateOfBirth":
                        dateOfBirth = null;  
                        break;
                    case "Put_Patient_Invalid_Email":
                                           break;
                    case "Put_Patient_Invalid_Allergy":
                        break;
                    default:
                        LoggerLoad.logWarning("Unhandled case: " + header);
                        break;
                }

                PutPatient_request patientRequest = new PutPatient_request(firstName, lastName, contactNumber, email, allergy,
                        foodCategory, dateOfBirth);

                return patientRequest;
            }

        
        private static String convertExcelDate(int excelDate) throws ParseException {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date referenceDate;
            referenceDate = dateFormat.parse("1900-01-01");
            Date resultDate = new Date(referenceDate.getTime() + ((long) excelDate - 2) * 24 * 60 * 60 * 1000);

            return dateFormat.format(resultDate);
        }

}
	


