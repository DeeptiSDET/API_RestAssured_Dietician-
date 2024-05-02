package api.endpoints;

import Context.SharedContext;
import Utilities.ConfigReader;


import api.routes.UserDietician_routes;
import api.stepdefinition.UserDieticianSteps;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserDietician {

	String baseUrl;
	
	public UserDietician(String baseUrl)
	{
		this.baseUrl = baseUrl;
	}
	
	public Response GetPatientMorbidityDetails(int patientId)
	{
		RestAssured.baseURI = ConfigReader.BaseURL();
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
        request.header("Authorization", "Bearer " + UserDieticianSteps.bearerToken);

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
        request.header("Authorization", "Bearer " + UserDieticianSteps.bearerToken);

		request.then().log().all();
		
		Response response = request.get(UserDietician_routes.getPatientFilebyFileId(fileId));
		
		response.then().log().all();
		
		return response;
	}
	


}
