Then /^the view with id "([^\"]*)" should have text "([^\"]*)"$/ do | view_id, value |
  # get_view_property is also available: performAction( 'get_view_property', 'my_view', 'visibility') 
  performAction( 'assert_view_property', view_id, "text", value )
end

Given /^I am on "([^\"]*)" screen$/ do | expected_activity |
  actual_activity = performAction('get_activity_name')['message']
  raise "The current activity is #{actual_activity}" unless are_the_same?(actual_activity, expected_activity)
end


def are_the_same? actual_activity, expected_activity
	 actual_activity == expected_activity  ||  actual_activity == expected_activity + 'Activity' 
end

private :are_the_same?

Given /^I press the "([^\"]*)" button (\d+) times$/ do |buttonText, n|
	n.to_i.times do
  	performAction('press_button_with_text', buttonText)
  end
end
