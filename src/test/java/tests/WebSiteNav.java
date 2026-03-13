package tests;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import io.github.bonigarcia.wdm.WebDriverManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebSiteNav {
        @Test
        public void SiteNav() {
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
            WebElement courseLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='https://tarunsingh.co.in/courses/']")));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", courseLink);

            wait.until(ExpectedConditions.elementToBeClickable(courseLink));
            // course click doesn't work - scolls a bit and then stops
            //better alternative is following command : JavascriptExecutor
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", courseLink);
            //courseLink.click();
            driver.quit();
        }

}