package tests.cucumber.stepdefinitions;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;

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


}

