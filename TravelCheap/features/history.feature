Feature: History

	@VIP
	Scenario: As a user I can see totals for some date in calendar
		Given I am on "HomeActivity" screen
		When I press the "1" button
		And I press the "4" button
		And I press view with id "other"
    Then I see "Today you spent 14.0 Euro for other things"
    When I press the "Ok" button
    And I press the "History" button
    Then I see "Select a date"


