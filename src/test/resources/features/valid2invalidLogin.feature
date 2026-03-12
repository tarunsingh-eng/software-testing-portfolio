Feature: Valid to Invalid Login

Scenario: User enters valid password and then enters invalid password
Given user is on login page
When user enters username and password
When user enters invalid username or password
Then error message should appear