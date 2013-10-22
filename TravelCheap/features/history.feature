Feature: History


	Scenario: As a user I can select some date in calendar for report
		Given I am on "HomeActivity" screen
		When I press the "History" button
    Then I see "Select a date"
    When I touch the "10" text
    Then I am on "HistoryActivity" screen

  @VIP
	Scenario: As a user I can see totals for some date in calendar
		Given I am on "HomeActivity" screen
		When I press the "2" button
		And I press the "4" button
		And I press the "9" button
		And I press view with id "other"
    Then I see "Today you spent 249.0 Euro for other things"
    When I press the "Ok" button
    And I press the "History" button
    Then I see "Select a date"
    When I touch today's date
    Then I am on "HistoryActivity" screen
    And I see "249"
    And I see "other things"

  @VIP
	Scenario: As a user I can see totals for some date in calendar
		Given I am on "HomeActivity" screen
		When I press the "4" button
		And I press the "9" button
		And I press view with id "food"
    Then I see "Today you spent 49.0 Euro for food"
    When I press the "Ok" button
    And I press the "History" button
    Then I see "Select a date"
    When I touch today's date
    Then I am on "HistoryActivity" screen
    When I touch the "Totals" text
    Then I see "49"
    And I see "food"
