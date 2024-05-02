package api.stepdefinition;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import Utilities.ConfigReader;
import Utilities.LoggerLoad;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class LogOutDietician_Steps {
	 private String baseUrl;
	    private Response response;
//validlogout
	    @Given("User creates GET Request for the Dietician API endpoints {string}")
	    public void userCreatesGetRequestForTheDieticianAPIEndpoint(String endpoint) {
//	        LogoutUrl = ConfigReader.getProperty("UserLogout");
//	        RestAssured.baseURI = LogoutUrl;
	    	LoggerLoad.logInfo("logging out");
	    }

	    @When("User sends the HTTPS Request after setting of User logout endpoint for the Dietician")
	    public void userSendsTheHTTPSRequestAfterSettingOfUserLogoutEndpointForTheDietician() {
	        List<String> bearerTokens = Arrays.asList(
	                MorbiditySteps.bearerToken,
	                Post_StepDefinition.bearerToken,
	                UserDieticianSteps.bearerToken,
	                Put_StepDefinition.bearerToken,
	                GetDeleteSteps.bearerToken);

	        for (String currentToken : bearerTokens) {
	            RequestSpecification request = RestAssured.given();
	        RestAssured.baseURI = baseUrl;

	            request.header("Content-Type", "application/json");
	            request.header("Authorization", "Bearer " + currentToken);

	            response = request.get(ConfigReader.getProperty("UserLogout")); 

	            response.then().log().all();
	            Assert.assertEquals(200, response.getStatusCode());
	        }
	    }
	    @Then("User receives Status Code with response message for logout endpoint")
	    public void userReceivesStatusCodeWithResponseMessageForLogoutEndpoint() {
	        response.then().log().all();
	        Assert.assertEquals(200, response.getStatusCode());
	    }
}
	   

