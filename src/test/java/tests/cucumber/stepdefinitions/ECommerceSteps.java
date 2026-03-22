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

public class ECommerceSteps { 

    WebDriver driver;
    WebDriverWait wait;

    @Given("user open saucedemo")
    public void userOpensSaucedemo() {
        driver = Hooks.driver;
        wait = new WebDriverWait (driver, Duration.ofSeconds(15));
        driver.get("https://www.saucedemo.com/");
        //Thread.sleep(3000);
    }

    @When("user logs into ecommerce")
    public void userLogsIntoEcommerce() {
        driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
        System.out.println("Username Entered ");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
        System.out.println("Password Entered ");
        driver.findElement(By.xpath("//input[@id='login-button']")).click();
        System.out.println("Login Started ");
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        //Thread.sleep(5000);
    }
    
    @When("user buys an item")
    public void userBuysOneBikeLight(){
     driver.findElement(By.cssSelector("#add-to-cart-sauce-labs-bike-light")).click(); 
        System.out.println("Item Added ");
		driver.findElement(By.cssSelector(".shopping_cart_link")).click(); 
        System.out.println("OpenCart");

		//wait.until(ExpectedConditions.urlContains("cart.html"));
		//System.out.println("Cart Page");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='checkout']"))).click();
		System.out.println("Checkout");
		wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));

		driver.findElement(By.id("first-name")).sendKeys("Test"); 
		driver.findElement(By.id("last-name")).sendKeys("Account1"); 
		driver.findElement(By.cssSelector("#postal-code")).sendKeys("45454"); 
		driver.findElement(By.cssSelector("#continue")).click();
		System.out.println("Information-added");
		driver.findElement(By.cssSelector("#finish")).click(); 
    }

    @Then("order should be complete")
    public void orderComplete() {
    String text =driver.findElement(By.className("complete-header")).getText();
    Assert.assertTrue(text.toLowerCase().contains("thank you for your order!"));
    System.out.println("Checkout-successful");
    //driver.quit();
    }

    }

