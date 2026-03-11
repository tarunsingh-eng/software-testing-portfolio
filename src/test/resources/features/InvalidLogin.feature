Feature: Invalid Login

Scenario: Invalid login
When user is on login page
Given user enters invalid username or password
Then error message should appear
