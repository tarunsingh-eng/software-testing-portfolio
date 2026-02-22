package tests;
import java.time.Duration;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class InvalidLoginTest{
    
    
   //  public static void main(String[] args) {
    @Test
    public void InvalidLoginTest(){
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
           // driver.findElement(By.id("inputUsername")).sendKeys("rahul");
           //driver.findElement(By.cssSelector("input#inputUsername")).sendKeys("Rahul");
            driver.findElement(By.cssSelector("input[placeholder='Username']")).sendKeys("Rahul");
            driver.findElement(By.name("inputPassword")).sendKeys("hello123");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.findElement(By.className("submit")).click();
            String error = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.error"))
                ).getText();
            Assert.assertEquals("* Incorrect username or password", error);
            System.out.println(error);
            driver.quit();
        
    }
}