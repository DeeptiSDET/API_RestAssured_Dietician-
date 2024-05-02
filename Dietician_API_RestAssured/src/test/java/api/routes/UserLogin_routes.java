package api.routes;

import Utilities.ConfigReader;

public class UserLogin_routes {
	
	public static String userlogin() {
	    try {
	        return ConfigReader.UserLoginUrl();
	    } catch (RuntimeException e) {
	        return null; 
	    }
	}
}
