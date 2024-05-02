Feature: Create Patient with Post

Background: User sets the Authorization header with the Bearer token.
Given User is the registered Dietician with the valid "password" and "UserLoginEmail" 
When User sends HTTP POST Request for User login with valid endpoint
Then User receives Bearer Token 
    
  Scenario Outline: Check if user able to create a Patient with valid endpoint and request body (non existing values)
    Given User creates POST Request for the Dietician  API endpoint with "<sheetName>" and "<header>"
    When  User sends HTTPS Request and  request Body with mandatory fields and morbidity files from "<sheetName>" with "<header>"  
    Then User receives response for POST "<sheetName>" with "<header>"

    Examples: 
      | sheetName  |            header                    |
      |  patient   | Post_Patient_Valid                   |
      |  patient   | Post_Patient_ExistingUniqueField     |
      |  patient   | Post_Patient_NonAcceptedFoodCategory |
      |  patient   | Post_Patient_Missing_FirstName       |
      |  patient   | Post_Patient_Missing_LastName        |
      |  patient   | Post_Patient_Invalid_Email           |
      |  patient   | Post_Patient_Invalid_ContactNumber   |
      |  patient   | Post_Patient_Missing_DateOfBirth     |
      |  patient   | Post_Patient_Missing_File            |
      
      
     