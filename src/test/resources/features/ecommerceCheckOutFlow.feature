Feature: Ecommerce checkout flow

Scenario: E2E - Login to checkout
Given user open saucedemo
When user logs into ecommerce
And user buys an item
Then order should be complete