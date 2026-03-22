package tests.cucumber.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

    @Given("user is on login page")
    public void openLoginPage(){
        System.out.println("Opening login page");
    }

    @When("user enters username and password")
    public void enterCredentials(){
        System.out.println("Entering credentials");
    }

    @When("user enters invalid username or password")
    public void enterIncorrectCredentials(){
        System.out.println("Entering incorrect credentials");
    }

    @Then("user should see dashboard")
    public void verifyDashboard(){
        System.out.println("Dashboard displayed");
    }

    @Then("error message should appear")
    public void blockLogin(){
        System.out.println("Please enter the correct username and password");
    }
}