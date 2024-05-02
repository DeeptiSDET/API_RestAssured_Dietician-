package api.routes;
import Utilities.ConfigReader;

public class Patient_routes {

	public static String createPost() {
	    try {
	        return ConfigReader.PostURL();
	    } catch (RuntimeException e) {
	        return null; 
	    }
	}
		public static String loginDietician() {
		    try {
		        return ConfigReader.LoginURL();
		    } catch (RuntimeException e) {
		        return null; 
		    }
		}
		    public static String getPatients() {
			    try {
			        return ConfigReader.GetPatientsURL();
			    } catch (RuntimeException e) {
			        return null; 
			    }
		}
		    public static String deletePatient() {
			    try {
			        return ConfigReader.DeletePatientURL();
			    } catch (RuntimeException e) {
			        return null; 
			    }
			}
	}

