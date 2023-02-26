Feature: Sample scripts for different rest api calls

@GetUserDetails
Scenario: Get the list of users in the application
  Given User is fed with the correct authentications and endpoint details
  Then User performs get calls
  And verifies the response and response code

#Scenario: Add new user and user validate details are added correctly
#  Given User is fed with the correct authentication, payload and endpoints
#  When User performs post call out
#  Then verifies the response and response code
#  And User performs get calls
#  Then Verify new user is added to the list
#
#Scenario: Delete user and validate user details are deleted appropriately
#  Given User is fed with the correct authentication, payload and endpoints
#  When User performs delete call
#  Then verifies the response and response code
#  And User performs get calls
#  Then Verify user details are deleted
#
#Scenario: update user detail and validate user details are modified successfully
#  Given User is fed with the correct authentication, payload and endpoints
#  When User performs patch call
#  Then verifies the response and response code
#  And User performs get calls
#  Then Verify user details are updated correctly




