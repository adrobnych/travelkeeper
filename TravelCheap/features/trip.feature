Feature: Trip



	Scenario: As a user I can create new trip
		Given I wait for dialog to close
        And I wait for the "HomeActivity" screen to appear
        When I press the "My trip" button
        And I wait for the "MyTripsActivity" screen to appear
        When I press the "New Trips" button
        Then I see "New Trips"
        And I enter "Trololo" as the newTrip
        And I press the "Ok" button
        And I wait for the "MyTripsActivity" screen to appear
        When I see "Trololo"
        Then I press list item number 2