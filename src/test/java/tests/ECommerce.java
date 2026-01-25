import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;


public class ECommerceTest {

	@Test
	public void addItemsToCart() throws Exception {
		ChromeOptions options = new ChromeOptions();
		options.addArguments(
				"--headless=new",
				"--no-sandbox",
				"--disable-dev-shm-usage",
				"--disable-gpu"
				);
		
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver(options);
		
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
		driver.quit();
	}
	

}
