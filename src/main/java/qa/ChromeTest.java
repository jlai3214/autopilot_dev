package LTE.qa;
import static org.junit.Assert.assertEquals;
 






import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

 import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
 
@RunWith(BlockJUnit4ClassRunner.class)
public class ChromeTest extends TestCase {
 
		private static ChromeDriverService service;
		private WebDriver driver;
 
		@BeforeClass
		public static void createAndStartService() throws IOException {
				service = new ChromeDriverService.Builder()
				//'      .usingDriverExecutable(new File("path/to/my/chromedriver.exe"))
				.usingDriverExecutable(new File("S:/JSAT/Galaxy/Referenced Libraries/WebDrivers/chromedriver.exe"))
     			.usingAnyFreePort()
				.build();
				service.start();
		}
 
		@AfterClass
		public static void createAndStopService() {
			service.stop();
		}
 
		@Before
		public void createDriver() {
				driver = new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
		}
 
		@After
		public void quitDriver() {
		//		driver.quit();
		}
 
		@Test
		public void test_Demo() {
				driver.get("http://www.google.com");
				WebElement searchBox = driver.findElement(By.name("q"));
				searchBox.sendKeys("webdriver");
	//			((RemoteWebDriver) searchBox).quit();
				System.out.println(driver.getTitle());
				//'assertEquals("webdriver - Google Search", driver.getTitle());
				assertEquals("Google", driver.getTitle());
				
						
				}
}
 