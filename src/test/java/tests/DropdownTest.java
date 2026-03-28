package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.junit.Test;

import java.util.List;


public class DropdownTest {

    @Test
    public void dropdown() {

        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
     

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        System.out.println("login completed");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        System.out.println("Current URL: " + driver.getCurrentUrl());
        wait.until(ExpectedConditions.urlContains("inventory.html"));

        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.id("inventory_container")
        ));

        WebElement dd = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//select[@class='product_sort_container']")
            )
        );        
        Select s = new Select(dd);

        List<WebElement> dropoptions = s.getOptions();

        for (WebElement value : dropoptions)
        {
            System.out.println(value.getText());
        }
        driver.quit();
    }
}