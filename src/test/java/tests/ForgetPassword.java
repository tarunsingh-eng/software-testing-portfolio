import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ForgetPassword{
    
    @Test
    public void forgetpasswordLinkWorks() throws InterruptedException {
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
            driver.findElement(By.linkText("Forgot your password?")).click();
            driver.findElement(By.xpath("//input[@placeholder='Name']")).sendKeys("Jane Doe");
            driver.findElement(By.cssSelector("input[placeholder='Email']")).sendKeys("JaneDoe@online.com");
            driver.findElement(By.cssSelector("input[placeholder='Phone Number']")).sendKeys("1-000-111-222");
            Thread.sleep(3000);
            driver.findElement(By.cssSelector(".reset-pwd-btn")).click();
            System.out.println("Password was reset");
            Thread.sleep(3000);
            driver.quit();
            
    }
}