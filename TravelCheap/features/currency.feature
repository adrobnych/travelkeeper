Feature: Currency

    Scenario: As a user I can see current Currency selected for entrance
        Given I wait for the "HomeActivity" screen to appear
        Then I see "EUR"

  
    Scenario: As a user I can change Entrance Currencies
        Given I wait for the "HomeActivity" screen to appear
        When I press the "EUR" button
        Then I wait for the "CurrencyActivity" screen to appear
        And I see "Select currency"
        When I touch the "UAH" text
        Then I wait for the "HomeActivity" screen to appear
        And I see "UAH"
        When I press the "UAH" button
        Then I wait for the "CurrencyActivity" screen to appear
        And I see "Select currency"
        When I touch the "EUR" text
        Then I wait for the "HomeActivity" screen to appear
        And I see "EUR"


    Scenario: As a user I can change Report Currencies
        Given this new app installation
        And I wait for the "HomeActivity" screen to appear
        When I press the menu key
        And I touch the "Currency for Reports" text
        Then I wait for the "CurrencyActivity" screen to appear
        And I see "Select currency"
        When I touch the "USD" text
        Then I wait for the "HomeActivity" screen to appear
        And I see "EUR"
        When I press "4"
        And I press "8"
        Then I see "48"
        When I press view with id "entertainment"
        Then I see "Today you spent 65.17 USD for entertainment"
        And I press "Ok"
        When I press the menu key
        And I touch the "Currency for Reports" text
        Then I wait for the "CurrencyActivity" screen to appear
        And I see "Select currency"
        When I touch the "EUR" text

 
    Scenario: As a user I can update courses of currencies
        Given this new app installation
        And I wait for the "HomeActivity" screen to appear
        And I see "EUR"
        When I press the menu key
        And I touch the "Currency for Reports" text
        Then I wait for the "CurrencyActivity" screen to appear
        And I see "Select currency"
        When I touch the "UAH" text
        Then I wait for the "HomeActivity" screen to appear
        And I see "EUR"
        When I press "1"
        And I press "0"
        When I press view with id "entertainment"
        Then I see "Today you spent 111.8 UAH for entertainment"
        And I press "Ok"
        When I press the menu key
        And I touch the "Administration" text
        And I press "Update Courses"
        And I wait for dialog to close
        Then I go back
        Then I wait for the "HomeActivity" screen to appear
        And I see "EUR"
        When I press "1"
        And I press "0"
        And I press view with id "food"
        Then I should not see "Today you spent 111.8 UAH for food"
