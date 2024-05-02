package api.routes;

import Utilities.ConfigReader;

public class PostPatient_routes {
	
	
	public static String createPost() {
	    try {
	        return ConfigReader.PostURL();
	    } catch (RuntimeException e) {
	        return null; 
	    }
	}
}
