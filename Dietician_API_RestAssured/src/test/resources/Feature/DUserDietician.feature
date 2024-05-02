Feature: Validating User as Dietician APIs

Background: User sets the Authorization header with the Bearer token.
Given User is the registered Dietician with valid "<password>" and "<UserLoginEmail>"
When User sends HTTP POST Request for User login with a valid endpoint
Then User receives the Bearer Token 

#GET (Get Patients Morbidity Details) ( User - dietician role)

Scenario Outline: Check if user is able to retrieve patient morbidity details with valid patientid
Given User creates GET Request for the Patient endpoint with "<scenario>" scenario patientid
When User sends "<scenario>" request with patientid 
Then User receives the status code and message based on "<scenario>"
Examples:
|scenario|
|ValidPatientIdMorbidityFileAttached  |
|ValidPatientIdMorbidityFileMissing   |
|InvalidPatientIdMorbidityFileAttached|

#GET (Retrieve Patient file by FileId) ( User - dietician role)

Scenario Outline: Check if user is able to retrieve patient file report using fileId
Given User creates GET Request for the Patient endpoint with "<scenario>" scenario fileid
When User sends "<scenario>" request with fileid                                                               
Then User receives the status code and message based on "<scenario>" with file report
Examples:
|scenario     |
|ValidFileId  |
|InvalidFileId|
                                                         