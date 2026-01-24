package test.java.test;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

//import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DataDrivenLoginTest {
	@Test
	public void loginTest() throws IOException {

		
	
		ChromeOptions options = new ChromeOptions();
        options.addArguments(
            "--headless=new",
            "--no-sandbox",
            "--disable-dev-shm-usage",
            "--disable-gpu"
        );
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(options);
		
		dataDriven d = new dataDriven();
		ArrayList data = d.getData("Login");
		
		// while (n < data.size()) {
		
		// Successful Login Test Case
		 driver.get("https://rahulshettyacademy.com/locatorspractice/");
         driver.findElement(By.cssSelector("input[placeholder='Username']")).sendKeys((data.get(1).toString()));
         driver.findElement(By.name("inputPassword")).sendKeys((data.get(2).toString()));
         driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
         driver.findElement(By.className("submit")).click();   
       
         System.out.print("Successful Login - Pass");
         
        // Successful Login Assertions
         String successText = driver.findElement(By.cssSelector("p[style*='color']")).getText();
         Assert.assertEquals(successText.trim(), "You are successfully logged in.");

         
         driver.findElement(By.className("logout-btn")).click();
         driver.quit();
	}
	
	@Test
	public void InvalidloginTest() throws IOException {
		
		ChromeOptions options = new ChromeOptions();
        options.addArguments(
            "--headless=new",
            "--no-sandbox",
            "--disable-dev-shm-usage",
            "--disable-gpu"
        );
 
 //        driver.switchTo().newWindow(WindowType.TAB);
		WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(options);
		
		dataDriven d = new dataDriven();
		
		ArrayList data2 = d.getData("Invalid Login"); 
       // Invalid Login Test Case 
         driver.get("https://rahulshettyacademy.com/locatorspractice/");
         driver.findElement(By.cssSelector("input[placeholder='Username']")).sendKeys((data2.get(1).toString()));
         driver.findElement(By.name("inputPassword")).sendKeys((data2.get(2).toString()));
         driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
         driver.findElement(By.className("submit")).click();  
         
         
         
         String errorMsg = driver.findElement(By.cssSelector("p.error")).getText();
         Assert.assertTrue(errorMsg.contains("Incorrect"),
                 "Error message not shown for invalid login!");
         System.out.print("\nInvalid Login - Pass");
         driver.quit();
		//	System.out.println(n);
		//	n++;
		// }
		
		
	}
}
