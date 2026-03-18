package tests;

import java.time.Duration;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class WebSiteNavTest{
        @Test
        public void SiteNav()  {
            ChromeOptions options = new ChromeOptions();

            options.addArguments(
                    "--headless=new",
                    "--no-sandbox",
                    "--window-size=1920,1080",
                    "--disable-dev-shm-usage"
            );

           // WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver(options);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
            //JavascriptExecutor js = (JavascriptExecutor) driver;
            
            driver.get("https://tarunsingh.co.in");
            wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
            //js.executeScript("window.scrollBy(0, 3000)");
           // By coursesLink = By.cssSelector("a[href*='courses']");
           By coursesLink = By.xpath("//a[normalize-space()='Courses']");
          
        

            // Debug: countmatches in DOM
            System.out.println("Courses links found: "+ driver.findElements(coursesLink).size());

          

            WebElement courseLink = wait.until(ExpectedConditions.presenceOfElementLocated(coursesLink));
            //courseLink.click();
            //wait.until(ExpectedConditions.urlContains("/courses")).click();
            //js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", courseLink);
        
            // course click doesn't work - scolls a bit and then stops
            //better alternative is following command : JavascriptExecutor
             ((JavascriptExecutor) driver).executeScript("arguments[0].click();", courseLink);
            // courseLink.click();
            //courseLink.click();
            wait.until(ExpectedConditions.urlContains("/courses"));
        } finally {
            driver.quit();
        }
}
}