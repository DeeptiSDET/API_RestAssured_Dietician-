package api.routes;

import Utilities.ConfigReader;

public class PutPatient_routes {
	
	
	public static String createPut() {
	    try {
	        return ConfigReader.PutURL();
	    } catch (RuntimeException e) {
	        return null; 
	    }
	}
}