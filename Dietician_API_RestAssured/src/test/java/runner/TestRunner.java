package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//@RunWith(Cucumber.class)
@CucumberOptions(
			plugin = {"pretty", "html:target/Cucumber.html",
					"json:target/cucumber.json",
					"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
					"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
					}, 
			monochrome=false,  

			features = {"src/test/resources/Feature"	
					}, 

			glue= {"api.stepdefinition"} 
		)
public class TestRunner extends AbstractTestNGCucumberTests{

}
