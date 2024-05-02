package api.endpoints;

import java.io.File;

import Utilities.ConfigReader;
import api.request.PutPatient_request;
import api.routes.PutPatient_routes;
import api.stepdefinition.Put_StepDefinition;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PutPatient {
	static String baseUrl;
	static Response response;
	

    public PutPatient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

	public static Response UpdatePatient(PutPatient_request putreq,int patientId)
	{
		RestAssured.baseURI = baseUrl;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
        request.header("Authorization", "Bearer " + Put_StepDefinition.bearerToken);

		
	        String filePath = ConfigReader.MorbidityFile();
	        File file = new File(filePath);
	        
	        response = request
	                .multiPart("file", file)
	                .multiPart("patientInfo", putreq, "application/json")
	                .pathParam("patientId", patientId)  
	                .post(PutPatient_routes.createPut());
	         
	   
		response.then().log().all();
		
		return response;
	}

}