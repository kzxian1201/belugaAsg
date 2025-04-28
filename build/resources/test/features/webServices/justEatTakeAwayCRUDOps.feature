@JustEatTakeAwayAssessment
Feature: Feature covers Create, Read, Update and Delete operations on environment
  @Test4
  Scenario: Test to create, read, update and delete the booking records
    Given Generate an access token
    When Create a new booking details
    And Validate new booking is created
    And Read all booking details
    And Validate newly created booking details present in the list
    And Update the details in newly created booking record
    And Read booking details of newly created record
    And Validate details are modified in new record
    Then Delete newly created record
    And Validate newly created booking details are deleted from list