Feature: Ecommerse Login functionality

@datadriven_login
Scenario Outline: Login user credentials
Given user open saucedemo
When user enter "<username>" and "<password>"
When user selects login button
Then user should see inventory

Examples: 
| username | password | 
| standard_user | secret_sauce | 
| problem_user | secret_sauce |
| performance_glitch_user | secret_sauce |
| error_user | secret_sauce |
| visual_user | secret_sauce |