package api.request;

public class GetToken_Request {
	
	
	public String userLoginEmail;
	public String password;

	public GetToken_Request(String Email, String pwd)
	{
		    this.password = pwd;
	        this.userLoginEmail = Email;
	}


}

   

