package api.endpoints;


import static io.restassured.RestAssured.given;

import Utilities.ConfigReader;

import api.request.GetToken_Request;
import api.request.PostPatient_request;
import api.routes.Patient_routes;
import api.routes.PostPatient_routes;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PatientEndpoints {
	
	public Response CreateAssignment(PostPatient_request postreq, String token)
	{
		RestAssured.baseURI = ConfigReader.BaseURL();
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.header("Authorization", "bearer "+token);
		request.then().log().all();
		
		Response response = request.body(postreq).post(Patient_routes.createPost());
		
		response.then().log().all();
		return response;
	}

	
	public String getToken(GetToken_Request tokenReq)
	{

		RestAssured.baseURI = ConfigReader.BaseURL();
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		
		request.then().log().all();
		Response response = request.body(tokenReq).when().post(Patient_routes.loginDietician());
          
		response.then().log().all();
	
        JsonPath js = new JsonPath(response.asString());
        String bearerToken = js.getString("token");
        System.out.println(bearerToken);
        return bearerToken;
	}
	
	
	public Response GetPatient(String token)
	{
		RestAssured.baseURI = ConfigReader.BaseURL();
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "bearer "+token);
		request.then().log().all();
        Response response = request.get(Patient_routes.getPatients());
		
		response.then().log().all();
		return response;
		
		 
	}
	public Response DeletePatientById(int Patientid,String token)
	{
		RestAssured.baseURI = ConfigReader.BaseURL();
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.header("Authorization", "bearer "+token);
		request.pathParam("patientId", Patientid);
		request.then().log().all();
		Response response = request.delete(Patient_routes.deletePatient());
        response.then().log().all();	
		
		return response;
	}
}


