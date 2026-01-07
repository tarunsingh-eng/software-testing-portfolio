import java.time.Duration;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GetTitleTest {

    @Test
    public void verifyTitle() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--headless=new",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu"
        );

        WebDriver driver = new ChromeDriver (options);
        
        driver.get("https://tarunsingh.co.in/");

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.titleContains("Example"));

        String title = driver.getTitle();
        //Assert.assertTrue(title.contains("example"));
        Assert.assertEquals("Example Domain", title);
        driver.quit();
    }
}