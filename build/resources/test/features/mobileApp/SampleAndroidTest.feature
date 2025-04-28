Feature: Test to validate bag functionality
  Description: This feature contains the test for bag functionality

@CottonTrader
Scenario Outline: Add a item to cart and verify it is present in cart
  Given User verifies he is present in main landing page
  And User searches for "<keyword>" from home page search
  And Select the "<item>" item from the list
  And Choose the color of the item if available
  When User adds the item to bag
  Then Validate selected "<keyword>" item present in cart with specified color

  Examples:
    | keyword | item |
    | Jacket  | 9   |

