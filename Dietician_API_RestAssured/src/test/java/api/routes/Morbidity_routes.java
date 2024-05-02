package api.routes;

import Utilities.ConfigReader;

public class Morbidity_routes {
	

	public static String getAllMorbidity(String DataKey)
	{
		String endpoint = null;
		if("Invalid".equals(DataKey))
			endpoint = ConfigReader.getMorbidityGetAllUrl() + "ss";
		else 
			endpoint = ConfigReader.getMorbidityGetAllUrl();	

		
		System.out.println("endpoint in "+endpoint);
		return endpoint;
		
	}
	
	public static String getMorbidityByTestName(String morbidityTestName,String DataKey)
	{
		String endpoint = null;
		if("Invalid".equals(DataKey))
			endpoint = ConfigReader.getMorbidityGetAllUrlByTestNameUrl() + "0000" ;
		else 
			endpoint = ConfigReader.getMorbidityGetAllUrlByTestNameUrl() + morbidityTestName;
		return endpoint;
	}
	
	
}
