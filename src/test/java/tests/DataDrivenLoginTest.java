package tests;
//package test.java.test;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;


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
		
		DataDriven d = new DataDriven();
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
		
		DataDriven d = new DataDriven();
		
		ArrayList data2 = d.getData("Invalid Login"); 
       // Invalid Login Test Case 
         driver.get("https://rahulshettyacademy.com/locatorspractice/");
         driver.findElement(By.cssSelector("input[placeholder='Username']")).sendKeys((data2.get(1).toString()));
         driver.findElement(By.name("inputPassword")).sendKeys((data2.get(2).toString()));
         driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
         driver.findElement(By.className("submit")).click();  
         
         
         
         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
         String errorMsg = wait.until(
                 ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.error"))
         ).getText();

         Assert.assertTrue(
                 "Error message not shown for invalid login!",
                 errorMsg.contains("Incorrect")
         );

         System.out.println("Invalid Login - Pass");
         driver.quit();
		
	}
}
