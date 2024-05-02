package api.request;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
public class UserLogin_request {

    private String password;
    private String userLoginEmail;

    @JsonCreator
    public UserLogin_request(@JsonProperty("password") String password, 
            @JsonProperty("userLoginEmail") String userLoginEmail) {
        this.password = password;
        this.userLoginEmail = userLoginEmail;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserLoginEmail() {
        return userLoginEmail;
    }

    public void setUserLoginEmail(String userLoginEmail) {
        this.userLoginEmail = userLoginEmail;
    }

	
}
