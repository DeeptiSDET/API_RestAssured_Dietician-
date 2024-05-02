package api.stepdefinition;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import Context.SharedContext;
import Utilities.ConfigReader;
import Utilities.DynamicValues;
import Utilities.LoggerLoad;
import api.endpoints.PostPatient;
import api.endpoints.UserLogin;
import api.request.PostPatient_request;
import api.request.UserLogin_request;
import api.response.PostPatientNoFile;
import api.response.PostPatient_response;
import Utilities.ExcelReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class Post_StepDefinition extends BaseStep {

    PostPatient_request patientRequest;
    Map<String, String> excelData;
    String sheetName;
    String header;
    Response response;
    static int patientIdValid = 0;
    static int patientIdMissingFile = 0;
    static String existingPhoneNumber = null;
    static int fileId = 0;
    static String existingEmail = null;
    public static String bearerToken;
    String dateOfBirth;

    @Given("User is the registered Dietician with the valid {string} and {string}")
    public void user_is_the_registered_dietician_with_the_valid_and(String password, String UserLoginEmail)
            throws IOException {

        String filePath = ConfigReader.AloginCredentials();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String jsonContent = reader.lines().reduce("", (line1, line2) -> line1 + line2);

        JSONObject jsonObject = new JSONObject(jsonContent);

        String passwor = jsonObject.optString("password");
        String userLoginEmail = jsonObject.optString("userLoginEmail");

        UserLogin_request Alogin = new UserLogin_request(passwor, userLoginEmail);
        response = UserLogin.UserLoginCredentials(Alogin);

        reader.close();
    }

    @When("User sends HTTP POST Request for User login with valid endpoint")
    public void user_sends_http_post_request_for_user_login_with_valid_endpoint() {
        response.then().statusCode(200);
        bearerToken = response.jsonPath().getString("token");
    }
    @Then("User receives Bearer Token")
	public void user_receives_bearer_token() {
	    LoggerLoad.logInfo("BearerToken was created");

	}
    @Given("User creates POST Request for the Dietician  API endpoint with {string} and {string}")
    public void user_creates_post_request_for_the_dietician_api_endpoint_with(String sheetName, String header)
            throws Exception {
        patientRequest = CreatePatientRequest(sheetName, header);
        LoggerLoad.logInfo("Patient POST request object created for- " + header);
    }

    @When("User sends HTTPS Request and  request Body with mandatory fields and morbidity files from {string} with {string}")
    public void user_sends_https_request_and_request_body_with_mandatory_fields_and_morbidity_files_from_with(
            String sheetName, String header) {
    	if (!header.equals("Post_Patient_Valid") && !header.equals("Post_Patient_Missing_File")) {
            response = PostPatient.CreatePatient(patientRequest, true);
            LoggerLoad.logInfo("Patient POST request sent for- " + header);
        }
    }

    @Then("User receives response for POST {string} with {string}")
    public void user_receives_response_for_post_with(String sheetName, String header) {
        switch (header) {
            case "Post_Patient_Valid":
            	
                response.then().assertThat()
                        .body(JsonSchemaValidator.matchesJsonSchema("postpatientresponsebodyschema.json"));

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
            case "Post_Patient_Missing_File":
//                response.then().assertThat().statusCode(HttpStatus.SC_CREATED)
//                        .body(JsonSchemaValidator.matchesJsonSchema(
//                                getClass().getClassLoader().getResourceAsStream(ConfigReader.PostPatientSchema())));

                PostPatient_response patient1 = response.getBody().as(PostPatient_response.class);
                           assertTrue(patient1.patientId != 0);

                assertEquals(patientRequest.FirstName, patient1.FirstName);
                assertEquals(patientRequest.LastName, patient1.LastName);
                assertEquals(patientRequest.Email, patient1.Email);
                assertEquals(patientRequest.ContactNumber, patient1.ContactNumber);
                assertEquals(patientRequest.DateOfBirth, patient1.DateOfBirth);
                assertEquals(patientRequest.Allergy, patient1.Allergy);
                assertEquals(patientRequest.FoodCategory, patient1.FoodCategory);
                break;

            case "Post_Patient_ExistingUniqueField":
            case "Post_Patient_NonAcceptedFoodCategory":
            case "Post_Patient_Missing_FirstName":
            case "Post_Patient_Missing_LastName":
            case "Post_Patient_Invalid_Email":
            case "Post_Patient_Invalid_ContactNumber":
            case "Post_Patient_Missing_DateOfBirth":
            	File responsebodyfile=new File(ConfigReader.BadRequestSchema());
            	
                response.then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                        .body(JsonSchemaValidator.matchesJsonSchema(responsebodyfile));
                              
                                                                         break;
        }
    }

    private PostPatient_request CreatePatientRequest(String sheetName, String header) throws Exception {
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
            case "Post_Patient_Valid":
            case "Post_Patient_NonAcceptedFoodCategory":
            case "Post_Patient_Missing_FirstName":
            case "Post_Patient_Missing_LastName":
            case "Post_Patient_Invalid_Email":
            case "Post_Patient_Missing_DateOfBirth":
                break;
            case "Post_Patient_ExistingUniqueField":
                contactNumber = SharedContext.getExistingPhoneNumber();
                dateOfBirth = SharedContext.getDateOfBirth();
                break;
            case "Post_Patient_Invalid_ContactNumber":
                contactNumber = excelData.get("ContactNumber");
                break;
                        
            case "Post_Patient_Missing_File":
                Response responseMissingFile = PostPatient.CreatePatient(patientRequest, false);

                if (header.equals("Post_Patient_Missing_File")) {
                	PostPatientNoFile patientMissingFile = responseMissingFile.getBody()
                            .as(PostPatientNoFile.class);
                    patientIdMissingFile = patientMissingFile.patientId;
                    SharedContext.setPatientIdMissingFile(patientIdMissingFile);

                    LoggerLoad.logInfo("PatientId for Post_Patient_Missing_File: " + patientIdMissingFile);
                }
                break;
            default:
                LoggerLoad.logWarning("Unhandled case: " + header);
                break;
        }

        System.out.println("Generated Contact Number: " + contactNumber);

        PostPatient_request patientRequest = new PostPatient_request(firstName, lastName, contactNumber, email, allergy,
                foodCategory, dateOfBirth);

        if (header.equals("Post_Patient_Valid")) {
            Response responseValid = PostPatient.CreatePatient(patientRequest, true);

            if (header.equals("Post_Patient_Valid")) {
                PostPatient_response patientValid = responseValid.getBody().as(PostPatient_response.class);
                patientIdValid = patientValid.patientId;
                existingPhoneNumber = patientValid.ContactNumber;
                existingEmail = patientValid.Email;
                fileId = patientValid.fileId;
                SharedContext.setExistingPhoneNumber(existingPhoneNumber);
                SharedContext.setExistingEmail(existingEmail);
                SharedContext.setFileId(fileId);
                SharedContext.setPatientIdValid(patientIdValid);
                SharedContext.setDateOfBirth(dateOfBirth);

                LoggerLoad.logInfo("PatientId for Post_Patient_Valid: " + patientIdValid);
            }
        }

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
