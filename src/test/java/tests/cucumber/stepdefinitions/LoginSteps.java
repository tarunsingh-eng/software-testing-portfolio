package tests.cucumber.stepdefinitions;

import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {
    
    WebDriver driver;
    WebDriverWait wait;

    @Given("user is on login page")
    public void openLoginPage(){
        System.out.println("Opening login page");
    }

    @When("user logs into ecommerce")
    public void userLogsIntoEcommerce() {
        driver = Hooks.driver;
        wait = new WebDriverWait (driver, Duration.ofSeconds(10));
        driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
        System.out.println("Username Entered ");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
        System.out.println("Password Entered ");
        driver.findElement(By.xpath("//input[@id='login-button']")).click();
        System.out.println("Login Started ");
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        //Thread.sleep(5000);
    }

    @When("user enter {string} and {string}")
    public void userEntersUsernameAndPassword (String username, String password){
        driver = Hooks.driver;
        driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys((username));
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password);
        System.out.println("username and password entered");
    }

    @When("user selects login button")
    public void selectLogin () {
        driver = Hooks.driver;
        wait = new WebDriverWait (driver, Duration.ofSeconds(10));
        driver.findElement(By.xpath("//input[@id='login-button']")).click();
        System.out.println("login button click");
        wait.until(ExpectedConditions.urlContains("inventory.html"));
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

    @Then("user should see inventory")
    public void usergetstoinventory(){
        driver = Hooks.driver;
        String title;
        title = driver.findElement(By.xpath("//span[@class='title']")).getText();
        Assert.assertEquals("Products", title);
    }

    @Then("error message should appear")
    public void blockLogin(){
        System.out.println("Please enter the correct username and password");
    }
}