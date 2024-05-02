package api.stepdefinition;

import Utilities.ConfigReader;
import api.endpoints.GetDelete;
import api.endpoints.Morbidity;
import api.endpoints.PostPatient;
import api.endpoints.UserLogin;
import api.endpoints.UserPatient;
import api.endpoints.UserDietician;


public class BaseStep {
String baseUrl = ConfigReader.BaseURL();
GetDelete patientEndpoints;
PostPatient postpatient;
UserLogin login;
UserDietician userDieticianEndpoints;
UserPatient userPatientEndpoints;	
Morbidity morbidityEndpoints;
    public BaseStep() 
    {
    	postpatient = new PostPatient(baseUrl);
    	login = new UserLogin(baseUrl);
    	userDieticianEndpoints = new UserDietician(baseUrl);
    	userPatientEndpoints = new UserPatient(baseUrl);
    	morbidityEndpoints = new Morbidity(baseUrl);
    	patientEndpoints = new GetDelete(baseUrl);

}
}
