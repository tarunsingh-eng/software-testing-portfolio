import java.time.Duration;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;


public class ECommerceTest {

	@Test
	public void addItemsToCart() throws Exception {
	/*	ChromeOptions options = new ChromeOptions();
		 options.addArguments(
				//"--headless=new",
				"--no-sandbox",
				"--disable-dev-shm-usage",
				"--disable-gpu",
				"--window-size=1920,1080"
				);
		
		// 🔑 Disable password manager & autofill
			options.addArguments("--user-data-dir=/tmp/selenium-profile");
			options.addArguments("--disable-features=PasswordLeakDetection");
			options.addArguments("--disable-save-password-bubble");
			options.addArguments("--disable-features=AutofillServerCommunication");
			options.addArguments("--disable-autofill-keyboard-accessory-view");

			// 🔑 Stronger, recommended way
			options.setExperimentalOption("prefs", Map.of(
				"credentials_enable_service", false,
				"profile.password_manager_enabled", false
			));
		WebDriverManager.chromedriver().setup(); */
		WebDriverManager.firefoxdriver().setup();
		WebDriver driver = new FirefoxDriver(); // ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		
		driver.get("https://www.saucedemo.com/");
		Thread.sleep(3000);
		driver.manage().window().maximize();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
		System.out.println("Username Entered ");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
		System.out.println("Password Entered ");
		//Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id='login-button']")).click();
		System.out.println("Login Started ");
		Thread.sleep(5000);
        driver.findElement(By.cssSelector("#add-to-cart-sauce-labs-bike-light")).click(); 
        System.out.println("Item Added ");
		driver.findElement(By.cssSelector(".shopping_cart_link")).click(); 
        System.out.println("Open Cart ");

		//wait.until(ExpectedConditions.urlContains("cart.html"));
		//System.out.println("Cart Page");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='checkout']"))).click();
		System.out.println("Checkout");
		wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));

		driver.findElement(By.id("first-name")).sendKeys("Test"); 
		driver.findElement(By.id("last-name")).sendKeys("Account1"); 
		driver.findElement(By.cssSelector("#postal-code")).sendKeys("45454"); 
		driver.findElement(By.cssSelector("#continue")).click();
		System.out.println("Information added");
		driver.findElement(By.cssSelector("#finish")).click(); 
		System.out.println("Checkout successful");
		driver.quit();
	}
	
}