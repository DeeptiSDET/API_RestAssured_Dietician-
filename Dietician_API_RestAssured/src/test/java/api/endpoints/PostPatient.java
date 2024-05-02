package api.endpoints;

import java.io.File;

import Utilities.ConfigReader;
import api.request.PostPatient_request;
import api.routes.PostPatient_routes;
import api.stepdefinition.Post_StepDefinition;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostPatient {
	

    static String baseUrl;
    private static Response response;

    public PostPatient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public static Response CreatePatient(PostPatient_request postreq, boolean hasFile) {
        RestAssured.baseURI = baseUrl;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + Post_StepDefinition.bearerToken);
        if (hasFile) {
            String filePath = ConfigReader.MorbidityFile();
            File file = new File(filePath);
            response = request
                    .multiPart("file", file)
                    .multiPart("patientInfo", postreq, "application/json")
                    .post(PostPatient_routes.createPost());
        } else {
            response = request
            		.multiPart("patientInfo", postreq, "application/json")
                    .post(PostPatient_routes.createPost());
        }

        response.then().log().all();

        return response;
    }
}
