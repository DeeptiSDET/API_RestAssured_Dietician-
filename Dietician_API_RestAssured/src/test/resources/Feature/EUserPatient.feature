Feature: Validating User as Patient APIs

Background: User is authenticated as a Patient and sets the Authorization header with the Bearer token
Given User is the registered patient with the valid "<password>" and "<UserLoginEmail>"
When User sends HTTP POST Request for User login with valid endpoint for user patient
Then User receives Bearer Token for user patient

#GET (Get Patients Morbidity Details) ( User - dietician role)

Scenario Outline: Check if user is able to retrieve patient morbidity details with valid patientid for user patient
Given User creates GET Request for the patient endpoint with "<scenario>" scenario patientid for user patient
When User sends "<scenario>" request with patientid for user patient
Then User receives the status code and message based on "<scenario>" for user patient
Examples:
|scenario|
|ValidPatientIdMorbidityFileAttached  |
|ValidPatientIdMorbidityFileMissing   |
|InvalidPatientIdMorbidityFileAttached|

#GET (Retrieve Patient file by FileId) ( User - dietician role)

Scenario Outline: Check if user is able to retrieve patient file report using fileId for user patient
Given User creates GET Request for the Patient endpoint with "<scenario>" scenario fileid for user patient
When User sends "<scenario>" request with fileid for user patient                                                            
Then User receives the status code and message based on "<scenario>" with file report for user patient
Examples:
|scenario     |
|ValidFileId  |
|InvalidFileId|
                                                         