package api.endpoints;

import java.io.File;

import Utilities.ConfigReader;
import api.request.PostPatient_request;
import api.request.PutPatient_request;
import api.routes.GetDelete_routes;
import api.routes.PostPatient_routes;
import api.routes.PutPatient_routes;
import api.routes.UserDietician_routes;
import api.stepdefinition.GetDeleteSteps;
import api.stepdefinition.Put_StepDefinition;
import api.stepdefinition.UserDieticianSteps;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetDelete {


static String baseUrl;
static Response response;
public  GetDelete(String baseUrl)
{
	PostPatient.baseUrl = baseUrl;
}
public static Response GetAllPatients()
{
	RestAssured.baseURI = baseUrl;
	RequestSpecification request = RestAssured.given();
	request.header("Content-Type", "application/json");
    request.header("Authorization", "Bearer " + GetDeleteSteps.bearerToken);

	      
        response = request.get(GetDelete_routes.getPatientDetails());

	response.then().log().all();
	
	return response;
}

public static Response DeletePatientById(int patientId)
{
	RestAssured.baseURI = ConfigReader.BaseURL();
	RequestSpecification request = RestAssured.given();
	
    request.pathParam("patientId", patientId);
	request.then().log().all();
	Response response = request.delete(GetDelete_routes.DeletePatient(patientId));
    response.then().log().all();	
	
	return response;
}
}