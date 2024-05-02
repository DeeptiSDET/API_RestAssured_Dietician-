package api.stepdefinition;

import java.io.FileOutputStream;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import Utilities.ConfigReader;
import io.cucumber.java.BeforeAll;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public class Hooks {

	@BeforeAll
	public static void beforeAll() 
	{
	   ConfigReader.loadProperty();
	try {

	    OutputStream file = new FileOutputStream(ConfigReader.getProperty("datapath") + "request_log.txt");
		PrintStream stream = new PrintStream(file, true);
		RestAssured.filters(RequestLoggingFilter.logRequestTo(stream));
		
		OutputStream fileR = new FileOutputStream(ConfigReader.getProperty("datapath") + "response_log.txt");
	  
		PrintStream streamR = new PrintStream(fileR, true);
		RestAssured.filters(ResponseLoggingFilter.logResponseTo(streamR));
		
	} catch (IOException e) {
		e.printStackTrace();
	}
	     
	}
	
	
}
