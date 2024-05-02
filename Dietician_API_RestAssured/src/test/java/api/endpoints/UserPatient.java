package api.endpoints;

import Utilities.ConfigReader;
import api.routes.UserDietician_routes;
import api.stepdefinition.Post_StepDefinition;
import api.stepdefinition.UserPatientSteps;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserPatient {

	String baseUrl;
	
	public UserPatient(String baseUrl)
	{
		this.baseUrl = baseUrl;
	}
	
	public Response GetPatientMorbidityDetails(int patientId)
	{
		RestAssured.baseURI = ConfigReader.BaseURL();
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		 request.header("Authorization", "Bearer " + UserPatientSteps.bearerToken);

		request.then().log().all();
		
		Response response = request.get(UserDietician_routes.getPatientMorbidityDetails(patientId));
		
		response.then().log().all();
		
		return response;
	}
	
	
	public Response GetPatientFileReport(int fileId)
	{
		RestAssured.baseURI = ConfigReader.BaseURL();
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		 request.header("Authorization", "Bearer " + UserPatientSteps.bearerToken);

		request.then().log().all();
		
		Response response = request.get(UserDietician_routes.getPatientFilebyFileId(fileId));
		
		response.then().log().all();
		
		return response;
	}
	


}
