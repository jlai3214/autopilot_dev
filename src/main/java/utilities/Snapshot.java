package LTE.utilities;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.rhi.qa.TestExecution;


public class Snapshot {
	
		public static File scrFile;
		public static String $file;
		public static String traceMessage;
		public static Boolean trace =false;

		//Take a screenshot with Selenium WebDriver for selenium automation testing 
       public static void takeDriverSnapShot(WebDriver myDriver, String host, String snapShotFile, String path) throws InterruptedException, IOException {
     			
    	   	//$file = path + snapShotFile + System.currentTimeMillis()+".gif";
    		$file = path + snapShotFile;
    	   	String $Browser = new String(TestExecution.myTSbrowser);
    		
    	   try {
    	   		if (host == "local") {
    	  			if ($Browser.equalsIgnoreCase("Chrome")) {
    	  					WebDriver augmentedDriver = new Augmenter().augment(myDriver);
    	  					scrFile = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
    	  			}
    	  			else {
    	  				scrFile = ((TakesScreenshot)myDriver).getScreenshotAs(OutputType.FILE);
    	    		}
    	  		}		
    	   		else {
    	   			System.out.println ("Selected " +host +" webdriver");
    	   			DesiredCapabilities capability = DesiredCapabilities.firefox();
    	   			myDriver = new RemoteWebDriver( new URL("http://localhost:4444/wd/hub"),DesiredCapabilities.firefox());
    	   			myDriver.get("http://bobnet/bobnet/home");

    	   			WebDriver augmentedDriver = new Augmenter().augment(myDriver);
    	   			scrFile = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
    	   		}
    	   }catch (NullPointerException n) {
    	   		$file = "n/a";
    	   		n.printStackTrace();
    	   	}
    	   		
    	   	if (scrFile != null) {
    	   		FileUtils.copyFile(scrFile, new File($file));
    	   		traceMessage="";
    	   		traceMessage="[myScreenshot.png is generated go to directory]:" + $file;
    	   	}
    	   	else {
    	   		traceMessage="No Screenshot captured";
    	   	}
       	}
	

       /*========================================================================
       /*Take a ACTIVE Windows screenshot 
		*=================================================================*/
	   public static void takeScreenSnapShot(String file, String path) throws HeadlessException, AWTException, IOException {
		
    	   		$file = path + file + System.currentTimeMillis()+".gif";
				// capture the active windows screen
				BufferedImage screencapture = new Robot().createScreenCapture(
				new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()) );

				// Save as JPEG
				scrFile = new File("screencapture.jpg");
				ImageIO.write(screencapture, "jpg", scrFile);
				FileUtils.copyFile(scrFile, new File($file));
      
				// Save as PNG
				// File file = new File("screencapture.png");
				// ImageIO.write(screencapture, "png", file);
				//FileUtils.copyFile(scrFile, new File($file));

       }

}