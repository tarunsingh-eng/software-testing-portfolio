Feature: Invalid 2 Valid Login

Scenario: Invalid login
When user is on login page
Given user enters invalid username or password
When user enters username and password
Then user should see dashboard