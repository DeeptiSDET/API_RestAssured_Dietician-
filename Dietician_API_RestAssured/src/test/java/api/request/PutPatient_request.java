package api.request;

import java.io.File;

import Utilities.LoggerLoad;

public class PutPatient_request {
	
		public String FirstName;
		public String LastName;
		public String ContactNumber;
		public String Email;
		public String Allergy;
		public String FoodCategory;
		public String DateOfBirth;

		
		
	    public PutPatient_request(String FirstName, String LastName, String ContactNumber,String Email, String Allergy,
		                                   String FoodCategory, String DateOfBirth)
	    {
	        this.FirstName = FirstName;
	        this.LastName = LastName;
	        this.ContactNumber =ContactNumber;
	        this.Email = Email;
	        this.Allergy = Allergy;
	        this.FoodCategory = FoodCategory;
	        this.DateOfBirth = DateOfBirth;	 

	       
	        LoggerLoad.logInfo("PatientInfo is initialized");
	    }
	}