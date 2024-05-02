package api.routes;

import java.net.URI;

import Utilities.ConfigReader;

public class GetDelete_routes {
	

	public static String getPatientDetails() {
	    try {
	        return ConfigReader.GetPatientsURL() ;
	    } catch (RuntimeException e) {
	        return null; 
	    }
	   
}

	public static String DeletePatient(int patientId) {
	    try {
	        return ConfigReader.PutURL()+ patientId;
	    } catch (RuntimeException e) {
	        return null; 
	    }
	}

	
}