package tests.cucumber.stepdefinitions;

import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CheckoutSteps { 

    WebDriver driver;
    WebDriverWait wait;


    @When("user buys an item")
    public void userBuysOneBikeLight(){
        driver = Hooks.driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
    driver = Hooks.driver;
    String text =driver.findElement(By.className("complete-header")).getText();
    Assert.assertTrue(text.toLowerCase().contains("thank you for your order!"));
    System.out.println("Checkout-successful");
    //driver.quit();
    }

    }

