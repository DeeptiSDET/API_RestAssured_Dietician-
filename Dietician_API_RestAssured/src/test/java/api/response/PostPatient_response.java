package api.response;

import java.util.Map;
import java.io.File;

public class PostPatient_response {
	
	public int patientId;
	public String FirstName;
	public String LastName;
	public String ContactNumber;
	public String Email;
	public String Allergy;
	public String FoodCategory;
	public String DateOfBirth;
	public int DieticianId;
	public int fileId;
	 public Map<String, Map<String, String>> FileMorbidity;
	    public Map<String, String> FileMorbidityCondition;

}
