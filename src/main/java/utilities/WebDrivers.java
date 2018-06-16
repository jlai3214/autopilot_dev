package main.java.utilities;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.Capabilities;


public class WebDrivers  {
	
	public static WebDriver driver;
	public static String browser;
	public static String operatingSys;
	public static String version;
	public static String traceMessage;
	//public static Boolean trace =false;
	
		
	public static void getBrowser(String mydriver, String URL)  throws Exception { 
			
		switch(mydriver.toUpperCase()) {
		case "CHROME": {
			getChromeDriver();
	     	break;
		}	

	     case "FIREFOX": {  
	    	 getFirefoxDriver();
		     break;
	     }
	     
	    case "IE":
	    case "IE9":
	    case "IE10":
	    case "IE 32-bit":
	    case "IE 64-bit": {
	    	 getIEDriver(mydriver);
	    	 break;
	    }
		}
			
		Capabilities cap = (Capabilities) ((RemoteWebDriver) driver).getCapabilities();
		browser =  cap.getBrowserName().toLowerCase();
		operatingSys = cap.getPlatform().toString();
		version = cap.getVersion().toString();
		System.out.print("[Browser]=:" +browser + "; ");
		System.out.print("[Operating System]=:"+ operatingSys + "; ");
		System.out.println("[Version]=:" + version);
	}		
		
		public static void getChromeDriver() throws Exception  {
			
				System.setProperty("webdriver.chrome.driver", "Z:/JSAT/Galaxy/Referenced Libraries/webDriver/chromedriver.exe");
				ChromeDriverService service;
				service = new ChromeDriverService.Builder()
				.usingDriverExecutable(new File("c:/JSAT 2.5/Galaxy/Referenced Libraries/WebDrivers/chromedriver.exe"))
				//.usingDriverExecutable(new File("c:/JSAT/Galaxy/Referenced Libraries/webDriver/chromedriver.exe"))
				//		.usingDriverExecutable(new File(myGalaxy.myBrowserDriverDir + "Chromedriver.exe"))
				.usingAnyFreePort()
				.build();
				service.start();
				driver = new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
		}					
	
		public static void getIEDriver(String version) throws Exception  {
			
				String bit = "Win 32";   //default 32 bit
				if (version.endsWith("32-bit")) {
					bit = "Win 32";
				}
				else if (version.endsWith("64-bit")) {
					bit = "Win 64";
				}
				//String $refFolder = "z://JSAT/Galaxy/Referenced Libraries/WebDrivers/" + bit + "/" ;
				String $refFolder = "c://JSAT 2.5/Galaxy/Referenced Libraries/WebDrivers/" + bit + "/" ;
				
				File file = new File( $refFolder  +"IEDriverServer.exe");
				//System.setProperty("webdriver.ie.driver",  myGalaxy.myBrowserDriverDir + "IEDriverServer.exe");
				//System.setProperty("webdriver.ie.driver",  "S://JSAT/Galaxy/Referenced Libraries/WebDrivers/" + "IEDriverServer.exe");
				System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
				driver = new InternetExplorerDriver();
 			}					

		public static void getFirefoxDriver() throws Exception  {
				
				System.setProperty("webdriver.firefox.profile","JSAT");
				//System.setProperty("webdriver.firefox.profile","Default User");
				//System.setProperty("webdriver.firefox.profile","Selenium");
	 			
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, false);
				driver = new FirefoxDriver(capabilities);
		}					
	
	}