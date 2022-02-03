import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SauceDemoPractice1 {

	WebDriver driver;
	@BeforeMethod
	public void setUp() {
		// Pre-requisite - chrome driver should be set in environment
		driver = new ChromeDriver();
		driver.get("https://www.saucedemo.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	@Test
	public void verifyUserAbleToAddMaxProdInCart() throws InterruptedException {
		/*
		 * Open sauce demo application (https://www.saucedemo.com/) login add the
		 * product to cart which has maximum price
		 */
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();
		//Thread.sleep(2000);
		List<WebElement> allPriceElemnts = driver.findElements(By.xpath("//div[@class='inventory_item_price']"));
		// priceListEle.forEach(p -> System.out.println(p.getText()));

		Set<Double> tempList = new TreeSet<>();
		for (WebElement el : allPriceElemnts) {
			tempList.add(Double.parseDouble(el.getText().substring(1)));
		}
		// System.out.println(tempList);
		List<Double> finalList = new LinkedList<>(tempList);
		Double maxPrice = finalList.get(finalList.size() - 1);
		// System.out.println(maxPrice);
		WebElement addToCartBtnOfMaxPrice = driver.findElement(By.xpath("//div[@class='inventory_item_price' and text()='"
				+ String.valueOf(maxPrice) + "']/parent::div/button"));
		addToCartBtnOfMaxPrice.click();
		//Thread.sleep(3000);
		WebElement removeBtn = driver.findElement(By.xpath("//div[@class='inventory_item_price' and text()='"
				+ String.valueOf(maxPrice) + "']/parent::div/button"));
		Assert.assertEquals(removeBtn.getText(), "REMOVE", "Add to cart does not become REMOVE");

	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
