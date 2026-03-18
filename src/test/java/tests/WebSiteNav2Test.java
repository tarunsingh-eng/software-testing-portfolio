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

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebSiteNav2Test {
        @Test
        public void SiteNavHitCourseLink() {
            ChromeOptions options = new ChromeOptions();

            options.addArguments(
                    "--headless=new",
                    "--window-size=1920,1080",
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-gpu"
            );

            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver(options);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
            driver.get("https://tarunsingh.co.in");
            WebElement courseLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href*='courses']")));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", courseLink);

            wait.until(ExpectedConditions.elementToBeClickable(courseLink));
            // course click doesn't work - scolls a bit and then stops
            //better alternative is following command : JavascriptExecutor
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", courseLink);
            System.out.print("Course Link clicked");
            //courseLink.click();
            //driver.quit();
            WebElement projectLink = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector(("a[href*='projects']")))));
            
            wait.until(ExpectedConditions.elementToBeClickable(projectLink));
            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", projectLink);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", projectLink);
            System.out.println("project link clicked");
            driver.quit();
        }
     
        public void SiteNavHitProjectsLink() {
            ChromeOptions options = new ChromeOptions();

            options.addArguments(
                    "--headless=new",
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-gpu"
            );

            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver(options);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
            driver.get("https://tarunsingh.co.in");

           // JavascriptExecutor js = (JavascriptExecutor) driver;
           // js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", courseLink);

            
            WebElement projectLink = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector(("a[href*='projects']")))));
            
            wait.until(ExpectedConditions.elementToBeClickable(projectLink));
            JavascriptExecutor js =  (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", projectLink);

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", projectLink);
            System.out.println("project link clicked");
            driver.quit();
        }

}