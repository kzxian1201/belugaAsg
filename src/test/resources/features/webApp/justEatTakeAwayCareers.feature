@JustEatTakeAwayAssessment
Feature: Functionalities in Career page of Just Eat Take Away

  Description: Feature file covers test cases in Careers page of Just Eat Take Away
  Background:
    Given Open career page of Takeaway

  @Test1
  Scenario Outline: Verify that custom job title search and country filter works as per functionality
  When Job is searched globally for Job Title "<Job Title>"
  Then Verify search result matches the Job title "<Job Title>"
  And Verify search result contain jobs posted in different locations
  And Refine the search for country "<Country>"
  And Verify search result contains only jobs posted in "<Country>"

  Examples:
    | Job Title | Country     |
    | Test      | Netherlands |

  @Test2
  Scenario Outline: Check that Job Category dropdown and country filter displays correct data on selection
  When "<Job Category>" is selected from Job Category dropdown
  And Verify "<Job Category>" is selected automatically in Refine your search section
  And Verify number of search result matches the number of Job count section for "<Job Category>"
  Then Refine the search for country "<Country>"
  And Verify number of search result matches the job count for "<Country>"
  And Verify search result contain only for job category as "<Job Category>" in "<Country>"

  Examples:
    | Job Category |  Country     |
    | Sales        |  Germany     |

  @Test3
  Scenario Outline: Verify that Job Category widget displays correct information on selection
  When "<Job Category>" is selected from Job Category widget
  Then Verify search result is opened in new tab
  And Verify "<Job Category>" is selected automatically in Refine your search section
  And Verify search result contain jobs posted in different locations
  And Validate "<Job Category>" is not selected after clicking clear filter

  Examples:
    | Job Category          |
    | Corporate             |
    | Customer Service      |
    | Data & Analytics      |
    | Finance               |
    | Human Resources       |
    | Marketing             |
    | Operations & Logistic |
    | Tech & Product        |
    | Sales                 |


