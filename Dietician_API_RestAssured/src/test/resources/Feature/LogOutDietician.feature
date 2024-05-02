Feature: Logout Feature

  Scenario Outline: Valid logout for multiple users
    Given User creates GET Request for the Dietician API endpoints "<Dietician>"
    When User sends the HTTPS Request after setting of User logout endpoint for the Dietician
    Then User receives Status Code with response body for logout endpoint 

  Examples:
    | Dietician                  |
    | AUserLogin                 |    
    | DRUserlogin                | 
    | DSUserLogin                | 
    | SGUserLogin                | 
    | STUserLogin                | 

    