@WebServiceExample
Feature: Sample scripts for different rest api calls

@GetUserDetails
Scenario: Get the list of users in the application
  Given User is fed with the correct authentications and endpoint details
  Then User performs get calls
  And verifies the response and response code

@AddNewUser
Scenario: Add new user and user validate details are added correctly
  Given User is fed with the correct authentication, payload and endpoints
  When User performs post call out
  Then Validates the post response




