Feature: Validating GET and DELETE Dietician API methods
   
Background: User sets the Authorization header with the Bearer token
Given User is the registered Dietician with the valid "<password>" and "<UserLoginEmail>" for get all patient
When User sends HTTP POST Request for User login with valid endpoint for get all patient
Then the bearer token is generated and is stored for future use for get all patient

    
Scenario Outline: Check if User is able to get all patients 
Given User creates GET Request for the Patient endpoint with "<scenario>" to retrieve all patients
When User sends "<scenario>" get request to retrieve all patients
Then User receives the status code and message based on "<scenario>" for get all patients

Examples:
|scenario|
|ValidPatientIdMorbidityFileAttached  |
|ValidPatientIdMorbidityFileMissing   |

Scenario Outline: Check if user is able to delete patient using patientid
Given User creates DELETE Request for the Patient endpoint with "<scenario>" scenario using patientid
When User sends "<scenario>" request with patientid for delete request
Then User receives the status code and message based on "<scenario>" for delete patient
Examples:
|scenario        |
|ValidPatientId  |
|InvalidPatientId|
