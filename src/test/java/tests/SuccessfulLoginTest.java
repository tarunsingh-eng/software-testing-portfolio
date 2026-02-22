package tests;

import java.time.Duration;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SuccessfulLoginTest{
    @Test
    public void LoginTest(){
            ChromeOptions options = new ChromeOptions();
            options.addArguments(
                "--headless=new",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu"
            );
            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver(options);
            driver.get("https://rahulshettyacademy.com/locatorspractice/");
            driver.findElement(By.cssSelector("input[placeholder='Username']")).sendKeys("Rahul");
            driver.findElement(By.name("inputPassword")).sendKeys("rahulshettyacademy");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.findElement(By.className("submit")).click();  
            System.out.println("Login Successful");  
            driver.quit();
    }
}