package tests.cucumber.stepdefinitions;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


public class Hooks {

    public static WebDriver driver;

    @Before
    public void setUp() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments(
            "--headless",
            "--width=1920",
            "--height=1080"
        );

        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
    }

    @After
    public void tearDown(){
        if(driver != null) {

            driver.quit();
        }
    }

}