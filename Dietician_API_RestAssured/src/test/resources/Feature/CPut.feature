Feature: Update Patient with Put

Background: User sets the Authorization header with the Bearer token.
Given User is a registered Dietician with the valid "<password>" and "<UserLoginEmail>"
When User sends HTTP POST Request for the User login with valid endpoint
Then User receives Bearer Token in response

  Scenario Outline: Check if user able to update a Patient with valid endpoint and request body (non existing values)
    Given A valid existing Patient Id with request values from "<sheetName>" row "<header>"
    When Submit PUT request with "<sheetName>" and "<header>"
    Then User receives response for PUT "<sheetName>" with "<header>"

    Examples: 
    
      | sheetName  |            header               |
      |  put   | Put_Patient_Valid                   |
      |  put   | Put_Patient_NonAcceptedFoodCategory |
      |  put   | Put_Patient_Missing_FirstName       |
      |  put   | Put_Patient_Missing_LastName        |
      |  put   | Put_Patient_Invalid_Email           |
      |  put   | Put_Patient_Invalid_ContactNumber   |
      |  put   | Put_Patient_Missing_DateOfBirth     | 
      |  put   | Put_Patient_Invalid_Allergy         |