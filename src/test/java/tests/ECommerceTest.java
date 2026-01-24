import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class ECommerce {

	public static void main(String[] args) throws Exception {
		ChromeOptions options = new ChromeOptions();
		options.addArguments(
				"--headless=new",
				"--no-sandbox",
				"--disable-dev-shm-usage",
				"--disable-gpu"
				);
		
		
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver(options);
		
		driver.get("https://www.saucedemo.com/");
		Thread.sleep(3000);
		driver.manage().window().maximize();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
		System.out.print("Username Entered ");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
		System.out.print("Password Entered ");
		//Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id='login-button']")).click();
		System.out.print("Login Started ");
		Thread.sleep(5000);
        driver.findElement(By.cssSelector("#add-to-cart-sauce-labs-bike-light")).click(); // Simpler selector
        System.out.println("Item Added ");
	}
	
}
