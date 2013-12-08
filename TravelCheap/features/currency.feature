Feature: Currency

    Scenario: As a user I can see current Currency selected for entrance
        Given this new app installation
        And I am on "HomeActivity" screen
        Then I see "EUR"

    @VIP
    Scenario: As a user I can chenge Entrance Currencies
        Given this new app installation
        And I am on "HomeActivity" screen
        When I press the "EUR" button
        Then I am on "CurrencyActivity" screen
        When I touch the "UAH" text
        Then I am on "HomeActivity" screen
        And I see "UAH"
        


