Then /^the view with id "([^\"]*)" should have text "([^\"]*)"$/ do | view_id, value |
  # get_view_property is also available: performAction( 'get_view_property', 'my_view', 'visibility') 
  performAction( 'assert_view_property', view_id, "text", value )
end

Given /^I am on "([^\"]*)" screen$/ do | expected_activity |
  actual_activity = performAction('get_activity_name')['message']
  raise "The current activity is #{actual_activity}" unless are_the_same?(actual_activity, expected_activity)
end



And /I enter "([^\"]*)" as the newTrip/ do |name|
  query "edittext", :setText => name
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

When(/^I touch today's date$/) do
  #todo: fragil test. rewrite to constant date

  # p "^^^^^^^^^^^^^^^^^^^^^^^^^^"
  # p query("SquareTextView marked:'#{10}'").size
  # p query("SquareTextView marked:'#{29}'").size

  #` D:/Android/sdk/platform-tools/adb shell date -s 20131112.104533`
  day = Time.now.day

  if(query("SquareTextView marked:'#{day}'").size > 4)
    touch("SquareTextView marked:'#{day}' index:1")
  else
    touch("SquareTextView marked:'#{day}'")
  end
end

Given(/^this new app installation$/) do
  # D:/Android/sdk/platform-tools/adb shell date -s 20131112.104533`
  performAction('select_from_menu', 'Administration')
  performAction('press_button_with_text', "Destroy All Data")
  performAction('press_button_with_text', "Ok")
end
