Feature: History


    Scenario: As a user I can select some date in calendar for report
        Given I am on "HomeActivity" screen
        When I press the "History" button
        Then I see "Select a date"
        When I touch the "10" text
        And I wait for the "HistoryActivity" screen to appear


    Scenario: As a user I can see totals for some date in calendar
        Given this new app installation
        And I am on "HomeActivity" screen
        When I press the "2" button
        And I press the "4" button
        And I press the "9" button
        And I press view with id "other"
        Then I see "Today you spent 249.0 EUR for other things"
        When I press the "Ok" button
        And I press the "History" button
        Then I see "Select a date"
        When I touch today's date
        And I wait for the "HistoryActivity" screen to appear
        And I see "249"
        And I see "other things"


    Scenario: As a user I can see totals for some date in calendar
        Given this new app installation
        And I am on "HomeActivity" screen
        When I press the "4" button
        And I press the "9" button
        And I press view with id "food"
        Then I see "Today you spent 49.0 EUR for food"
        When I press the "Ok" button
        And I press the "History" button
        Then I see "Select a date"
        When I touch today's date
        And I wait for the "HistoryActivity" screen to appear
        Then I see "49"
        And I see "food"

    
    Scenario: As a user I can see total all types
        Given this new app installation
        And I am on "HomeActivity" screen
        When I press the "4" button
        And I press view with id "food"
        And I press the "Ok" button
        And I press the "9" button
        And I press view with id "transport"
        And I press the "Ok" button
        And I press the "History" button
        Then I see "Select a date"
        When I touch today's date
        And I wait for the "HistoryActivity" screen to appear
        And I see "13.0 EUR spent on "

    
    Scenario: As a user I can see separate records
        Given this new app installation
        And I am on "HomeActivity" screen
        When I press the "4" button
        And I press view with id "food"
        And I press the "Ok" button
        And I press the "9" button
        And I press view with id "transport"
        And I press the "Ok" button
        And I press the "History" button
        Then I see "Select a date"
        When I touch today's date
        And I wait for the "HistoryActivity" screen to appear
        And I press the "Records" button 
        Then I see "4.0"
        And I see "9.0"


    Scenario: (BUG FIX) As a user I can see not zero records
        Given this new app installation
        And I am on "HomeActivity" screen
        When I press view with id "food"
        And I press the "Ok" button
        And I press the "History" button
        Then I see "Select a date"
        When I touch today's date
        And I wait for the "HistoryActivity" screen to appear
        And I press the "Records" button 
        Then I should not see "food: 0.0"

    
    Scenario: As a user I can remove all data in the app
        Given this new app installation
        And I am on "HomeActivity" screen
        And I press the "9" button
        When I press view with id "food"
        And I press the "Ok" button
        And I press the "History" button
        Then I see "Select a date"
        When I touch today's date
        And I wait for the "HistoryActivity" screen to appear
        And I see "9.0 EUR spent on "
        When I select "Administration" from the menu
        And I press the "Destroy All Data" button
        And I press the "Ok" button
        Then I see "0.0 EUR spent on "
        And I see "food: 0.0"

     
    Scenario: As a user I can remove all data and no records will be in history
        Given this new app installation
        And I am on "HomeActivity" screen
        And I press the "9" button
        When I press view with id "food"
        And I press the "Ok" button
        And I press the "History" button
        Then I see "Select a date"
        When I touch today's date
        And I wait for the "HistoryActivity" screen to appear
        And I press the "Records" button 
        Then I see "food: 9.0"
        When I select "Administration" from the menu
        And I press the "Destroy All Data" button
        And I press the "Ok" button
        Then I see "0.0 EUR spent on "
        And I should not see "food: 9.0"

    Scenario: As a user I can remove single record from history
        Given this new app installation
        And I am on "HomeActivity" screen
        And I press the "9" button
        When I press view with id "food"
        And I press the "Ok" button
        And I press the "History" button
        Then I see "Select a date"
        When I touch today's date
        And I am on "HistoryActivity" screen
        And I press the "Records" button 
        Then I see "food: 9.0"
        When I touch the "food: 9.0" text
        And I press the "Delete" button
        Then I see "0.0 EUR spent on "
        And I should not see "food:"


