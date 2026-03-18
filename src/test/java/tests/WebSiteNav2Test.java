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
        public void SiteNavHitCourseLink() throws InterruptedException {
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
            JavascriptExecutor js = (JavascriptExecutor) driver;
     
           

            wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
            WebElement courseLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='Courses']")));
            

            wait.until(ExpectedConditions.elementToBeClickable(courseLink));
            // course click doesn't work - scolls a bit and then stops
            //better alternative is following command : JavascriptExecutor
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", courseLink);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", courseLink);
            System.out.print("Course Link clicked");
            //courseLink.click();
            //driver.quit();
           
            js.executeScript("window.scrollTo(0,document.body.scrollHeight);");
            wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
            WebElement projectLink = driver.findElement(By.xpath("//a[normalize-space()='Projects']"));
            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", projectLink);
            wait.until(ExpectedConditions.elementToBeClickable(projectLink));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", projectLink);
           //projectLink.click();
            System.out.println("project link clicked");
            driver.quit();
        }
     
        public void SiteNavHitProjectsLink() throws InterruptedException {
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
            JavascriptExecutor js =  (JavascriptExecutor) driver;

            wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
            WebElement projectLink = driver.findElement(By.xpath("//a[normalize-space()='Projects']"));
            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", projectLink);
            wait.until(ExpectedConditions.elementToBeClickable(projectLink));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", projectLink);
            //projectLink.click();
            System.out.println("project link clicked");
            driver.quit();
        }

}