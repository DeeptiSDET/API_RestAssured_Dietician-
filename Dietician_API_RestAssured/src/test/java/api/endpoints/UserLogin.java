package api.endpoints;

import api.request.UserLogin_request;
import api.routes.UserLogin_routes;
import api.stepdefinition.Post_StepDefinition;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserLogin {
	static Response response;
	static String baseUrl;
	public UserLogin(String baseUrl)
	{
		this.baseUrl = baseUrl;
	}
	
	public static Response UserLoginCredentials(UserLogin_request data)
	{
		
		RestAssured.baseURI = baseUrl;
        RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
	//	 request.header("Authorization", "Bearer " + Post_StepDefinition.bearerToken);
		
    	 response = request.body(data)
                .post(UserLogin_routes.userlogin());
    
		
		return response;
	}
	public static Response PatientLoginCredentials(UserLogin_request data) {
	    RestAssured.baseURI = baseUrl;
	    RequestSpecification request = RestAssured.given();
	    request.header("Content-Type", "application/json");
	    request.header("Authorization", "No Auth");

	    response = request.body(data)
	            .post(UserLogin_routes.userlogin());

	    return response;
	}


}
