Feature: Currency

    Scenario: As a user I can see current Currency selected for entrance
        Given this new app installation
        And I am on "HomeActivity" screen
        Then I see "EUR"

  
    Scenario: As a user I can change Entrance Currencies
        Given this new app installation
        And I am on "HomeActivity" screen
        When I press the "EUR" button
        Then I wait for the "CurrencyActivity" screen to appear
        And I see "select currency for your expenses"
        When I touch the "UAH" text
        Then I wait for the "HomeActivity" screen to appear
        And I see "UAH"

    @VIP
    Scenario: As a user I can change Report Currencies
        Given this new app installation
        And I am on "HomeActivity" screen
        And I see "EUR"
        When I press the menu key
        And I touch the "Currency for Reports" text
        Then I wait for the "CurrencyActivity" screen to appear
        And I see "select currency for your reports"
        When I touch the "USD" text
        Then I wait for the "HomeActivity" screen to appear
        And I see "EUR"
        When I press "4"
        And I press "8"
        Then I see "48"
        When I press view with id "entertainment"
        Then I see "Today you spent 52.0 USD for entertainment"