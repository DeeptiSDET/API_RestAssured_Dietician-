package api.endpoints;

import api.routes.Morbidity_routes;
import api.stepdefinition.MorbiditySteps;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Morbidity {
     
	static String baseUrl;
	
	public Morbidity(String baseUrl)
	{
		this.baseUrl = baseUrl;
	}
	public static Response GetAllMorbidity(String DataKey)
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
        request.header("Authorization", "Bearer " + MorbiditySteps.bearerToken);

		Response response = request.get(Morbidity_routes.getAllMorbidity(DataKey));
		return response;
	}
	
	public static Response GetMorbidityByTestName(String morbidityTestName,String DataKey)
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
        request.header("Authorization", "Bearer " + MorbiditySteps.bearerToken);

		Response response = request.get(Morbidity_routes.getMorbidityByTestName(morbidityTestName,DataKey));
		return response;
	}
	
	

}
