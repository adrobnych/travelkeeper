Feature: Calculator

	Scenario: As a user I can enter decimal digits on HomeActivity
		Given I see "Loading of All Currencies at first launch"
        And I wait for dialog to close
        And this new app installation
        And I am on "HomeActivity" screen
		Then the view with id "amount" should have text "0"
		When I press "7"
		And I press "8"
		Then I should not see "078"
		And I see "78"

	Scenario: As a user I can enter 0s on HomeActivity
		Given this new app installation
        And I am on "HomeActivity" screen
		Then the view with id "amount" should have text "0"
		When I press the "0" button
		And I press the "0" button
		Then I should not see "00"
		And I see "0"

	Scenario: As a user I can enter 20 digits but no more on HomeActivity
		Given this new app installation
        And I am on "HomeActivity" screen
		Then the view with id "amount" should have text "0"
		When I press the "4" button 20 times
		Then I see "44444444444444444444"
		When I press the "4" button
		Then I should not see "444444444444444444444"


	Scenario: As a user I can remove last digit in amount on HomeActivity
		Given this new app installation
        And I am on "HomeActivity" screen
		Then the view with id "amount" should have text "0"
		When I press the "5" button
		And I press the "6" button
		And I press the "C" button
		Then the view with id "amount" should have text "5"

  Scenario: As a user I can report my next expense and see expences spent for transport
    Given this new app installation
    And I wait for the "HomeActivity" screen to appear
    When I press "1"
    And I press "2"
    Then I see "12"
    When I press view with id "transport"
    Then I see "Today you spent 12.0 EUR for transport"
    When I press the "Ok" button
    Then the view with id "amount" should have text "0"
    When I press view with id "transport"
    Then I see "Today's expenses"
    And I see "Today you spent 12.0 EUR for transport"
    

  Scenario: As a user I can report several expenses
    Given this new app installation
        And I wait for the "HomeActivity" screen to appear
    When I press "1"
    And I press "2"
    Then I see "12"
    When I press view with id "food"
    Then I see "Today you spent 12.0 EUR for food"
    When I press the "Ok" button
    Then the view with id "amount" should have text "0"
    When I press "9"
    And I press view with id "food"
    Then I see "Today you spent 21.0 EUR for food" 


	Scenario: As a user I can report my next expense and see expences spent for shopping
    Given this new app installation
        And I wait for the "HomeActivity" screen to appear
    When I press "1"
    And I press "6"
    Then I see "16"
    When I press view with id "shopping"
    Then I see "Today you spent 16.0 EUR for shopping"
    When I press the "Ok" button
    Then the view with id "amount" should have text "0"
    When I press view with id "shopping"
    Then I see "Today's expenses"
    And I see "Today you spent 16.0 EUR for shopping"

	Scenario: As a user I can report my next expense and see expences spent for accommodation
    Given this new app installation
        And I wait for the "HomeActivity" screen to appear
    When I press "0"
    And I press "6"
    Then I see "6"
    When I press view with id "accommodation"
    Then I see "Today you spent 6.0 EUR for accommodation"
    When I press the "Ok" button
    Then the view with id "amount" should have text "0"
    When I press view with id "accommodation"
    Then I see "Today's expenses"
    And I see "Today you spent 6.0 EUR for accommodation"

	Scenario: As a user I can report my next expense and see expences spent for entertainment
    Given this new app installation
        And I wait for the "HomeActivity" screen to appear
    When I press "4"
    And I press "8"
    Then I see "48"
    When I press view with id "entertainment"
    Then I see "Today you spent 48.0 EUR for entertainment"
    When I press the "Ok" button
    Then the view with id "amount" should have text "0"
    When I press view with id "entertainment"
    Then I see "Today's expenses"
    And I see "Today you spent 48.0 EUR for entertainment"

	Scenario: As a user I can report my next expense and see expences spent for other things
    Given this new app installation
        And I wait for the "HomeActivity" screen to appear
    When I press "3"
    And I press "0"
    Then I see "30"
    When I press view with id "other"
    Then I see "Today you spent 30.0 EUR for other things"
    When I press the "Ok" button
    Then the view with id "amount" should have text "0"
    When I press view with id "other"
    Then I see "Today's expenses"
    And I see "Today you spent 30.0 EUR for other things"

   
	Scenario: As a user I can report my next expense with decimal dot
    Given this new app installation
    And I wait for the "HomeActivity" screen to appear
    When I press "3"
    And I press "."
    And I press "4"
    And I press "5"
    Then I see "3.45"
    When I press view with id "other"
    Then I see "Today you spent 3.45 EUR for other things"

  Scenario: As a user I can enter no more then one dot on HomeActivity
		Given this new app installation
        And I am on "HomeActivity" screen
		Then the view with id "amount" should have text "0"
		When I press the "3" button
		And I press the "\." button
		And I press the "\." button
		Then I should not see "3\.\."

	Scenario: As a user I can enter no more then 2 digits after dot on HomeActivity
		Given this new app installation
        And I am on "HomeActivity" screen
		Then the view with id "amount" should have text "0"
		When I press the "1" button
		And I press the "\." button
		And I press the "3" button
		And I press the "3" button
		And I press the "3" button
		Then I should not see "333"

 
    Scenario: As a user I can press back button on HomeActivity
        Given this new app installation
        And I am on "HomeActivity" screen
        When I press the "EUR" button
        Then I wait for the "CurrencyActivity" screen to appear
        And I touch the "AOA" text
        Then I wait for the "HomeActivity" screen to appear
        When I go back
        Then I should not see "select currency"
