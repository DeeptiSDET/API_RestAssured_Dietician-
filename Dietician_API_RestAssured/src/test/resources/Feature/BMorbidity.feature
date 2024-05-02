
Feature: GET Morbidities
    
   Background: User sets the Authorization header with the Bearer token
   Given User is the registered Dietician with a valid "<password>" and "<UserLoginEmail>"
   When User sends HTTP POST Request with User login with valid endpoint
   Then User receives a Bearer Token 
    
    Scenario Outline: Check if user able to retrieve all Morbidities with API End point 
    Given User creates GET Request for the Dietician API endpoint (no parameters)
    When User sends HTTPS Request with "<DataKey>"
    Then User receives Status Code  with response body for endpoint "<DataKey>"
    Examples:
      | DataKey |
      | Valid   |
      | Invalid |
  
    
    Scenario Outline: Check if user able to retrieve Morbidities by morbidity test name   
    Given User creates GET Request for the Dietician API endpoint with morbidity test name. 
    When User sends the HTTPS Request after setting of morbidity Test Name "<DataKey>"
    Then User receives Status Code  with response body with morbidity test name endpoint  "<DataKey>"
    Examples: 
      | DataKey |
      | Valid   |
      | Invalid |
      
   
    
    
    
   
    
    
    


