package tests;

import java.time.Duration;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebSiteNav2Test {
        @Test
        public void SiteNavHitProjectsLink() throws InterruptedException {
            ChromeOptions options = new ChromeOptions();

            options.addArguments(
                    "--headless=new",
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-gpu"
            );
            options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/115 Safari/537.36");

            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver(options);
            String token = System.getProperty("cfTestToken");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
            driver.get("https://tarunsingh.co.in/?ci_token=" + token);

           // JavascriptExecutor js = (JavascriptExecutor) driver;
           // js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", courseLink);
            JavascriptExecutor js =  (JavascriptExecutor) driver;

            wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));

        //DEBUG
        System.out.println("TITLE: " + driver.getTitle());
        System.out.println("URL: " + driver.getCurrentUrl());
        String html = driver.getPageSource();
        System.out.println("HAS_COURSES_TEXT: " + html.contains("Courses"));
        System.out.println("HAS_CLOUDFLARE: " + html.toLowerCase().contains("cloudflare"));
        System.out.println("HAS_JUST_A_MOMENT: " + html.toLowerCase().contains("just a moment"));
        System.out.println("HAS_VERIFY_HUMAN: " + html.toLowerCase().contains("verify you are human"));
        System.out.println("HAS_ACCESS_DENIED: " + html.toLowerCase().contains("access denied"));
        //END DEBUG



            WebElement projectLink = driver.findElement(By.xpath("//a[normalize-space()='Projects']"));
            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", projectLink);
            //wait.until(ExpectedConditions.elementToBeClickable(projectLink));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", projectLink);
            //projectLink.click();
            System.out.println("project link clicked");



            js.executeScript("window.scrollBy(0, 3000)");
            //By coursesLink = By.cssSelector("a[href*='courses']");
            By coursesLink = By.xpath("//a[normalize-space()='Courses']");
            WebElement courseLink = driver.findElement(coursesLink);

          //  js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'})", courseLink);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", courseLink);
            System.out.println("course link clicked");




            driver.quit();




            
        }

}