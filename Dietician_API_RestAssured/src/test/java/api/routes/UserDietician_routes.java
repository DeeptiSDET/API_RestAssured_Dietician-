package api.routes;

import Utilities.ConfigReader;

public class UserDietician_routes {
	/* Base URL: dietician-dev-41d9a344a720.herokuapp.com/dietician
	 * Get Patient Morbidity Details: /patient/testReports/{patientId}
	 * Get Patient file by FileId: /patient/testReports/viewFile/{fileId}
	 */
	
	public static String getPatientMorbidityDetails(int patientId) {
	    try {
	        return ConfigReader.getPatientDetailsURL() +patientId;
	    } catch (RuntimeException e) {
	        return null; 
	    }
	}
	
	public static String getPatientFilebyFileId(int fileId) {
	    try {
	        return ConfigReader.getPatientFileURL() + fileId;
	    } catch (RuntimeException e) {
	        return null; 
	    }
	}
	
	

}
