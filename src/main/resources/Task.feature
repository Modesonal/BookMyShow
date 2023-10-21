Feature: BookMyShow Sign-In and OTP Verification

  Scenario: Sign In with Email and Verify OTP
    Given I am on the BookMyShow homepage
    And I select Bengaluru as the city
    When I click on Sign In
    And I click on Continue with Email
    And I enter "test2711@mailinator.com" and click on continue
    And I open new tab
    And I go to mailinator
    And I type "test2711@mailinator.com" and access the inbox
    And I locate the latest email from BookMyShow and fetch the OTP
    And I come back to the Sign-In Page and enter the OTP
    Then I validate that the user is successfully signed in
    And I verify that "Hi, Guest" is displayed
