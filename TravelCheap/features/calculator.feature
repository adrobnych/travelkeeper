Feature: Calculator

	Scenario: As a user I can enter decimal digits on HomeActivity
		Given I am on "HomeActivity" screen
		Then the view with id "amount" should have text "0"
		When I press "7"
		And I press "8"
		Then I should not see "078"
		And I see "78"

	Scenario: As a user I can enter 0s on HomeActivity
		Given I am on "HomeActivity" screen
		Then the view with id "amount" should have text "0"
		When I press the "0" button
		And I press the "0" button
		Then I should not see "00"
		And I see "0"

	Scenario: As a user I can enter 20 digits but no more on HomeActivity
		Given I am on "HomeActivity" screen
		Then the view with id "amount" should have text "0"
		When I press the "4" button
		And I press the "4" button
		And I press the "4" button
		And I press the "4" button
		And I press the "4" button

		And I press the "4" button
		And I press the "4" button
		And I press the "4" button
		And I press the "4" button
		And I press the "4" button

		And I press the "4" button
		And I press the "4" button
		And I press the "4" button
		And I press the "4" button
		And I press the "4" button

		And I press the "4" button
		And I press the "4" button
		And I press the "4" button
		And I press the "4" button
		And I press the "4" button

		Then I see "44444444444444444444"
		When I press the "4" button
		Then I should not see "444444444444444444444"


	Scenario: As a user I can remove last digit in amount on HomeActivity
		Given I am on "HomeActivity" screen
		Then the view with id "amount" should have text "0"
		When I press the "5" button
		And I press the "6" button
		And I press the "C" button
		Then the view with id "amount" should have text "5"

	@VIP
  Scenario: As a user I can report my next expense
    Given I wait for the "HomeActivity" screen to appear
    When I press "1"
    And I press "2"
    Then I see "12"
    When I press view with id "transport"
    Then I see "Today you spent 12 Euro for transport"
    

  Scenario: As a user I can report several expenses
    Given I wait for the "HomeActivity" screen to appear
    When I press "1"
    And I press "2"
    Then I see "12"
    When I press view with id "food"
    Then I see "Today you spent 12 Euro for food"
    When I press the "Ok" button
    Then the view with id "amount" should have text "0"
    When I press "9"
    And I press view with id "food"
    Then I see "Today you spent 21 Euro for food" 


